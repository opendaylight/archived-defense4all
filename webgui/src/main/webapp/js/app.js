'use strict';

//var REST_SERVER_IP = location.hostname ;
var REST_SERVER_IP = '10.206.167.39';

var REST_SERVER = '//' + REST_SERVER_IP + ':8086';
//in milliseconds
var POLLING_TIMEOUT = 5000;

var d4allApp = angular.module('d4allApp', ['ngResource', 'ngRoute', 'ui.bootstrap','ngTagsInput']);


/*
 d4allApp.config(['$httpProvider', function($httpProvider) {
 $httpProvider.defaults.useXDomain = true;
 delete $httpProvider.defaults.headers.common['X-Requested-With'];
 }
 ]);
 */

d4allApp.config(function($routeProvider, $locationProvider){

    $routeProvider.
        when("/fw-setup", {templateUrl : "partials/fw-setup.html", controller: 'FWConfigController'}).
        when("/addSdn", {templateUrl : "partials/addSdn.html", controller: 'SdnController'}).
        when("/addAms", {templateUrl : "partials/addAms.html", controller: 'AmsController'}).
        when("/addNetnode", {templateUrl : "partials/addNetnode.html", controller: 'NetnodeController'}).
        when("/fw-reports", {
            templateUrl : "partials/reports.html",
            controller: 'ReportsController',
            reportData: {
                reportType:'FW',
                reportTitle:'Framework Reports'
            }
        }).
        when("/df-setup", {templateUrl : "partials/df-setup.html", controller: 'DFConfigController'}).
        when("/df-reports", {
            templateUrl : "partials/reports.html",
            controller: 'ReportsController',
            reportData: {
                reportType:'DF',
                reportTitle:'Defense4All Reports'
            }
        }).
        when("/addPo", {templateUrl : "partials/addPo.html", controller: 'PoController'}).
        otherwise({redirectTo:"/fw-setup"})
});
d4allApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}
]);
d4allApp.controller('FWConfigController', function FWConfigController($scope, $resource,$timeout,$modal,AMSService,$q) {

    $scope.pollingInterval = POLLING_TIMEOUT/1000;

    var ofcs = $resource(REST_SERVER + "/rest/app/df/ofcs/:hostname",
        {hostname:'@hostname'},{get:{method:'GET', isArray:true}});

    // Make the first poller open and close the server connection modal
    var ofcsPoller = function () {
        ofcs.get(function(ofcsResult){
            $scope.ofcs = ofcsResult;
            console.log('success');
            if ($scope.offlineModal) {
                $scope.offlineModal.close();
                $scope.offlineModal = undefined;
            }
        }, function(response){
            console.log('error');
            if (!$scope.offlineModal) {
                $scope.offlineModal = $modal.open({
                    templateUrl: 'offline.html',
                    keyboard:false,
                    scope:$scope,
                    backdrop:'static'
                });
            }
        });
        $timeout(ofcsPoller,POLLING_TIMEOUT);
    }
    ofcsPoller();

    var amss = [];

    var amssPoller = function () {

        var numberOfActiveAmss = 0;

        var defer = $q.defer();

        defer.promise.then(function(amssResult){
            $scope.amss = amssResult;
            for (var i = 0; i < amssResult.length; i++) {
                if (amssResult[i].status === "ACTIVE" || amssResult[i].status === "UNKNOWN") {
                    numberOfActiveAmss++
                };
            }
            $scope.numberOfActiveAmss = numberOfActiveAmss;

        });

        AMSService.getAmss(defer);

        $timeout(amssPoller,POLLING_TIMEOUT);
    }
    amssPoller();


    var netNodes = $resource(REST_SERVER + "/rest/app/df/netnodes/:label",
        {label:'@label'},{get:{method:'GET', isArray:true}});

    var netNodesPoller = function () {
        netNodes.get(function(netNodesResult){
            $scope.netNodes = netNodesResult;
        });
        $timeout(netNodesPoller,POLLING_TIMEOUT);
    }
    netNodesPoller();


    $scope.numberOfAmss = function numberOfAmss(delimitedString) {
        if (delimitedString == "") {
            return 0;
        } else {
            var arrAmss = delimitedString.split("::");
            return arrAmss.length;
        }
    };

    /*
     $scope.trafficPortLabels = function trafficPortLabels(ports) {
     var portsStr = "";
     angular.forEach(ports,function(v,k){
     if (ports[k].up){
     portsStr += ports[k].number + " [UP]\n";
     }else {
     portsStr += ports[k].number + " [DOWN]\n";
     }
     });

     return portsStr;
     }

     $scope.protectedLinkLabels = function protectedLinkLabels(portsLinks) {
     var portsStr = "";
     angular.forEach(portsLinks,function(v,k){
     portsStr += "["+ portsLinks[k].northPort + "->"+ portsLinks[k].southPort + "]\n";
     });
     return portsStr;
     }
     */
    $scope.amssStatus = function (delimitedString) {

        if (delimitedString == "") {
            return "transparent";
        }

        var arrAmss = delimitedString.split("::");
        var numberTotal = arrAmss.length;
        var numberOfDown = 0;
        for (var i ; i < arrAmss.length; i++) {
            var ams = arrAmss[i].split(':');

            if (ams[5] === 'UP') {
                //do nothing
            } else {
                numberOfDown++;
            }
        }

        //check for red, green or orange
        if (numberTotal == numberOfDown) {
            return "down"
        } else if (numberOfDown == 0) {
            return "up"
        } else {
            return "warning"
        }

    }

    $scope.linkStatus = function (link,ports) {

        var southPort,northPort;

        for(var port in ports){
            if (link.southPort == ports[port].number) {
                southPort = ports[port];
            } else if (link.northPort == ports[port].number){
                northPort = ports[port];
            }
        }

        if (southPort && northPort){
            if (southPort.up && northPort.up) {
                return "up"
            } else {
                return "down"
            }
        } else {
            return "warning"
        }

    }


    $scope.deleteSdn = function (hostname) {

        $scope.deleteSDNOK = confirm('Are you absolutely sure you want to delete SDN:' + hostname + ' ?');

        if ($scope.deleteSDNOK) {

            ofcs.delete({hostname: hostname}, function () {
                console.log('success');
                //refresh scope
                $scope.ofcs = ofcs.get();
            }, function (err) {
                console.log('error: ' + err.statusText);
            });
        }

    };

    $scope.deleteAms = function (label) {

        $scope.deleteAMSOK = confirm('Are you absolutely sure you want to delete AMS:' + label + ' ?');

        if ($scope.deleteAMSOK) {

            console.log('deleteAMS:' + label);
            AMSService.deleteAms(label);
        }

    };


    $scope.deleteNetNode = function (node) {

        $scope.deleteNNOK = confirm('Are you absolutely sure you want to delete NetNode:' + node.label + ' ?');

        if ($scope.deleteNNOK) {

            console.log('deleteNetNode:' + node.label);
            netNodes.delete({label: node.label}, function () {
                console.log('success');
                //refresh scope
                $scope.netNodes = netNodes.get();
            }, function (err) {
                console.log('error: ' + err.statusText);
            });
        }
    };

    $scope.isActive = function(object) {
        return object.status === "ACTIVE";
    };

    $scope.isNotRemoved = function(object) {
        return !(object.status === "REMOVED") ;
    };

});

