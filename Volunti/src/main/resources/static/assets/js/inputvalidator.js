$(document).ready(function() {
    var inputField = $('#organizationName');
    inputField
        .keyup(function() {
            var value = inputField.val();
            $.ajax({
                url: "/registration/ValidateFieldIfExists",
                type: 'POST',
                async: true,
                contentType: "application/json",
                  data:  JSON.stringify({
                      "entityName": "organisation",
                      "fieldName": "name",
                      "value": value
                  }),
                success: function(exists){
                    console.log(exists);
                    var InputSpan = $('#organizationNameSpan');
                    console.log(exists);
                    if(exists == "true") {
                        InputSpan.text(inputField.val() + " is already used.");
                        InputSpan.css("display", "block");
                    }else if(exists == "false"){
                        InputSpan.css("display", "none");
                    }
                }
            })
        })
});
