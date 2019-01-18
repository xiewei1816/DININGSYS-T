var userFormValidation = function () {
    var userAddValidation = function () {
        var userAddForm = $("#userAddForm");
        var error1 = $('.alert-danger', userAddForm);
        var success1 = $('.alert-success', userAddForm);
        userAddForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                username: {
                    minlength: 2,
                    required: true
                },
                nickname: {
                    minlength: 2,
                    required: true
                },
                phone: {
                    required: true,
                    minlength:11,
                    maxlength:11
                },
                email: {
                    required: true,
                    email:true
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
    
    var userEditValidation = function () {
        var userEditForm = $("#userEditForm");
        var error1 = $('.alert-danger', userEditForm);
        var success1 = $('.alert-success', userEditForm);
        userEditForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                username: {
                    minlength: 2,
                    required: true
                },
                nickname: {
                    minlength: 2,
                    required: true
                },
                phone: {
                    required: true,
                    minlength:11,
                    maxlength:11
                },
                email: {
                    required: true,
                    email:true
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
        addFormInit: function () {
            handleWysihtml5();
            userAddValidation();
        },
        EditFormInit: function () {
            handleWysihtml5();
            userEditValidation();
        }

    };
}();