/*
 Doing this as an http and not resource as it's not JSON but a regular string
 */
d4allApp.controller('HostIpController', function HostIpController($scope,$http,$timeout,$log){

    $scope.hostIpAlerts = [];

    var fwConfig = {
        currentHostIp : '',
        newHostIp : ''
    }
    $scope.fwConfig = fwConfig;

    $scope.update = function() {
        $log.debug('updating newHostIp:' + $scope.fwConfig.newHostIp);
        $http({
            method: 'POST',
            url: REST_SERVER + '/rest/general/hostaddress',
            data: $scope.fwConfig.newHostIp
        }).error(function(err){
            $scope.hostIpAlerts.push({type: 'danger', msg: err.statusText});
        })
    };

    var hostIpPoller = function () {

        $http.get(REST_SERVER + '/rest/general/hostaddress').success(function(data){
            $log.debug('hostIP DATA :' + data);
            $scope.fwConfig.currentHostIp = data;
        });

        $timeout(hostIpPoller,POLLING_TIMEOUT);
    }
    hostIpPoller();

    $scope.closeAlert = function(index) {
        $scope.hostIpAlerts.splice(index, 1);
    };


});


d4allApp.controller('DangerZone', function DangerZone($scope,$http,$log){

    $scope.dangerZoneAlerts = [];

    $scope.resetSystem = function() {

        $scope.okToReset = confirm('This will remove ALL dynamic data as well as ALL configurations. Are you sure?');

        if ($scope.okToReset) {

            $http.post(REST_SERVER + '/rest/general/reset', 'factory').success(function(){
                $scope.dangerZoneAlerts.push({type: 'success', msg: 'Successfully reset the system to factory settings.'});
            })
                .error(function(err){
                    $scope.dangerZoneAlerts.push({type: 'danger', msg: err.statusText});
                })

        }

    }


    $scope.terminate = function() {

        $scope.okToTerminate = confirm('Are you sure you want to terminate Defense Flow?');

        if ($scope.okToTerminate) {

            $http.post(REST_SERVER + '/rest/general/terminate').success(function(){
                $scope.dangerZoneAlerts.push({type: 'success', msg: 'Successfully terminated DefenseFlow.'});
            })
                .error(function(data, status){

                    if (status == '404') {
                        $scope.dangerZoneAlerts.push({type: 'danger', msg: 'Can\'t connect to DefenseFlow service'});
                    } else {
                        $scope.dangerZoneAlerts.push({type: 'danger', msg: data.statusText});
                    }

                })

        }

    }

    $scope.closeAlert = function(index) {
        $scope.dangerZoneAlerts.splice(index, 1);
    };

});


