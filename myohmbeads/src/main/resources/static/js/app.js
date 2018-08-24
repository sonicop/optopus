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
    getBrands: function () {
      var url = host + 'brands';
      app.request.get(url, function (data) {
        if (data) {
          var selectedBrandIdDropdown = $$('#selectedBrandId');
          selectedBrandIdDropdown.empty();
          var brandArray = JSON.parse(data)._embedded.brands;
          for (var i = 0; i < brandArray.length; i++) {
            selectedBrandIdDropdown.append('<option value="' + brandArray[i].brandId + '">' + brandArray[i].name + '</option>');
          }
        }
      });
    },
    getUserProducts: function(userId) {
      var url = host + 'purchaseTransactions?userId=' + userId
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
                                   .replace(/{{createTime}}/g, transactionArray[i].createTime.substring(0,10))
                                   .replace(/{{note}}/g, transactionArray[i].note)
                                   .replace(/{{reference}}/g, transactionArray[i].imageReference);
            var li = document.createElement('li');
            li.innerHTML = html;
            productListUl.appendChild(li);
          }
        }
      });
    },
    saveUserProduct: function(record) {
      var promise = new Promise(function(resolve, reject) {
        record['userId'] = app.data.user.userId;
        console.log(record);
        app.request.postJSON(
          host + 'purchaseTransactions',
          record,
          function (data) {
            resolve(data);
          },
          function(xhr, status) {
            reject(xhr);
          }
        );
      });
      return promise;
    },
    updateUserProduct: function(record, transactionId) {
      var promise = new Promise(function(resolve, reject) {
        record['userId'] = app.data.user.userId;
        console.log(record);
        app.request({
          method: "PUT",
          url: host + 'purchaseTransactions/' + transactionId,
          data: JSON.stringify(record),
          contentType: "application/json",
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
            console.log(transaction);
            resolve(transaction);
          }
        });
      });
      return promise;
    },
    prepareDropDowns: function() {
      app.methods.getBrands();
      app.autocomplete.create({
        inputEl: '#product-autocomplete', //link that opens autocomplete
        openIn: 'dropdown', //open in page
        valueProperty: 'sku', //object's "value" property name
        textProperty: 'dropDownText', //object's "text" property name
        typeahead: true,
        limit: 50,
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
            url: host + 'brands/' + $$('#selectedBrandId').val() + '/productList',
            method: 'GET',
            dataType: 'json',
            //send "query" to server. Useful in case you generate response dynamically
            data: {
              query: query
            },
            success: function (data) {
              data = data._embedded.products;
              // Find matched items
              for (var i = 0; i < data.length; i++) {
                displayText = data[i].sku + ': ' + data[i].name;
                if (displayText.toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(data[i]);
              }
              // Hide Preoloader
              autocomplete.preloaderHide();
              // Render items by passing array with result items
              render(results);
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
            url: host + 'currencies',
            method: 'GET',
            dataType: 'json',
            //send "query" to server. Useful in case you generate response dynamically
            data: {
              query: query
            },
            success: function (data) {
              data = data._embedded.currencies;
              // Find matched items
              for (var i = 0; i < data.length; i++) {
                displayText = data[i].dropDownText;
                if (displayText.toLowerCase().indexOf(query.toLowerCase()) >= 0) results.push(data[i]);
              }
              // Hide Preoloader
              autocomplete.preloaderHide();
              // Render items by passing array with result items
              render(results);
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
      if (typeof app.data.user === 'undefined') {
        app.request.get(host + 'users/6c7acae9-9941-11e8-ab1c-080027d981a5', function (data) {
          app.data['user'] = JSON.parse(data);
          app.methods.getUserProducts(app.data.user.userId);
        }); 
      }
    },
    pageBeforeIn: function (page) {
      if (page.name === 'home') {
      }
    }
  }
});



$$(document).on('page:afterin', '.page[data-name="home"]', function (e) {
  app.methods.getUserProducts(app.data.user.userId);
});



$$(document).on('page:init', '.page[data-name="new-item"]', function (e, page) {
  app.methods.prepareDropDowns();
  $$('.page[data-name="new-item"] a#save').on('click', function() {
    var formData = app.form.convertToData('#new-item-form');
    console.log(JSON.stringify(formData));
    app.methods.saveUserProduct(formData).then(function() {
      app.dialog.alert('Added successfully!', app.name, function() {
        page.router.back();
      });      
    });
  });
  $$('.page[data-name="new-item"] a#add-more').on('click', function() {
    var formData = app.form.convertToData('#new-item-form');
    console.log(JSON.stringify(formData));
    app.methods.saveUserProduct(formData).then(function() {
      app.dialog.alert('Added successfully!', app.name, function() {
        $$('#new-item-form')[0].reset();
      });      
    });
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
    $$("input[name='currencyCode']").val(transaction.currencyCode);
    $$("input[name='purchasePrice']").val(transaction.purchasePrice);
    $$("input[name='purchaseFrom']").val(transaction.purchaseFrom);
    $$("input[name='purchaseDate']").val(transaction.purchaseDate);
    $$("textarea[name='note']").val(transaction.note);
  });
  
  $$('.page[data-name="edit-item"] a#update').on('click', function() {
    var formData = app.form.convertToData('#edit-item-form');
    app.dialog.confirm('Are you sure?', function () {
      console.log(JSON.stringify(formData));
      app.methods.updateUserProduct(formData, transactionId).then(
        function(data) {
          app.dialog.alert('Updated successfully!', app.name, function() {
            page.router.back();
          });
        },
        function(xhr) {
          app.dialog.alert('Failed to update!');
          console.error(xhr);
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
          app.dialog.alert('Failed to update!');
          console.error(xhr);
        }
      );
    });
  });
});

