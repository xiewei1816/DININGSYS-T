var itemFileTypeValidation = function () {
    var itemFileBigTypeValidation = function () {
        var editFormEditForm = $("#itemFileBigTypeForm");
        var error1 = $('.alert-danger', editFormEditForm);
        var success1 = $('.alert-success', editFormEditForm);
        editFormEditForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                num: {
                    required: true
                },
                name: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                scrollTo(error1, -200);
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

    var itemFileSmallTypeValidation = function () {
        var editFormEditForm = $("#itemFileSmallTypeForm");
        var error1 = $('.alert-danger', editFormEditForm);
        var success1 = $('.alert-success', editFormEditForm);
        editFormEditForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                num: {
                    required: true
                },
                name: {
                    required: true
                },
                pId: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                scrollTo(error1, -200);
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

    var itemFileValidation = function () {
        var editFormEditForm = $("#ppEditFormId");
        var error1 = $('.alert-danger', editFormEditForm);
        var success1 = $('.alert-success', editFormEditForm);
        editFormEditForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                num: {
                    required: true
                },
                name: {
                    required: true
                },
                standardPrice:{
                	required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                scrollTo(error1, -200);
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

    var itemFileNutritionValidation = function () {
        var itemFileNutritionEditForm = $("#editForm");
        var error1 = $('.alert-danger', itemFileNutritionEditForm);
        var success1 = $('.alert-success', itemFileNutritionEditForm);
        itemFileNutritionEditForm.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {
                code: {
                    required: true
                },
                bzsrl: {
                    required: true
                },
                name: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                scrollTo(error1, -200);
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
        bigFormInit: function () {
            handleWysihtml5();
            itemFileBigTypeValidation();
        },
        smallFormInit: function () {
            handleWysihtml5();
            itemFileSmallTypeValidation();
        },
        itemFileFormInit: function () {
            handleWysihtml5();
            itemFileValidation();
        },
        itemFileNutritionValidation: function () {
            handleWysihtml5();
            itemFileNutritionValidation();
        }
    };
}();