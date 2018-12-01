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
        $$('.my-swipeout-delete').on('click', function () {
          var swipeEl = $$(this.parentElement.parentElement);
          app.methods.deleteUserProduct(swipeEl.attr('data-id')).then(function() {
            app.swipeout.delete(swipeEl);
          });
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
          var id = $$(this).parents('.deleted-callback').attr('data-id');
          app.data.selectedProductId = id;
        });        
        app.preloader.hide();
      });
    },
    saveUserProduct: function(record) {
      var progress = 0;
      var dialog = app.dialog.progress('UPLOADING ...', progress);      
      var promise = new Promise(function(resolve, reject) {
        app.methods.uploadImages(record.sku, dialog).then(function(imageDataRows) {
          record.userImages = imageDataRows;
          dialog.setText('Transaction data.');
          app.request.postJSON(
            host + 'purchaseTransactions',
            record,
            function (data) {
              dialog.close();
              resolve(data);
            },
            function(xhr) {
              dialog.close();
              reject(xhr);
            }
          );
        });
      });
      return promise;
    },
    updateUserProduct: function(record, transactionId) {
      var progress = 0;
      var dialog = app.dialog.progress('Updating...', progress);      
      var promise = new Promise(function(resolve, reject) {
        app.request({
          method: "PUT",
          url: host + 'purchaseTransactions/' + transactionId,
          data: JSON.stringify(record),
          contentType: "application/json",
          success: function (data) {
            dialog.close();
            resolve(data);
          },
          error: function(xhr) {
            dialog.close();
            reject(xhr);
          }
        });
      });
      return promise;
    },
    deleteUserProduct: function(transactionId) {
      var promise = new Promise(function(resolve, reject) {
        app.dialog.confirm('Are you sure?', function () {
          var dialog = app.dialog.create({
            title: 'DELETING ...',
            cssClass: 'flash-message'
          });
          dialog.open();      
          app.request({
            method: "DELETE",
            url: host + 'purchaseTransactions/' + transactionId,
            success: function (data) {
              dialog.close();
              app.methods.flashMessage('Item removed').then(function() {
                resolve(data);
              });
            },
            error: function(xhr, status) {
              dialog.close();
              reject(xhr);
            }
          });
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
        var responseObj = JSON.parse(responseText);
        var errors = responseObj.fieldErrors;
        if (errors) {
          errors.forEach(function(error) {
            var fieldName = error.field;
            $$('input[name="' + fieldName + '"]').parent('.item-input-wrap').addClass('error-field-wrapper');
            messages = messages + error.message + '<br/>';
          });
        } else {
          // TODO: Error handling
          messages = responseObj.error + ' (' + responseObj.status + ')';
        }
      } else {
        messages = responseText;
      }
      app.dialog.create({
        title: 'WARNING',
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
      imageDB.getFiles().then(function (records) {
        log(records);
        var images = [];
        records.reverse();
        records.forEach(function(record) {
            images.push({url: record.file, caption: record.caption});
          }
        );
        var imageBrowser = app.photoBrowser.create({
          photos: images,
          type: 'standalone'
        });
        imageBrowser.open();
      });
    },
    uploadImages: function(sku, dialog) {
      var promise = new Promise(function(resolve, reject) {
        app.request({
          url: host + 'awsParameters',
          method: 'GET',
          dataType: 'json',
          success: function (awsParams) {
            AWS.config.credentials = new AWS.Credentials(awsParams.accessKeyId, awsParams.secretAccessKey, awsParams.sessionToken);
            var s3 = new AWS.S3();
            imageDB.getFiles().then(function (records) {
              var recordCount = records.length;
              var progressStep = 100 / recordCount;
              var currentProgress = 0;
              var currentCount = 0;
              var imageDataRows = [];
              var requestArray = [];
              records.forEach(function(record, index) {
                // sku / username / datetime / index
                var objectKey = encodeURIComponent(sku) + '/' +
                                encodeURIComponent(awsParams.username) + '/' +
                                awsParams.creationTime + '/' +
                                index;
                var blob = app.methods.b64toBlob(record.file, record.type);
                var s3Params = {
                  Bucket: awsParams.s3bucket, 
                  Key: objectKey,
                  Body: blob, 
                  ContentEncoding: 'base64',
                  ContentType: record.type,
                  ContentLength: blob.size
                };
                requestArray[index] = s3.putObject(s3Params).promise();
                requestArray[index].then(function(data) {
                  console.log(data);
                  var make = null;
                  var model = null;
                  if (record.exifData) {
                    make = record.exifData.Make;
                    model = record.exifData.Model;
                  }
                  imageDataRows[index] = {
                    sortNumber: index,
                    reference: objectKey,
                    caption: record.caption,
                    originalFileName: record.name,
                    takenByMake: make,
                    takenByModel: model
                  };
                  imageDB.deleteFile(record.id);
                }).catch(function(err) {
                  log(err);
                  console.log(err, err.stack); // an error occurred
                }).then(function() {
                  currentCount += 1;
                  currentProgress += progressStep;
                  dialog.setProgress(currentProgress);
                  dialog.setText('Image ' + currentCount + ' of ' + recordCount);              
                });
              });
              Promise.all(requestArray).then(function() {
                log(imageDataRows);
                resolve(imageDataRows);
              });
            });
            log('upload');
          }
        });
      });
      return promise;
    },
    extractSku: function(productFullName) {
      var startPos = productFullName.lastIndexOf("(") + 1;
      var endPos = productFullName.lastIndexOf(")");
      var sku;
      if (startPos >= 0 && endPos >= 0) {
        sku = productFullName.substring(startPos, endPos);
      } else {
        sku = '';
      }
      return sku;
    },
    b64toBlob: function(dataURI, contentType) {
      var byteString = atob(dataURI.split(',')[1]);
      var ab = new ArrayBuffer(byteString.length);
      var ia = new Uint8Array(ab);

      for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ab], {type: contentType});
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
    },
    flashMessage: function(msg) {
      var promise = new Promise(function(resolve, reject) { 
        var dialog = app.dialog.create({
          title: msg,
          cssClass: 'flash-message'
        });
        dialog.open();
        setTimeout(function () {
          dialog.close();
          resolve();
        }, 1500);
      });
      return promise;
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



$$('#resetDB').on('click', function() {
  app.dialog.confirm('Are you sure?', function () {
    imageDB.resetDatabase();
  });
});



function log(txt) {
  var now = new Date();
  var nowStr = pad(now.getHours(), 2) + ":" + pad(now.getMinutes(),2) + ":" + pad(now.getSeconds(),2);
  $$('#logger').prepend(nowStr + ' - ' + txt + '<br/>');
  console.log(txt);
}



function pad(num, size) {
    var s = num+"";
    while (s.length < size) s = "0" + s;
    return s;
}