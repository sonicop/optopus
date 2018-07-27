// Dom7
var $$ = Dom7;
var host = "";

// Framework7 App main instance
var app  = new Framework7({
  root: '#app', // App root element
  id: 'io.framework7.testapp', // App bundle ID
  name: 'My OHM Beads', // App name
  theme: 'auto', // Automatic theme detection
  // App root data
  data: function () {
    return {
      user: {
        firstName: 'John',
        lastName: 'Doe',
      },
    };
  },
  // App root methods
  methods: {
    helloWorld: function () {
      app.dialog.alert('Hello World!');
    },
    geUserProducts: function(userId) {
      var url = host + 'userProducts/search/findAllByUserUserId?userId=' + userId;
      app.request.get(url, function (data) {
        if (data) {
          var userProductArray = JSON.parse(data)._embedded.userProducts;
          for (var i = 0; i < userProductArray.length; i++) {
            var template = document.getElementById("product-template");
            var templateHtml = template.innerHTML;
            var html = templateHtml.replace(/{{sku}}/g, userProductArray[i].product.sku)
                                   .replace(/{{name}}/g, userProductArray[i].product.name)
                                   .replace(/{{quantity}}/g, userProductArray[i].quantity)
                                   .replace(/{{comment}}/g, userProductArray[i].comment)
                                   .replace(/{{category}}/g, userProductArray[i].product.categoryList[0].name)
                                   .replace(/{{reference}}/g, userProductArray[i].product.imageList[0].reference);
            var li = document.createElement('li');
            li.innerHTML = html;
            document.getElementById('product-list').appendChild(li);
          }
        }
      });
    },
    saveUserProduct: function(record) {
      var key = {"userId": app.data.user.userId, "sku": record.sku};
      record['userProductPK'] = key;
      record['createdBy'] = app.data.user.userId;
      app.request.postJSON(host + 'userProducts', record, function (data) {
        console.log(data);
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
      if (!app.data.token) {
        app.loginScreen.open('.login-screen');
      }
    },
    pageBeforeIn: function (page) {
      if (page.name === 'home') {
      }
    }
  }
});

// Login Screen Demo
$$('#my-login-screen .login-button').on('click', function () {
  var username = $$('#my-login-screen [name="username"]').val();
  var password = $$('#my-login-screen [name="password"]').val();

  // Close login screen
  app.loginScreen.close('#my-login-screen');
  app.data['token'] = 'abc';
  app.request.get(host + 'users/1', function (data) {
    app.data['user'] = JSON.parse(data);
    app.methods.geUserProducts(app.data.user.userId);
  });    
});



$$(document).on('page:reinit', function (e) {
  console.log('home: ' + this);
});



$$(document).on('page:beforein', '.page[data-name="home"]', function (e) {
  app.methods.geUserProducts(app.data.user.userId);
});



$$(document).on('page:init', '.page[data-name="new-item"]', function (e) {
  $$('.page[data-name="new-item"] a#save').on('click', function() {
    var formData = app.form.convertToData('#new-item-form');
    console.log(JSON.stringify(formData));
    app.methods.saveUserProduct(formData);
  });
});