d4allApp.controller('SdnController', function SdnController($scope, $location, $resource) {

    var ofcs = $resource(REST_SERVER + '/rest/app/df/ofcs');

    $scope.save = function() {

        $scope.SDNFormAlerts = [];

        $scope.closeAlert = function(index) {
            $scope.SDNFormAlerts.splice(index, 1);
        };

        ofcs.save($scope.ofc,function(){
            console.log('success');
            $location.path("/");
        },function(err){
            console.log('error');
            $scope.SDNFormAlerts.push({type: 'danger', msg: err.statusText});
        });


    }

    $scope.closeAlert = function(index) {
        $scope.SDNFormAlerts.splice(index, 1);
    };


    $scope.cancel = function() {
        $location.path('/fw-setup');
    }


})

d4allApp.controller('AmsController', function AmsController($scope, $location, $resource) {

    $scope.brands = [
        {name:'Radware DefensePro', value:'Radware DefensePro'},
        {name:'Other', value:'Other'}
    ];

    $scope.changeBrand = function() {

        if ($scope.ams.brand == 'Radware DefensePro') {
            $scope.ams.mgmtPort = '0';
            $scope.ams.healthCheckFrequency = '60';
        } else {
            $scope.ams.mgmtPort = '-1';
            $scope.ams.healthCheckFrequency = '-1';
            $scope.ams.status = 'UNKNOWN';
        }

    };

    //Set the first option as the default
    //$scope.ams.brand = $scope.brands[0].value;

    $scope.ams = {brand : $scope.brands[0].value};

    var amss = $resource(REST_SERVER + '/rest/app/df/amss');

    $scope.save = function() {

        $scope.AMSFormAlerts = [];

        $scope.closeAlert = function(index) {
            $scope.AMSFormAlerts.splice(index, 1);
        };

        $scope.ams.forDiversion = true;

        amss.save($scope.ams,function(){
            console.log('success');
            $location.path("/");
        },function(err){
            console.log('error');
            $scope.AMSFormAlerts.push({type: 'danger', msg: err.statusText});
        });


    }

    $scope.closeAlert = function(index) {
        $scope.AMSFormAlerts.splice(index, 1);
    };


    $scope.cancel = function() {
        $location.path('/fw-setup');
    }


})

d4allApp.controller('NetnodeController', function NetnodeController($scope, $location, $resource, $log, connsToAddService, linksToAddService) {

    var nodes = $resource(REST_SERVER + '/rest/app/df/netnodes');

    //initialize the services used to store the protected links and ams connections
    linksToAddService.linksToAdd = [];
    connsToAddService.connsToAdd = [];

    $scope.save = function(connsToAdd) {

        $log.debug('Netnodes: AMS Connections:' + connsToAddService.connsToAdd);
        $log.debug('Netnodes: Protected Links:' + linksToAddService.linksToAdd);

        var theConnsToAdd = connsToAddService.connsToAdd;

        //serialize the AMS connections
        var strAMSConns = "";
        for (var i=0; i<theConnsToAdd.length; i++) {
            if (i>0) {
                strAMSConns += "::";
            }

            var currConn = theConnsToAdd[i];
            strAMSConns += currConn.name + ":" + currConn.amsname + ":" + currConn.nnnorthport + ":" + currConn.nnsouthport + ":" + currConn.amsnorthport + ":" + currConn.amssouthport ;

        }

        $log.debug('Serialized netnode AMS connections:' + strAMSConns);

        $scope.node.amsConnectionsStr = strAMSConns;

        //Serialize the Incoming Traffic Ports
        var currTrafficPorts = linksToAddService.linksToAdd;

        var strTrafficPorts = "";
        var strProtectedLinks = "";

        for (var i=0 ; i<currTrafficPorts.length; i++) {
            if (i>0) {
                strTrafficPorts += "::";
                strProtectedLinks += "::";
            }
            strTrafficPorts += "north" + i + ":" + currTrafficPorts[i].incoming + ":0:north";
            strTrafficPorts += "::";
            strTrafficPorts += "south" + i + ":" + currTrafficPorts[i].outgoing + ":0:south";

            strProtectedLinks += "north_" + currTrafficPorts[i].incoming  + "_to_south_" + currTrafficPorts[i].outgoing + ":" + currTrafficPorts[i].incoming + ":" + currTrafficPorts[i].outgoing;

        }

        $log.debug('Serlialized traffic ports:' + strTrafficPorts);
        $scope.node.trafficPortsStr = strTrafficPorts;

        $log.debug('Serlialized protected links:' + strProtectedLinks);
        $scope.node.protectedLinksStr = strProtectedLinks;

        $scope.NNFormAlerts = [];

        $scope.closeAlert = function(index) {
            $scope.NNFormAlerts.splice(index, 1);
        };

        nodes.save($scope.node,function(){
            console.log('success');
            $location.path("/");
        },function(err){
            console.log('error');
            $scope.NNFormAlerts.push({type: 'danger', msg: err.statusText});
        });

    }

    $scope.cancel = function() {
        $location.path('/fw-setup');
    }

    $scope.closeAlert = function(index) {
        $scope.NNFormAlerts.splice(index, 1);
    };

})


