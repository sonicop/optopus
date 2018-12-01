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
    
    $$('#stock-photo').css('background-image','url(' + transaction.imageReference + ')');
    $$('#stock-photo').css('background-size','contain');
    
    var wrapper = document.getElementById('swiper-wrapper');
    wrapper.innerHTML = "";
    if (transaction.userImages) {
      var records = transaction.userImages;
      records.reverse();
      records.forEach(function(record) {
        var div = $$('<div>');
        div.addClass('swiper-slide');
        div.css('background-image', 'url(' + record.reference + ')');
        wrapper.appendChild(div[0]);
      });
      $$('#stock-photo').css('background-image','none');
      var swiper = app.swiper.create('.swiper-container', {
        speed: 900,
        spaceBetween: 50,
        pagination: {"el": ".swiper-pagination"}
      });    
      if (records && records.length > 0) {
        $$('#my-photo-wrapper').on('click', function() {
          app.methods.browseImages();
        });
      }
    }


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

  $$('a.check-and-back').on('click', function() {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var formData = app.form.convertToData('#edit-item-form');
    if (app.methods.equalsObjects(app.data.initialFormData, formData)) {
      page.router.back();
      return;
    } else {
      app.dialog.create({
        title: 'WARNING',
        text: 'You have entered some data.',
        buttons: [{
          text:'Update',
          onClick: function() {
            var errors = app.methods.validatePurchaseTranstationForm(formData);
            if (errors) {
              return;
            }
            app.methods.updateUserProduct(formData, transactionId).then(
              function(data) {
                app.methods.flashMessage('Item updated').then(function() {
                  page.router.back();
                });        
              },
              function(xhr) {
                app.methods.displayFormErrors(xhr.responseText);
              }
            );            
          }
        },
        {
          text:'Discard',
          onClick: function() {
             page.router.back();
             return;
           }}      
        ]
      }).open();
    }
  });
  
  $$('.page[data-name="edit-item"] a#update').on('click', function() {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var formData = app.form.convertToData('#edit-item-form');
    if (app.methods.equalsObjects(app.data.initialFormData, formData)) {
      app.dialog.create({
        title: 'WARNING',
        text: 'No data has been changed.',
        buttons: [{text:'Cancel'}]
      }).open();
      return;
    }
    var errors = app.methods.validatePurchaseTranstationForm(formData);
    if (errors) {
      return;
    }
    app.methods.updateUserProduct(formData, transactionId).then(
      function(data) {
        app.methods.flashMessage('Item updated').then(function() {
          page.router.back();
        });        
      },
      function(xhr) {
        app.methods.displayFormErrors(xhr.responseText);
      }
    );
  });
  
  $$('.page[data-name="edit-item"] a#remove').on('click', function() {
    app.methods.deleteUserProduct(transactionId).then(
      function(data) {
        page.router.back();
      },
      function(xhr) {
        app.methods.displayFormErrors(xhr.responseText);
      }
    );
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
