$$(document).on('page:init', '.page[data-name="new-item"]', function (e, page) {
  app.methods.prepareDropDowns();
  var today = new Date();
  today.setHours(0,0,0,0);
  var tomorrow = new Date().setDate(today.getDate() + 0);
  app.calendar.create({
      inputEl: 'input[name="purchaseDate"]',
      dateFormat: 'yyyy-mm-dd',
      closeOnSelect: true,
      disabled: {
        from: tomorrow
      }
  });  
  app.data.initialFormData = app.form.convertToData('#new-item-form');
  
  var addNewItem = function(formData) {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var errors = app.methods.validatePurchaseTranstationForm(formData);
    if (errors) {
      return;
    }
    formData.sku = app.methods.extractSku(formData.sku);
    app.methods.saveUserProduct(formData).then(
      function() {
        app.methods.flashMessage('Item added').then(function() {
          page.router.back();
        });
      },
      function(xhr) {
        app.methods.displayFormErrors(xhr.responseText);
      }
    );
  };
  
  $$('#item-photo .swiper-container').on('click', function() {
    mainView.router.navigate('/photo-loader/');
  });

  $$('#zoom-in-icon').on('click', function(event) {
    app.methods.browseImages();
     event.stopPropagation();
  });
  
  $$('a.check-and-back').on('click', function() {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var formData = app.form.convertToData('#new-item-form');
    if (app.methods.equalsObjects(app.data.initialFormData, formData)) {
      page.router.back();
      return;
    } else {
      app.dialog.create({
        title: 'Warning!',
        text: 'You have entered some data.',
        buttons: [{
          text:'Save',
          onClick: function() {
            addNewItem(formData);
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
  $$('.page[data-name="new-item"] a#save').on('click', function() {
    var formData = app.form.convertToData('#new-item-form');
    addNewItem(formData);
//    $$('.error-field-wrapper').removeClass('error-field-wrapper');
//    var errors = app.methods.validatePurchaseTranstationForm(formData);
//    if (errors) {
//      return;
//    }
//    formData.sku = app.methods.extractSku(formData.sku);
//    app.methods.saveUserProduct(formData).then(
//      function() {
//        app.methods.flashMessage('Item added').then(function() {
//          page.router.back();
//        });
//      },
//      function(xhr) {
//        app.methods.displayFormErrors(xhr.responseText);
//      }
//    );
  });
  $$("input[name='sku']").on('keyup keydown change',function() {
    var productFullName = $$("input[name='sku']").val();
    if (productFullName !== '') {
      $$('#save').removeClass('disabled');
    } else {
      $$('#save').addClass('disabled');
    }
  });
  $$("input[name='sku']").on('change',function() {
    var productFullName = $$("input[name='sku']").val();
    if (productFullName !== '' && productFullName.indexOf('(') >= 0 && productFullName.indexOf(')') >= 0) {
      var sku = app.methods.extractSku(productFullName);
      app.methods.getProductInfo(sku).then(function(productInfo) {
        $$('#stock-photo').css('background-image','url(' + productInfo.imageReference + ')');
        $$('#stock-photo').css('background-size','contain');
      });
    }
  });  
});



$$(document).on('page:afterin', '.page[data-name="new-item"]', function () {
  imageDB.getFiles().then(function (records) {
    log(records);
    var wrapper = document.getElementById('swiper-wrapper');
    wrapper.innerHTML = "";
    if (records) {
      records.reverse();
      records.forEach(function(record) {
          var div = $$('<div>');
          div.addClass('swiper-slide');
          div.css('background-image','url(' + record.file + ')');
          wrapper.appendChild(div[0]);
        }
      );
      $$('#stock-photo').css('background-image','none');
    }
    var swiper = app.swiper.create('.swiper-container', {
        speed: 900,
        spaceBetween: 50,
        pagination: {"el": ".swiper-pagination"}
    });    
    if (records && records.length > 0) {
      $$('#zoom-in-icon').css('display','inherit');
      $$('#my-photo-wrapper').on('click', function() {
        app.methods.browseImages();
      });
    }
  });
});