d4allApp.factory("connsToAddService", function(){

    return {
        connsToAdd: []
    };

});

d4allApp.factory("linksToAddService", function(){

    return {
        linksToAdd: []
    };

});

d4allApp.factory("AMSService", function($resource){

    var amsResource = $resource(REST_SERVER + "/rest/app/df/amss/:label",
        {label:'@label'},{get:{method:'GET', isArray:true}});

    return {
        getAmss: function(callback) {
            amsResource.get(function(amsResult){
                callback.resolve(amsResult);
            });
        },
        deleteAms:  function(label) {
            amsResource.delete({label:label}, function() {
                console.log('success');
                //refresh scope
                //$scope.amss = amss.get();
            }, function(err) {
                console.log('error:' + err.statusText);
            });
        }
    }

});



/******   [---- VIEW -----] AMSConnection MODAL ***********/


d4allApp.controller('AmsConnModalController', function AmsConnModalController($scope, $modal, $log) {


    $scope.open = function (amsConnectionsStr) {

        $log.debug(amsConnectionsStr);

        var amsConns = [];

        var amsConnsStrings = amsConnectionsStr.split('::');
        for (var i=0 ; i < amsConnsStrings.length; i++){

            var amsConnString = amsConnsStrings[i].split(':') ;

            $log.debug(amsConnString);

            var amsConn = {};
            if (amsConnString[4] < 0 && amsConnString[5] < 0){
                amsConn = {name:amsConnString[0], amsname:amsConnString[1], nnnorthport:amsConnString[2], nnsouthport:amsConnString[3], amsnorthport:amsConnString[4], amssouthport:amsConnString[5], status:amsConnString[6], brand:'Other' };
            }else{
                amsConn = {name:amsConnString[0], amsname:amsConnString[1], nnnorthport:amsConnString[2], nnsouthport:amsConnString[3], amsnorthport:amsConnString[4], amssouthport:amsConnString[5], status:amsConnString[6], brand:'Radware DefensePro' };
            }

            //var amsConn = {name:amsConnString[0], amsname:amsConnString[1], nnnorthport:amsConnString[2], nnsouthport:amsConnString[3], amsnorthport:amsConnString[4], amssouthport:amsConnString[5] };

            amsConns.push(amsConn);

        }

        $scope.amsConns = amsConns ;

        var modalInstance = $modal.open({
            templateUrl: 'amsConnModalContent.html',
            controller: ModalInstanceCtrl,
            resolve: {
                amsConns: function () {
                    return $scope.amsConns;
                }
            }
        });

    };

});

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

var ModalInstanceCtrl = function ($scope, $modalInstance, amsConns) {

    $scope.amsConns = amsConns;

    $scope.ok = function () {
        $modalInstance.dismiss('ok');
    };
};




/******    [--- ADD ----] AMSConnections MODAL ***********/


