angular.module('LiftChat', ['ChatServices'])

.controller('ChatController',
  ['$scope', 'ChatService' , function($scope, chatSvc) {
     $scope.onSend = function() {
       if ($scope.hasJoined){
         //This is the downside of using the BindingToServer on chatInput
         var tmpChatInput =  $scope.chat1.chatInput;
         $scope.chat1.chatInput = '';
         setTimeout(function() {chatSvc.sendChat(tmpChatInput);},500);
       }
     }
     $scope.onJoin =  function() {
       if (!$scope.hasJoined){
         chatSvc.sendJoin();
         $scope.hasJoined = true;
         $scope.joinButtonText = "Leave";
       } else {
         chatSvc.sendLeave();
         $scope.hasJoined = false;
         $scope.joinButtonText = "Join";
       }
     }
     $scope.init = function() {
       $scope.hasJoined = false;
       $scope.joinButtonText = "Join";
     }
     $scope.init();
  }]
)
;