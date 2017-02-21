$(document).ready(function() {

    //Organization input field
    var addValidateEvent = function (inputID, spanID, buttonID, entityName, fieldName) {
        var inputField = $('#' + inputID);
        inputField
            .keyup(function () {
                var value = inputField.val();
                $.ajax({
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
                        console.log(exists);
                        var InputSpan = $('#' + spanID);
                        var submitButton = $('#' + buttonID);
                        console.log(exists);
                        if (exists == "true") {
                            InputSpan.text(inputField.val() + " is already in use.");
                            InputSpan.css("display", "block");
                            submitButton.prop('disabled', true);

                        } else if (exists == "false") {
                            InputSpan.css("display", "none");
                            submitButton.prop('disabled', false);
                        }
                    }
                })
            });
    };

    addValidateEvent("organizationName", "organizationNameSpan", "submitButton", "organisation", "name");
    addValidateEvent("userEmailInput", "userEmailSpan", "submitButton", "user", "email");
});
