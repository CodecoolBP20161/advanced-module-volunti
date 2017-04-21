$(document).ready(function() {

    var validateWithAJAX = function (inputField, spanID, buttonID, entityName, fieldName, value) {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};

        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: "/registration/ValidateFieldIfExists",
            type: 'POST',
            headers: headers,
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
        });
    };


    var addValidateEvent = function (inputID, spanID, buttonID, entityName, fieldName) {
        var inputField = $('#' + inputID);
        validateWithAJAX(inputField, spanID, buttonID, entityName, fieldName, inputField.val());
        inputField.on("input propertychange", function () {
                var value = inputField.val();
                validateWithAJAX(inputField, spanID, buttonID, entityName, fieldName, value);
        });
    };

    var onChangeCheckIfFieldsMatch = function (inputID1, inputID2, submitButtonID, informationContainerID) {
        $('#' + inputID1 + ',#' + inputID2).on("input propertychange", function(){
            var input1 = $('#' + inputID1);            
            var input2 = $('#' + inputID2);
            var submitButton = $('#' + submitButtonID);
            var informationContainer = $('#' + informationContainerID);
            if (input1.val() !== input2.val() && input1.val().length === input2.val().length) {
                informationContainer.text("Passwords are not matching.");
                informationContainer.css("display", "block");
                submitButton.prop('disabled', true);

            } else{
                informationContainer.css("display", "none");
                submitButton.prop('disabled', false);
            }
        });
        
    };

    if(document.getElementById("organizationName")) {
        addValidateEvent("organizationName", "organizationNameSpan", "submitButton", "organisation", "name");
    }
    if(document.getElementById("userEmailInput")) {
        addValidateEvent("userEmailInput", "userEmailSpan", "submitButton", "user", "email");
    }
    if(document.getElementById("passwordMatcherSpan")) {
        onChangeCheckIfFieldsMatch("passWordInput1", "passWordInput2", "passwordSubmitButton", "passwordMatcherSpan");
    }
    if(document.getElementById("passwordMatcherSpan")) {
        onChangeCheckIfFieldsMatch("passWordInput1", "passWordInput2", "submitButton", "passwordMatcherSpan");
    }
});