d4allApp.controller('AddAmsConnModalController', function AddAmsConnModalController($scope, $modal, $log, $resource, connsToAddService) {

    $scope.connsToAdd = [];

    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'AddAmsConnModalContent.html',
            controller: AddAmsConnModalInstanceController,
            resolve: {
                connsToAdd: function () {
                    return $scope.connsToAdd;
                }
            }
        });

        modalInstance.result.then(function (connsToAdd) {

            $log.debug('got back from modal. connsToAdd:' + connsToAdd.length);

            $scope.connsToAdd = connsToAdd;

            //update shared object with netnodes controller
            connsToAddService.connsToAdd = connsToAdd;

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.remove = function(connToRemove) {

        for(var i = $scope.connsToAdd.length - 1; i >= 0; i--) {
            if($scope.connsToAdd[i].name === connToRemove.name
                && $scope.connsToAdd[i].amsname === connToRemove.amsname
                && $scope.connsToAdd[i].nnnorthport === connToRemove.nnnorthport
                && $scope.connsToAdd[i].nnsouthport === connToRemove.nnsouthport
                && $scope.connsToAdd[i].amsnorthport === connToRemove.amsnorthport
                && $scope.connsToAdd[i].amssouthport === connToRemove.amssouthport) {
                $scope.connsToAdd.splice(i, 1);
            }
        }

    }

});

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

