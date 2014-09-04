'use strict';

// directive that prevents submit if there are still form errors
d4allApp.directive('validSubmit', [ '$parse', function($parse) {
    return {
        // we need a form controller to be on the same element as this directive
        // in other words: this directive can only be used on a &lt;form&gt;
        require: 'form',
        // one time action per form
        link: function(scope, element, iAttrs, form) {
            form.$submitted = false;
            // get a hold of the function that handles submission when form is valid
            var fn = $parse(iAttrs.validSubmit);

            // register DOM event handler and wire into Angular's lifecycle with scope.$apply
            element.on('submit', function(event) {
                scope.$apply(function() {
                    // on submit event, set submitted to true (like the previous trick)
                    form.$submitted = true;
                    // if form is valid, execute the submission handler function and reset form submission state
                    if (form.$valid) {
                        fn(scope, { $event : event });
                        form.$submitted = false;
                    } else if (form.$invalid){
                        console.log();
                        var feild;
                        for (feild in form) {
                            // look at each form input with a name attribute set
                            // checking if it is pristine and not a '$' special field
                            if (feild[0] != '$' && form[feild].$pristine) {
                                form[feild].$setViewValue(
                                    form[feild].$modelValue
                                );
                            }
                        }
                    }
                });
            });
        }
    };
}
]);

d4allApp.directive("passwordVerify", function() {
    return {
        require: "ngModel",
        scope: {
            passwordVerify: '='
        },
        link: function(scope, element, attrs, ctrl) {
            scope.$watch(function() {
                var combined;

                if (scope.passwordVerify || ctrl.$viewValue) {
                    combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                }
                return combined;
            }, function(value) {
                if (value) {
                    ctrl.$parsers.unshift(function(viewValue) {

                        var origin = scope.passwordVerify;

                        if (origin.$viewValue !== viewValue) {
                            ctrl.$setValidity("passwordVerify", false);
                            return undefined;
                        } else {
                            ctrl.$setValidity("passwordVerify", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
});


d4allApp.directive('blacklist', function (){
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ngModel) {
            var blacklist = attr.blacklist.split(',');

            //For DOM -> model validation
            ngModel.$parsers.unshift(function(value) {
                var valid = value.indexOf(blacklist) === -1;
                ngModel.$setValidity('blacklist', valid);
                return value ;
            });

        }
    };
});


d4allApp.directive("cidrVerify", function() {
    return {
        require: "ngModel",
        scope: {
            cidrVerify: '='
        },
        link: function(scope, element, attrs, ctrl) {

            function padleft(num,size,value) {
                var s = num+"";
                while (s.length < size) s = value + s;
                return s;
            }
            function padright(num,size,value) {
                var s = num+"";
                while (s.length < size) s =  s + value;
                return s;
            }

            scope.$watch(function() {

                if (scope.cidrVerify && ctrl.$viewValue) {
                    return (ctrl.$viewValue + "_" + scope.cidrVerify);
                } else {
                    return undefined;
                }

            }, function(value) {

                if (value) {

                        value = value.substr(0,value.indexOf("_"));

                        var prefix = scope.cidrVerify;

                        var tokens = value.split('.');

                        var IPBinaryString = "";
                        var NetMask = "";

                        for (var i=0 ; i < tokens.length ; i++) {
                            IPBinaryString += padleft(Number(tokens[i]).toString(2),8,"0");
                        }

                        NetMask = padleft("",prefix,"0");

                        NetMask = padright(NetMask,32,"1");

                        console.log ('a:' + NetMask + '---' + NetMask.length);
                        console.log ('b:' + IPBinaryString + '---' + IPBinaryString.length);

                        var isValid = true;
                        if ((parseInt(NetMask,2) & parseInt(IPBinaryString,2)) > 0) {
                            isValid = false;
                        } else {
                            isValid = true;
                        };

                    /*
                        var isValid = true;
                        if (prefix > 0 && prefix <= 8) {
                            if (tokens[0] != 0 && tokens[1] == 0 && tokens[2] == 0 && tokens[3] == 0) {
                                isValid = true;
                            } else {
                                isValid = false;
                            }
                        } else if (prefix > 8 && prefix <= 16) {
                            if (tokens[0] != 0 && tokens[1] != 0 && tokens[2] == 0 && tokens[3] == 0) {
                                isValid = true;
                            } else {
                                isValid = false;
                            }
                        } else if (prefix > 16 && prefix <= 24) {
                            if (tokens[0] != 0 && tokens[1] != 0 && tokens[2] != 0 && tokens[3] == 0) {
                                isValid = true;
                            } else {
                                isValid = false;
                            }
                        } else if (prefix > 24) {
                            if (tokens[0] != 0 && tokens[1] != 0 && tokens[2] != 0 && tokens[3] != 0) {
                                isValid = true;
                            } else {
                                isValid = false;
                            }
                        }
*/
                        ctrl.$setValidity("cidrVerify", isValid);
                        return value;

                }
            });
        }
    };
});


/**
 * Provide custom type validation for input elements. Certain type attributes
 * don't work consistenty cross-browser, so this is a required workaround.
 * Looking at you, webkit and `type="number"`.
 *
 * ```html
 * <input
 *   ng-model=""
 *   app-type="">
 * ```
 *
 * forked from: http://blakeembrey.com/articles/angular-js-number-validation-bug/
 */
d4allApp.directive('appType', function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            // Custom number validation logic.
            if (attrs.appType === 'number') {
                return ctrl.$parsers.push(function (value) {
                    var valid = value == null || isFinite(value) ;

                    //check for whole number
                    valid = valid && (value == null || value.indexOf(".")==-1);

                    ctrl.$setValidity('number', valid);

                    return valid && value != null ? Number(value) : undefined;
                });
            }
        }
    };
});


/**
 * Provide minimum number validation for any input.
 *
 * ```html
 * <input
 *   ng-model=""
 *   app-min="">
 * ```
 */
d4allApp.directive('appMin', function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            return ctrl.$parsers.push(function (value) {
                var valid = value == null || Number(value) >= Number(attrs.appMin);

                ctrl.$setValidity('min', valid);

                return valid ? value : undefined;
            });
        }
    };
});

/**
 * Provide maximum number validation for any input.
 *
 * ```html
 * <input
 *   ng-model=""
 *   app-max="">
 * ```
 */
d4allApp.directive('appMax', function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            return ctrl.$parsers.push(function (value) {
                var valid = value == null || Number(value) <= Number(attrs.appMax);

                ctrl.$setValidity('max', valid);

                return valid ? value : undefined;
            });
        }
    };
});

