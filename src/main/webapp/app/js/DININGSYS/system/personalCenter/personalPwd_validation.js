var modifyPwdFormValidation = function () {
    var modifyPwdValidation = function () {
        var modifyPwd = $("#modifyPwd");
        var error1 = $('.alert-danger', modifyPwd);
        var success1 = $('.alert-success', modifyPwd);
        modifyPwd.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                oldPwd: {
                    minlength: 6,
                    required: true
                },
                newPwd: {
                    minlength: 6,
                    required: true
                },
                reNewPwd: {
                    minlength: 6,
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },

            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },

            submitHandler: function (form) {
                success1.show();
                error1.hide();
            }
        });
    };
    var handleWysihtml5 = function() {
        if (!jQuery().wysihtml5) {
            return;
        }

        if ($('.wysihtml5').size() > 0) {
            $('.wysihtml5').wysihtml5({
                "stylesheets": ["assets/plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
            });
        }
    };


    return {
        modifyPwdFormInit: function () {
            handleWysihtml5();
            modifyPwdValidation();
        },
    };
}();