var AddAmsConnModalInstanceController = function ($scope, $modalInstance, $log, $q, connsToAdd, AMSService) {

    $scope.connsToAdd = connsToAdd;

    $scope.amss = [];

    //this will happen once we return from getting the amss (async)
    var defer = $q.defer();
    defer.promise.then(function(val){
        $scope.amss = val;
        //Set the first option as the default
        //$scope.ams = $scope.amss[0].value;
        $scope.connToAdd.name = $scope.connToAdd.amsname = $scope.amss[0].label;
        $scope.connToAdd.brand = $scope.amss[0].brand;
        if ($scope.connToAdd.brand == 'Other'){
            $scope.connToAdd.amsnorthport = -1;
            $scope.connToAdd.amssouthport = -2;
        }
    });

    AMSService.getAmss(defer);

    //$scope.connToAdd = {"amsnorthport" : 1, "amssouthport" : 2};
    $scope.connToAdd = {};

    $scope.changeAMSName = function(){
        $scope.connToAdd.name = $scope.connToAdd.amsname;
        for (var i=0; i<$scope.amss.length; i++) {
            if ($scope.amss[i].label == $scope.connToAdd.amsname) {
                $scope.connToAdd.brand = $scope.amss[i].brand;
            }
            if ( $scope.connToAdd.brand == 'Other'){
                $scope.connToAdd.amsnorthport= -1;
                $scope.connToAdd.amssouthport= -2;
            }
        }
    };

    $scope.ok = function (connToAdd) {

        $log.debug('addedAMSConnection:' + connToAdd.name);

        connsToAdd.push(connToAdd);

        $modalInstance.close(connsToAdd);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};



/******    [--- ADD ----] ProtectedLinks MODAL ***********/


d4allApp.controller('AddProtectedLinksModalController', function AddProtectedLinksModalController($scope, $modal, $log, linksToAddService) {

    $scope.linksToAdd = [];

    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'AddProtectedLinksModalContent.html',
            controller: AddProtectedLinksModalInstanceController,
            resolve: {
                linksToAdd: function () {
                    return $scope.linksToAdd;
                }
            }
        });

        modalInstance.result.then(function (linksToAdd) {

            $scope.linksToAdd = linksToAdd;

            //update shared object with netnodes controller
            $log.debug('Updating linksToAddService with : ' + linksToAdd.length + ' links');
            linksToAddService.linksToAdd = linksToAdd;

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.remove = function(linkToRemove) {

        for(var i = $scope.linksToAdd.length - 1; i >= 0; i--) {
            if($scope.linksToAdd[i].incoming === linkToRemove.incoming
                && $scope.linksToAdd[i].outgoing === linkToRemove.outgoing) {
                $scope.linksToAdd.splice(i, 1);
            }
        }

    }

});

// Please note that $modalInstance represents a modal window (instance) dependency.
// It is not the same as the $modal service used above.

var AddProtectedLinksModalInstanceController = function ($scope, $modalInstance, $log, linksToAddService) {

    $scope.currentlinksToAdd = linksToAddService.linksToAdd;

    $scope.ok = function (linkToAdd) {

        //Check if any of the numbers are already used
        var portAlreadyUsed = false;
        for (var i=0; i < $scope.currentlinksToAdd.length; i++) {
            if ($scope.currentlinksToAdd[i].incoming == linkToAdd.incoming ||
                $scope.currentlinksToAdd[i].incoming == linkToAdd.outgoing ||
                $scope.currentlinksToAdd[i].outgoing == linkToAdd.incoming ||
                $scope.currentlinksToAdd[i].outgoing == linkToAdd.outgoing ) {
                portAlreadyUsed = true;
                break;
            }
        }

        var samePorts = (linkToAdd.incoming == linkToAdd.outgoing);

        if (samePorts) {
            alert('Incoming and outgoing ports must be different.');
        } else if(portAlreadyUsed) {
            alert('Port number already used in this Protected Links list.');
        } else {

            $log.debug('addedProtectedLink:' + linkToAdd.incoming + '->' + linkToAdd.outgoing);

            $scope.currentlinksToAdd.push(linkToAdd);

            linksToAddService.linksToAdd = $scope.currentlinksToAdd;

            $modalInstance.close(linksToAddService.linksToAdd);
        }

    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};




d4allApp.controller('DFConfigController', function FWConfigController($scope, $resource,$timeout,$modal,$log,$http) {

    $scope.DFConfigAlerts = [];

    $scope.pollingInterval = POLLING_TIMEOUT/1000;

    var pos = $resource(REST_SERVER + "/rest/app/df/pos/:label",
        {label:'@label'},{get:{method:'GET', isArray:true}});


    var pns = $resource(REST_SERVER + "/rest/app/df/pns/:label",
        {label:'@label'},{get:{method:'GET', isArray:true}});


    // Make the first poller open and close the server connection modal
    var posPoller = function () {
        pos.get(function(posResult){

            $scope.pns = pns.get();
            $scope.pns.$promise.then(function(pnsResult){
                for (var i = 0; i < pnsResult.length; i++) {

                    //$log.debug(pnsResult[i].label);

                    var idx = pnsResult[i].label.indexOf("__");
                    var poName = pnsResult[i].label.substring(0,idx);

                    //only check if this policy is active
                    if (pnsResult[i].operationalStatus != 'CANCELED') {
                        $log.debug (poName + " has the policy");

                        for (var j = 0; j < posResult.length; j++) {
                            if (posResult[j].label == poName){
                                posResult[j].hasPolicy = true;
                                break;
                            }
                        }
                    }

                }
            });

            $scope.pos = posResult;
            console.log('success');
            if ($scope.offlineModal) {
                $scope.offlineModal.close();
                $scope.offlineModal = undefined;
            }
        }, function(response){
            console.log('error');
            if (!$scope.offlineModal) {
                $scope.offlineModal = $modal.open({
                    templateUrl: 'offline.html',
                    keyboard:false,
                    scope:$scope,
                    backdrop:'static'
                });
            }
        });
        $timeout(posPoller,POLLING_TIMEOUT);
    }
    posPoller();




    $scope.deletePO = function (name) {

        $scope.deletePOOK = confirm('Are you absolutely sure you want to delete PO: ' + name + ' ?');

        if ($scope.deletePOOK) {

            pos.delete({label: name}, function () {
                console.log('success');
                //refresh scope
                $scope.pos = pos.get();
            }, function (err) {
                console.log('error: ' + err.statusText);
            });
        }

    };

    $scope.addPolicy = function (po) {
        var data = {
            "label": po.label + "__default",
            "ipVersion": "IPV4",
            "dstAddr": po.dstAddr,
            "dstAddrPrefixLen": po.dstAddrPrefixLen,
            "protectionSLA": "",
            "mitigationConfirmation": false,
            "ofBasedDetection": true,
            "anomalyThresholdPercentage": 80,
            "mitigationScope": "ATTACKED",
            "props": po.props,
            "statsCollectionStatus": "NONE",
            "virtualNetid": 0, // TODO: po.virtualNetid,
            "detectorLabel": "of_rate_based_detector",
            "symmetricDvsn": true,
//            "netNodeLabels" : $scope.poNetNodeLabels,
            "amsConfigProps":{}
        };

        $http.post(REST_SERVER + '/rest/app/df/pns', data).success(function() {
            $scope.DFConfigAlerts.push({type: 'success', msg: 'Added default policy to ' + po.label + '.'});
            $log.debug('Successfully created policy.');
        }).error(function(data, status, headers, config){
            $log.debug('error');
            $scope.DFConfigAlerts.push({type: 'danger', msg: 'Error ' + status + '. Could not add Policy.'});
        });

    }

    $scope.removePolicy = function (po) {
        $scope.deletePOPolicy = confirm('Are you absolutely sure you want to delete a PO: ' + po.label + ' Policy ?');
        if ($scope.deletePOPolicy) {
            $log.debug('Remove Policy called: ' + po.label + '__default');
            pns.delete({label: po.label + '__default'}, function () {
                $scope.DFConfigAlerts.push({type: 'success', msg: 'Removed default policy from ' + po.label + '.'});
            }, function (err) {
                $scope.DFConfigAlerts.push({type: 'danger', msg: err.statusText});
            });
        }

    }

    $scope.closeAlert = function(index) {
        $scope.DFConfigAlerts.splice(index, 1);
    };


    $scope.openPolicyDetails = function () {

        var modalInstance = $modal.open({
            templateUrl: 'DefaultPolicyDetails.html'
        });

    };



});


d4allApp.controller('PoController', function PoController($scope, $location, $resource, $log) {

    var pos = $resource(REST_SERVER + '/rest/app/df/pos');

    //define specific resource for GET only, the one above is used for POST in creating POs
    //used in testing if PO name already exists
    var getpos = $resource(REST_SERVER + "/rest/app/df/pos/:label",
        {label:'@label'},{get:{method:'GET', isArray:true}});
    $scope.allPos = getpos.get();

    $scope.save = function(poToAdd) {

        //First check that the a PO with this name doesn't exist already
        for (var i=0; i < $scope.allPos.length; i++){
            if ($scope.allPos[i].label == poToAdd.label) {
                alert(poToAdd.label + ' already exists. Please try a different name');
                return;
            }
        }

        $log.debug('Going to save PO');

        var poToSave = angular.copy(poToAdd)

//        var propsToSave = new Object();
//        for (var i=0 ; i < poToAdd.props.length; i++){
//            propsToSave['nb_vex_'+i] = poToAdd.props[i].text;
//        }

        //$log.debug(poToAdd.props);
        //$log.debug(propsToSave);

//        poToSave.props = propsToSave;

        $scope.PoFormAlerts = [];

        $scope.closeAlert = function(index) {
            $scope.PoFormAlerts.splice(index, 1);
        };

        pos.save(poToSave,function(){
            //$log.debug('success');
            $location.path("/df-setup");
        },function(err){
            $log.debug('error');
            $scope.PoFormAlerts.push({type: 'danger', msg: err.statusText});
        });

    }

    $scope.cancel = function() {
        $location.path('/df-setup');
    }


    $scope.closeAlert = function(index) {
        $scope.PoFormAlerts.splice(index, 1);
    };
})

d4allApp.controller('ReportsController', function ReportsController($scope, $route, $location, $resource, $log, FlightRecorderService) {

    var reportData = $route.current.reportData;
    $scope.reportData = reportData;

    $log.debug('ReportsController generating report of type: ' + reportData.reportType);

    var timePeriod = {
        fromDate : new Date(),
        toDate : new Date()
    };

    var reportParams = {
        filename : '',
        numberOfRows : '',
        fileNameNumRows : '',
        numberOfDays : ''
    }

    $scope.timePeriod = timePeriod;
    $scope.reportParams = reportParams;

    // event types
    //$scope.evTypes = ['DF_security', 'DF_config', 'DF_operational'];
    if (reportData.reportType == 'DF') {

        var evTypeDFConfig = {display:"DefenseFlow Configuration", value:"DF_config"};
        var evTypeDFOperational = {display:"DefenseFlow Operational", value:"DF_operational"};
        var evTypeDFFailure = {display:"DefenseFlow Failure", value:"DF_failure"};
        var evTypeDFSecurity = {display:"DefenseFlow Security", value:"DF_security"};
        var evTypeAMSOperational = {display:"AMS Operational", value:"AMS_operational"};
        var evTypeAMSFailure = {display:"AMS Failure", value:"AMS_failure"};
        var evTypeAMSSecurity = {display:"AMS Security", value:"AMS_security"};
        var evSDNOperational = {display:"SDN Operational", value:"OFC_operational"};
        var evSDNFailure = {display:"SDN Failure", value:"OFC_failure"};

        $scope.evTypes = [evTypeDFConfig, evTypeDFOperational,evTypeDFFailure,evTypeDFSecurity, evTypeAMSOperational, evTypeAMSFailure, evTypeAMSSecurity, evSDNOperational, evSDNFailure ];

        // selected event types
        $scope.evTypesSelection = ['DF_config', 'DF_operational' ,'DF_failure' ,'DF_security' ,'AMS_operational' ,'AMS_failure' ,'AMS_security' ,'OFC_operational' ,'OFC_failure'];


    } else {
        var evTypeFWConfig = {display:"Framework Configuration", value:"Framework_config"};
        var evTypeFWOperational = {display:"Framework Operational", value:"Framework_operational"};
        var evTypeFWFailure = {display:"Framework Failure", value:"Framework_failure"};
        var evTypeAMSOperational = {display:"AMS Operational", value:"AMS_operational"};
        var evTypeAMSFailure = {display:"AMS Failure", value:"AMS_failure"};
        var evTypeAMSSecurity = {display:"AMS Security", value:"AMS_security"};
        var evSDNOperational = {display:"SDN Operational", value:"OFC_operational"};
        var evSDNFailure = {display:"SDN Failure", value:"OFC_failure"};

        $scope.evTypes = [evTypeFWConfig, evTypeFWOperational,evTypeFWFailure,evTypeAMSOperational, evTypeAMSFailure, evTypeAMSSecurity, evSDNOperational, evSDNFailure ];

        // selected event types
        $scope.evTypesSelection = ['Framework_config', 'Framework_operational' ,'Framework_failure' ,'AMS_operational' ,'AMS_failure' ,'AMS_security' ,'OFC_operational' ,'OFC_failure'];

    }



    // toggle selection for a given event type by name
    $scope.toggleSelection = function toggleSelection(evTypeName) {
        var idx = $scope.evTypesSelection.indexOf(evTypeName.value);

        // is currently selected
        if (idx > -1) {
            $scope.evTypesSelection.splice(idx, 1);
        }
        // is newly selected
        else {
            $scope.evTypesSelection.push(evTypeName.value);
        }
    };

    $scope.refresh = function refresh() {

        $log.debug("refreshing " + reportData.reportType + " report");

        FlightRecorderService.getRecords($scope.evTypesSelection, $scope.timePeriod).then(function(dataResponse) {
            $log.debug(dataResponse);
            $scope.records = dataResponse.data;
            $scope.totalRecords = $scope.records.length;
            $scope.lastQuery = new Date();
            //$scope.totalRecords = $scope.records.length;
            //$scope.currentPage = 1;

            if ($scope.records.length > 9999) {
                ReportAlerts.push({type: 'danger', msg: 'There are more than 10,000 rows in the result. Please refine your query to see all records.'});
            }
        });

    }

    $scope.refresh();


    $scope.dump = function dump() {

        $scope.ReportAlerts = [];

        //http://localhost:8086/rest/general/fr/dump?toFile=/tmp/abcde&fromDate=10/14/2013_17:17:41&toDate=10/20/2013_17:17:46&maxNum=10&filter=DF_security,AMS_security

        $log.debug("dumping " + reportData.reportType + " report to file:" + $scope.reportParams.fileName);

        FlightRecorderService.dumpRecords($scope.evTypesSelection, $scope.timePeriod, $scope.reportParams.fileName).then(function(dataResponse) {
            $scope.ReportAlerts.push({type: 'success', msg: 'Sent file dump request to server.'});
        }, function(dataResponse) {
            $scope.ReportAlerts.push({type: 'danger', msg: dataResponse.statusText});
        })

    }


    $scope.refreshNumRows = function refreshNumRows(){

        $log.debug("refreshing report with # of rows: " + $scope.reportParams.numberOfRows);

        FlightRecorderService.getRecordsNum($scope.evTypesSelection, $scope.reportParams.numberOfRows).then(function(dataResponse) {
            $log.debug(dataResponse);
            $scope.records = dataResponse.data;
            $scope.totalRecords = $scope.records.length;
            $scope.lastQuery = new Date();
            //$scope.totalRecords = $scope.records.length;
            //$scope.currentPage = 1;
        });

    }


    $scope.dumpNumRows = function dumpNumRows() {

        $log.debug("filename numrows:" + $scope.reportParams.fileNameNumRows);

    }

    $scope.cleanup = function cleanup() {

        if (confirm('This will PERMANENTLY delete the records. Are you sure?')) {
            FlightRecorderService.cleanup( $scope.reportParams.numberOfDays).then(function(dataResponse) {
                $log.debug(dataResponse);
                //$scope.totalRecords = $scope.records.length;
                //$scope.currentPage = 1;
            });
        }

    }

    $scope.closeAlert = function(index) {
        $scope.ReportAlerts.splice(index, 1);
    };

    //can be used to sort by other columns (see:http://jsfiddle.net/vojtajina/rvdww/6/)

    $scope.sortDate = function(item) {
        return new Date(item.eventTime);
    }

})



/******** Navigation Menu ************/

d4allApp.controller("MenuController", function MenuController($scope) {
    $scope.menus = [
        {
            title: "Framework",
            submenus: [
                {
                    title: "Setup",
                    action: "#/fw-setup"
                },
                {
                    title: "Reports",
                    action: "#/fw-reports"
                }
            ]
        },
        {
            title: "Defense4All",
            submenus: [
                {
                    title: "Setup",
                    action: "#/df-setup"
                },
                {
                    title: "Reports",
                    action: "#/df-reports"
                }
            ]
        }
    ]
});


/******** DatePicker *********/

d4allApp.controller('DatepickerCtrl', function DatepickerDemoCtrl($scope, $log) {



    $scope.open = function($event) {

        $log.debug('open popup called:' + $event)

        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        showButtonBar: 'false'
    };

    //fromDate=10/13/2013_16:55:00&toDate=10/20/2013_16:55:00&filter=DF_security,AMS_security
    //$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = 'MM/dd/yyyy';
    $scope.minDate = '2013-06-01';
    $scope.maxDate = '2150-06-01';
});
