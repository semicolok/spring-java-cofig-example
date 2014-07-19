app.controller('MainCtrl', function($scope) {
  init();
  function init() {
    $scope.boardList = [1,2,3,4,5,6,7,8];

    console.log($scope.boardList);
  };
});