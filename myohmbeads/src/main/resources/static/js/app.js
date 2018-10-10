// Dom7
var $$ = Dom7;
var host = "";
if (window.location.hostname === "localhost" && window.location.port === "8383") {
  host = "http://localhost:8080/";
}

// Framework7 App main instance
var app  = new Framework7({
  root: '#app', // App root element
  id: 'io.framework7.testapp', // App bundle ID
  name: 'My OHM Beads', // App name
  theme: 'auto', // Automatic theme detection
  // App root data
  data: {} 
  ,
  // App root methods
  methods: {
    getUserProducts: function() {
      app.preloader.show();
      var url = host + 'purchaseTransactions'
      app.request.get(url, function (data) {
        if (data) {
          var productListUl = document.getElementById('product-list');
          productListUl.innerHTML = "";
          var transactionArray = JSON.parse(data);
          for (var i = 0; i < transactionArray.length; i++) {
            var template = document.getElementById("product-template");
            var templateHtml = template.innerHTML;
            var html = templateHtml.replace(/{{transactionId}}/g, transactionArray[i].transactionId)
                                   .replace(/{{sku}}/g, transactionArray[i].sku)
                                   .replace(/{{name}}/g, transactionArray[i].productName)
                                   .replace(/{{brandName}}/g, transactionArray[i].brandName)
                                   .replace(/{{createTime}}/g, transactionArray[i].createTime.substring(0,10))
                                   .replace(/{{price}}/g, transactionArray[i].purchasePrice)
                                   .replace(/{{currency}}/g, transactionArray[i].currencyCode)
                                   .replace(/{{note}}/g, transactionArray[i].note)
                                   .replace(/{{reference}}/g, transactionArray[i].imageReference);
            var div = document.createElement('div');
            div.innerHTML = html;
            productListUl.appendChild(div.firstElementChild);
          }
        }
        $$('.deleted-callback').on('swipeout:deleted', function () {
          app.methods.deleteUserProduct($$(this).attr('data-id'));
        });
        var moreActions = app.actions.create({
          buttons: [
            [
              {
                text: 'Select a command to apply to your product.',
                label: true,
                color: 'white',
                bg: 'black'
              },
              {
                text: 'Comment',
                color: 'white',
                bg: 'blue',
                onClick: function(){
                  mainView.router.navigate('/comment/' + app.data.selectedProductId);
                }                
              },
              {
                text: 'Edit',
                color: 'white',
                bg: 'orange',
                onClick: function(){
                  mainView.router.navigate('/edit-item/' + app.data.selectedProductId);
                }
              }
            ],
            [
              {
                text: 'Cancel',
                bold: true,
                color: 'red'
              }
            ]
          ],
        });
        $$('.open-more-actions').on('click', function () {
          moreActions.open();
          //var id = $$(this).parent('.deleted-callback').attr('data-id');
          var id = $$(this).parents('.deleted-callback').attr('data-id');
          app.data.selectedProductId = id;
        });        
        app.preloader.hide();
      });
    },
    saveUserProduct: function(record) {
      var promise = new Promise(function(resolve, reject) {
        app.request.postJSON(
          host + 'purchaseTransactions',
          record,
          function (data) {
            resolve(data);
          },
          function(xhr) {
            reject(xhr);
          }
        );
      });
      return promise;
    },
    updateUserProduct: function(record, transactionId) {
      var promise = new Promise(function(resolve, reject) {
        app.request({
          method: "PUT",
          url: host + 'purchaseTransactions/' + transactionId,
          data: JSON.stringify(record),
          contentType: "application/json",
          success: function (data) {
            resolve(data);
          },
          error: function(xhr) {
            reject(xhr);
          }
        });
      });
      return promise;
    },
    deleteUserProduct: function(transactionId) {
      var promise = new Promise(function(resolve, reject) {
        app.request({
          method: "DELETE",
          url: host + 'purchaseTransactions/' + transactionId,
          success: function (data) {
            resolve(data);
          },
          error: function(xhr, status) {
            reject(xhr);
          }
        });
      });
      return promise;
    },
    getUserProduct: function(transactionId) {
      var promise = new Promise(function(resolve, reject) {
        var url = host + 'purchaseTransactions/' + transactionId;
        app.request.get(url, function (data) {
          if (data) {
            var transaction = JSON.parse(data);
            resolve(transaction);
          }
        });
      });
      return promise;
    },
    validatePurchaseTranstationForm: function(formData) {
      var errors = [];
      if (typeof formData.sku === "undefined" || formData.sku.length === 0) {
        errors.push({field: "sku", message: "Product SKU is required."});
      } else if (formData.sku.length > 48) {
        errors.push({field: "sku", message: "Product SKU is invalid."});
      }
      
      if (typeof formData.serialNumber !== "undefined" && formData.serialNumber.length > 48) {
        errors.push({field: "serialNumber", message: "Serial number is invalid."});
      }
      
      if (typeof formData.currencyCode !== "undefined" && formData.currencyCode.length > 3) {
        errors.push({field: "currencyCode", message: "Currency code is invalid."});
      }
      
      if (typeof formData.purchasePrice !== "undefined" && !(/^(\s*|\d*\.?\d+)$/.test(formData.purchasePrice))) {
        errors.push({field: "purchasePrice", message: "Purchase price is invalid format."});
      }
      
      if (typeof formData.purchasePrice !== "undefined" && formData.purchasePrice.length > 13) {
        errors.push({field: "purchasePrice", message: "Purchase price exceed maximum length."});
      }
  
      if (typeof formData.purchaseFrom !== "undefined" && formData.purchaseFrom.length > 100) {
        errors.push({field: "purchaseFrom", message: "Purchase from is invalid."});
      }

      if (typeof formData.note !== "undefined" && formData.note.length > 500) {
        errors.push({field: "note", message: "Note is invalid."});
      }
      if (errors.length > 0) {
        app.methods.displayFormErrors(JSON.stringify({fieldErrors: errors}));
        return errors;
      } else {
        return null;
      }
    },
    displayFormErrors: function(responseText) {
      var messages;
      if (responseText.startsWith("{") && responseText.endsWith("}")) {
        messages = 'Invalid input(s):<br/>';
        var errors = JSON.parse(responseText).fieldErrors;
        errors.forEach(function(error) {
          var fieldName = error.field;
          $$('input[name="' + fieldName + '"]').parent('.item-input-wrap').addClass('error-field-wrapper');
          messages = messages + error.message + '<br/>';
        });
      } else {
        messages = responseText;
      }
      app.dialog.create({
        title: 'Warning!',
        text: messages,
        buttons: [{text:'Retry'}]
      }).open();      
    },
    prepareDropDowns: function() {
      app.autocomplete.create({
        inputEl: '#product-autocomplete', //link that opens autocomplete
        openIn: 'dropdown', //open in page
        valueProperty: 'valueProperty', //object's "value" property name
        textProperty: 'textProperty', //object's "text" property name
        typeahead: false,
        limit: 10,
        preloader: true, //enable preloader
        source: function (query, render) {
          var autocomplete = this;
          var results = [];
          if (query.length === 0) {
            render(results);
            return;
          }
          // Show Preloader
          autocomplete.preloaderShow();
          // Do Ajax request to Autocomplete data
          app.request({
            url: host + 'products/search/getByKeywords',
            method: 'GET',
            dataType: 'json',
            //send "query" to server. Useful in case you generate response dynamically
            data: {
              keywords: query
            },
            success: function (data) {
              // Hide Preoloader
              autocomplete.preloaderHide();
              // Render items by passing array with result items
              render(data);
            }
          });
        },
      });  
      app.autocomplete.create({
        inputEl: '#currency-autocomplete', //link that opens autocomplete
        openIn: 'dropdown', //open in page
        valueProperty: 'currencyCode', //object's "value" property name
        textProperty: 'dropDownText', //object's "text" property name
        typeahead: true,
        preloader: true, //enable preloader
        source: function (query, render) {
          var autocomplete = this;
          var results = [];
          if (query.length === 0) {
            render(results);
            return;
          }
          // Show Preloader
          autocomplete.preloaderShow();
          // Do Ajax request to Autocomplete data
          app.request({
            url: host + 'currencies/search/findByKeyword',
            method: 'GET',
            dataType: 'json',
            //send "query" to server. Useful in case you generate response dynamically
            data: {
              keyword: query
            },
            success: function (data) {
              data = data._embedded.currencies;
              autocomplete.preloaderHide();
              render(data);
            }
          });
        },
      });
      app.autocomplete.create({
        inputEl: '#seller-autocomplete', //link that opens autocomplete
        openIn: 'dropdown', //open in page
        valueProperty: 'seller', //object's "value" property name
        textProperty: 'seller', //object's "text" property name
        typeahead: true,
        preloader: true, //enable preloader
        source: function (query, render) {
          var autocomplete = this;
          var results = [];
          if (query.length === 0) {
            render(results);
            return;
          }
          // Show Preloader
          autocomplete.preloaderShow();
          // Do Ajax request to Autocomplete data
          app.request({
            url: host + 'sellers',
            method: 'GET',
            dataType: 'json',
            //send "query" to server. Useful in case you generate response dynamically
            data: {
              query: query
            },
            success: function (data) {
              data = data._embedded.sellers;
              // Find matched items
              for (var i = 0; i < data.length; i++) {
                if (data[i].seller.toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(data[i]);
              }
              // Hide Preoloader
              autocomplete.preloaderHide();
              // Render items by passing array with result items
              render(results);
            }
          });
        },
      });
    },
    getProductInfo: function(sku) {
      var promise = new Promise(function(resolve, reject) {
        app.request({
          url: host + 'productInfo/' + sku,
          method: 'GET',
          dataType: 'json',
          success: function (data) {
            resolve(data);
          },
          error: function(xhr, status) {
            reject(xhr);
          }
        });
      });
      return promise;
    },
    browseImages: function() {
      imageDB.getFiles().then(function (files) {
        console.log(files);
        var images = [];
        files.forEach(function(file) {
            images.push({url: URL.createObjectURL(file), caption: file.name});
          }
        );
        var imageBrowser = app.photoBrowser.create({
          photos: images,
          type: 'standalone'
        });
        imageBrowser.open();
      });
    },
    extractSku: function(productFullName) {
      var startPos = productFullName.lastIndexOf("(") + 1;
      var endPos = productFullName.lastIndexOf(")");
      var sku = productFullName.substring(startPos, endPos);
      return sku;
    },
    equalsObjects: function(object1, object2) {
      if (typeof object1 === "undefined" || object1 === null ||
          typeof object2 === "undefined" || object2 === null) {
        return false;   
      }
      for (var property in object1) {
        if (object1.hasOwnProperty(property)) {
          if (object1[property] !== object2[property]) {
            return false;
          }
        }
      }
      return true;
    }
  },
  // App routes
  routes: routes,
});

// Init/Create main view
var mainView = app.views.create('.view-main', {
  url: '/',
  on: {
    pageInit: function (page) {
      if (page.name === 'home') {
        app.methods.getUserProducts();
      }
    },
    pageBeforeIn: function (page) {
      if (page.name === 'home') {
      }
    }
  }
});



$$(document).on('page:afterin', '.page[data-name="home"]', function (e) {
  app.methods.getUserProducts();
});



$$(document).on('page:init', '.page[data-name="new-item"]', function (e, page) {
  app.methods.prepareDropDowns();
  app.data.initialFormData = app.form.convertToData('#new-item-form');
  $$('.page[data-name="new-item"] a#done').on('click', function() {
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
          text:'Dismiss',
          onClick: function() {
             page.router.back();
             return;
           }},{
          text:'Save',
          onClick: function() {
            var errors = app.methods.validatePurchaseTranstationForm(formData);
            if (errors) {
              return;
            }
            formData.sku = app.methods.extractSku(formData.sku);
            app.methods.saveUserProduct(formData).then(
              function() {
                app.dialog.alert('Added successfully!', 'Infomation', function() {
                  page.router.back();
                });      
              },
              function(xhr) {
                app.methods.displayFormErrors(xhr.responseText);
              }
            );
          }
        }]
      }).open();
    }
  });
  $$('.page[data-name="new-item"] a#save').on('click', function() {
    $$('.error-field-wrapper').removeClass('error-field-wrapper');
    var formData = app.form.convertToData('#new-item-form');
    var errors = app.methods.validatePurchaseTranstationForm(formData);
    if (errors) {
      return;
    }
    app.methods.saveUserProduct(formData).then(
      function() {
        app.dialog.alert('Added successfully!', 'Information', function() {
          $$('#new-item-form')[0].reset();
        });      
      },
      function(xhr) {
        app.methods.displayFormErrors(xhr.responseText);
      }
    );
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
        $$('#photo-label').css('background-image','url(' + productInfo.imageReference + ')');
        $$('#photo-label').css('background-size','contain');
      });
    }
  });  
  $$("#photo-file").on('change', function (e, page) {
    console.log(e);
  });
});



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

