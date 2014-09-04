
d4allApp.filter('toIcon', function () {
    return function (ams) {
        console.log('ams_status:' + ams.status);

        if (ams.status == 'UNKNOWN') {
            return 'unknown';
        } else {
            if (ams.up === true) {
                return 'up'
            } else {
                return 'down';
            }
        }
    }

});

/*
Serialize the PO tags in a comma delimited string
 */
d4allApp.filter('poTags', function () {
    return function (rawTagString) {

        var propsList = "";
        var firstElement = true;
        for(var propt in rawTagString){
            if (!firstElement) {
                propsList += ",";
            } else {
                firstElement = false;
            }
            propsList += rawTagString[propt];
        }
        return propsList;
    }

});

/*
Display prettify for report event types
 */
d4allApp.filter('eventTypeDisplay', function () {
    return function (eventCode) {


        var eventTypes = {
            'DF_config':'DefenseFlow Configuration',
            'DF_operational':'DefenseFlow Operational',
            'DF_failure':'DefenseFlow Failure',
            'DF_security':'DefenseFlow Security',
            'AMS_operational':'AMS Operational',
            'AMS_failure':'AMS Failure',
            'AMS_security':'AMS Security',
            'OFC_operational':'SDN Operational',
            'OFC_failure':'SDN Failure',
            'Framework_config':'Framework Configuration',
            'Framework_operational':'Framework Operational',
            'Framework_failure':'Framework Failure'
        }
        return eventTypes[eventCode];


    }

});


/*
 Display filter for making other type AMSs values N/A
 */
d4allApp.filter('prettifyNull', function () {
    return function (value) {
        if (value === null || value === -1 || value === "" ) {
            return 'N/A'
        } else {
            return value;
        }
    }

});
