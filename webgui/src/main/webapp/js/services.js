

//Flight Recorder Service, used by the reports pages

d4allApp.service('FlightRecorderService', ['$http' , '$log', '$filter', function($http, $log, $filter) {

    //"http://localhost:8086/rest/general/fr/events?maxNum=10&fromDate=10/13/2013_16:55:00&toDate=10/20/2013_16:55:00&filter=DF_security,AMS_security

    this.getRecords = function(filter, timeperiod) {

        strFilter = filter.join();

        // $http() returns a $promise that we can add handlers with .then()
        return $http({
            method: 'GET',
            url: REST_SERVER + '/rest/general/fr/events?maxNum=10000&' +
                'fromDate='+ $filter('date')(timeperiod.fromDate, "MM/dd/yyyy") + '_00:00:00' +
                '&toDate='+ $filter('date')(timeperiod.toDate, "MM/dd/yyyy") +'_23:59:59' +
                '&filter=' + strFilter
            //params: 'limit=10, sort_by=created:desc',
            //headers: {'Authorization': 'Token token=xxxxYYYYZzzz'}
        });

    };

    this.getRecordsNum = function(filter, numberOfRecords) {

        strFilter = filter.join();

        // $http() returns a $promise that we can add handlers with .then()
        return $http({
            method: 'GET',
            url: REST_SERVER + '/rest/general/fr/latest?maxNum=' + numberOfRecords +
                '&filter=' + strFilter
        });

    };


    this.dumpRecords = function(filter, timeperiod,fileName) {

        strFilter = filter.join();

        // $http() returns a $promise that we can add handlers with .then()
        return $http({
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            url: REST_SERVER + '/rest/general/fr/dump',
            data: {
                'toFile' : fileName ,
                'fromDate' :  $filter('date')(timeperiod.fromDate, "MM/dd/yyyy") + '_00:00:00',
                'toDate' :  $filter('date')(timeperiod.toDate, "MM/dd/yyyy") +'_23:59:59',
                'filter' : strFilter

            }

        });

    };


    this.cleanup = function(numberOfDays) {

        // $http() returns a $promise that we can add handlers with .then()
        return $http({
            method: 'POST',
            headers: {'Content-Type': 'application/json'} ,
            url: REST_SERVER + '/rest/general/fr/cleanup' ,
            data: {'olderDays' : numberOfDays}
        });

    };


}]);
