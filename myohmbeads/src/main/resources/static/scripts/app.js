// Dom7
var $$ = Dom7;
var host = "";

// Framework7 App main instance
var app  = new Framework7({
  root: '#app', // App root element
  id: 'io.framework7.testapp', // App bundle ID
  name: 'My OHM Beads', // App name
  theme: 'auto', // Automatic theme detection
  // App routes
  routes: routes,
});
