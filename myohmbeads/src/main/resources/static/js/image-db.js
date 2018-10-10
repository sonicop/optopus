var imageDB = {
  name: "imageDB",
  version: 1.0,
  storeName: "imageStore",
  indexedDB: (window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.OIndexedDB || window.msIndexedDB,
  IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.OIDBTransaction || window.msIDBTransaction),
  
  saveFile: function (record) {
    let request = indexedDB.open(imageDB.name, imageDB.version);
    request.onupgradeneeded = function() {
      let db = request.result;
      db.createObjectStore(imageDB.storeName, {keyPath:"id", autoIncrement:true});
    };
    request.onsuccess = function() {
      let db = request.result;
      let tx = db.transaction(imageDB.storeName, "readwrite");
      let objectStore = tx.objectStore(imageDB.storeName);
      objectStore.add(record);
      tx.complete;
      db.close();
    };
  },
  deleteFile: function (key) {
    let request = indexedDB.open(imageDB.name, imageDB.version);
    request.onupgradeneeded = function() {
      let db = request.result;
      db.createObjectStore(imageDB.storeName, {keyPath:"id", autoIncrement:true});
    };
    request.onsuccess = function() {
      let db = request.result;
      let tx = db.transaction(imageDB.storeName, "readwrite");
      let objectStore = tx.objectStore(imageDB.storeName);
      objectStore.delete(key);
      tx.complete;
      db.close();
    };
  },
  getFiles: function() {
    return new Promise(function(resolve, reject){
      let request = indexedDB.open(imageDB.name, imageDB.version);
      request.onupgradeneeded = function() {
        let db = request.result;
        db.createObjectStore(imageDB.storeName, {keyPath:"id", autoIncrement:true});
      };
      request.onsuccess = function() {
        let db = request.result;
        let tx = db.transaction(imageDB.storeName, "readonly");
        let objectStore = tx.objectStore(imageDB.storeName);
        let files;
        if ('getAll' in objectStore) {
          // IDBObjectStore.getAll() will return the full set of items in our store.
          objectStore.getAll().onsuccess = function(event) {
            files = event.target.result;
            tx.complete;
            db.close();
            return resolve(files);
          };
        } else {
          // Fallback to the traditional cursor approach if getAll isn't supported.
          files = [];
          objectStore.openCursor().onsuccess = function(event) {
            var cursor = event.target.result;
            if (cursor) {
              files.push(cursor.value);
              cursor.continue();
            }
            tx.complete;
            db.close();
            return resolve(files);
          };
        }      
      };
    });
  }
};

