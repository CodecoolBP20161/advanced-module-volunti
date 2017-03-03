$(document).ready(function() {

    var validateWithAJAX = function (inputField, spanID, buttonID, entityName, fieldName, value) {$.ajax({
        url: "/registration/ValidateFieldIfExists",
        type: 'POST',
        async: true,
        contentType: "application/json",
        data: JSON.stringify({
            "entityName": entityName,
            "fieldName": fieldName,
            "value": value
        }),
        success: function (exists) {
            var InputSpan = $('#' + spanID);
            var submitButton = $('#' + buttonID);
            if (exists == "true") {
                InputSpan.text(inputField.val() + " is already in use.");
                InputSpan.css("display", "block");
                submitButton.prop('disabled', true);

            } else if (exists == "false") {
                InputSpan.css("display", "none");
                submitButton.prop('disabled', false);
            }
        }
    });};

    //Organization input field
    var addValidateEvent = function (inputID, spanID, buttonID, entityName, fieldName) {
        var inputField = $('#' + inputID);
        validateWithAJAX(inputField, spanID, buttonID, entityName, fieldName, inputField.val());
        inputField.on("input propertychange", function () {
                var value = inputField.val();
                validateWithAJAX(inputField, spanID, buttonID, entityName, fieldName, value);
        });
    };

    var onChangeCheckIfFieldsMatch = function (inputID1, inputID2, submitButtonID, informationContainerID) {
        $('#' + inputID1 + ',#' + inputID2).on("onload input propertychange", function(){
            var input1 = $('#' + inputID1);            
            var input2 = $('#' + inputID2);
            var submitButton = $('#' + submitButtonID);
            var informationContainer = $('#' + informationContainerID);
            if (input1.val() !== input2.val()) {
                informationContainer.text("Passwords are not matching.");
                informationContainer.css("display", "block");
                submitButton.prop('disabled', true);

            } else{
                informationContainer.css("display", "none");
                submitButton.prop('disabled', false);
            }
        });
        
    };

    addValidateEvent("organizationName", "organizationNameSpan", "submitButton", "organisation", "name");
    addValidateEvent("userEmailInput", "userEmailSpan", "submitButton", "user", "email");
    onChangeCheckIfFieldsMatch("passWordInput1", "passWordInput2", "passwordSubmitButton", "passwordMatcherSpan");
});
