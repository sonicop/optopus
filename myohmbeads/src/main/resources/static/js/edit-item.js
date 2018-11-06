$$(document).on('page:init', '.page[data-name="edit-item"]', function (e) {
  app.methods.prepareDropDowns();
});



$$(document).on('page:afterin', '.page[data-name="edit-item"]', function (e, page) {
  var transactionId = page.route.params.transactionId;
  app.methods.getUserProduct(transactionId).then(function(transaction) {
    $$("select[name='brandId']").val(transaction.brandId);
    $$("input[name='sku']").val(transaction.sku);
    $$("input[name='serialNumber']").val(transaction.serialNumber);
    $$("input[name='currencyCode']").val(transaction.currencyCode);
    $$("input[name='purchasePrice']").val(transaction.purchasePrice);
    $$("input[name='purchaseFrom']").val(transaction.purchaseFrom);
    $$("input[name='purchaseDate']").val(transaction.purchaseDate);
    $$("textarea[name='note']").val(transaction.note);
    app.data.initialFormData = app.form.convertToData('#edit-item-form');
    
    var initialDateTxt = $$('input[name="purchaseDate"]').val();
    var initialDates;
    if (initialDateTxt === '') {
      initialDates = [];
    } else {
      initialDates = [new Date(initialDateTxt).getTimezoneOffset() / 60];
    }
    var today = new Date();
    today.setHours(0,0,0,0);
    var tomorrow = new Date().setDate(today.getDate() + 0);
    app.calendar.create({
        inputEl: 'input[name="purchaseDate"]',
        value: initialDates,
        dateFormat: 'yyyy-mm-dd',
        closeOnSelect: true,
        disabled: {
          from: tomorrow
        }
    });  
  });

  $$('.page[data-name="edit-item"] a#update').on('click', function() {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var formData = app.form.convertToData('#edit-item-form');
    if (app.methods.equalsObjects(app.data.initialFormData, formData)) {
      app.dialog.create({
        title: 'Warning!',
        text: 'No data has been changed.',
        buttons: [{text:'Cancel'}]
      }).open();
      return;
    }
    var errors = app.methods.validatePurchaseTranstationForm(formData);
    if (errors) {
      return;
    }
    app.dialog.confirm('Are you sure?', 'Confirm', function () {
      app.methods.updateUserProduct(formData, transactionId).then(
        function(data) {
          app.dialog.alert('Updated successfully!', app.name, function() {
            page.router.back();
          });
        },
        function(xhr) {
          app.methods.displayFormErrors(xhr.responseText);
        }
      );
    });
  });
  
  $$('.page[data-name="edit-item"] a#remove').on('click', function() {
    app.dialog.confirm('Are you sure?', function () {
      app.methods.deleteUserProduct(transactionId).then(
        function(data) {
          app.dialog.alert('Removed successfully!', app.name, function() {
            page.router.back();
          });
        },
        function(xhr) {
          app.methods.displayFormErrors(xhr.responseText);
        }
      );
    });
  });
  $$("input,textarea").on('keyup keydown change',function() {
    var formData = app.form.convertToData('#edit-item-form');
    if (app.methods.equalsObjects(app.data.initialFormData, formData) === false) {
      $$('#update').removeClass('disabled');
    } else {
      $$('#update').addClass('disabled');
    }
  });  
});