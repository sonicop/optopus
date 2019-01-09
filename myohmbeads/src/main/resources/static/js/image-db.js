var imageDB = {
  name: "imageDB",
  version: 1.0,
  storeName: "imageStore",
  indexedDB: (window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.OIndexedDB || window.msIndexedDB,
  IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.OIDBTransaction || window.msIDBTransaction),
  
  resetDatabase: function() {
    indexedDB.deleteDatabase(imageDB.name);
  },
  saveFile: function (record) {
    log("saveFile(): " + record);
    let request = indexedDB.open(imageDB.name, imageDB.version);
    request.onupgradeneeded = function() {
      log("saveFile() - request.onupgradeneeded");
      let db = request.result;
      db.createObjectStore(imageDB.storeName, {keyPath:"id", autoIncrement:true});
    };
    request.onsuccess = function() {
      let db = request.result;
      let tx = db.transaction(imageDB.storeName, "readwrite");
      let objectStore = tx.objectStore(imageDB.storeName);
      let objectStoreRequest = objectStore.add(record);
      objectStoreRequest.onsuccess = function(event) {
        log(event);
        tx.complete;
        db.close();
        log("save file successfully.")
      };
      objectStoreRequest.onerror = function(event) {
        tx.complete;
        db.close();
        log("failed to save. " + objectStoreRequest.error);
      };
    };
  },
  deleteFile: function (key) {
    let request = indexedDB.open(imageDB.name, imageDB.version);
    request.onupgradeneeded = function() {
      log("deleteFile() - request.onupgradeneeded");
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
      log("getFiles()");
      let request = indexedDB.open(imageDB.name, imageDB.version);
      request.onupgradeneeded = function() {
        log("getFiles() - request.onupgradeneeded");
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
            log("Got records from getAll(): records: " + files.length);
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
            log("Got records from non-getAll(): records: " + files.length);
            return resolve(files);
          };
        }      
      };
    });
  },
  processImage: function(imageFile) {
    return new Promise(function(resolve, reject) {
      var reader = new FileReader();
      reader.readAsDataURL(imageFile); 
      reader.onloadend = function() {
        var orgImg = new Image();
        orgImg.onload = function() {
          var canvas = document.createElement("canvas");
          var commonLength = Math.min(orgImg.width, orgImg.height);
          canvas.width = commonLength;
          canvas.height = commonLength;
          var ctx = canvas.getContext("2d");
          EXIF.getData(imageFile, function() {
            log("EXIF: " + EXIF.pretty(this));
            ctx.translate(commonLength/2, commonLength/2);
            var orientation = EXIF.getTag(this, "Orientation");
            switch (orientation) {
              case 1:
                ctx.rotate(0 * Math.PI / 180);
                break;
              case 8:
                ctx.rotate(270 * Math.PI / 180);
                break;
              case 3:
                ctx.rotate(180 * Math.PI / 180);
                break;
              case 6:
                ctx.rotate(90 * Math.PI / 180);
                break;
            }
            ctx.translate(-commonLength/2,-commonLength/2);
            ctx.drawImage(orgImg, (commonLength - orgImg.width)/2, (commonLength - orgImg.height)/2);
            
            return resolve(canvas.toDataURL(imageFile.type));
          }); 
  
        };
        orgImg.src = reader.result;
      };
    });
  },
  rotateImage: function(elementId, fileObj) {
    var element = $$('#' + elementId);
    EXIF.getData(fileObj, function() {
      $$('#test-text').html('EXIF:' + EXIF.pretty(this));
      log("EXIF: " + EXIF.pretty(this));
      var orientation = EXIF.getTag(this, "Orientation")
      switch (orientation) {
        case 1:
          element.css('transform','rotate(0deg)');
          //ctx.rotate(180 * Math.PI / 180);
          break;
        case 8:
          element.css('transform','rotate(270deg)');
          //ctx.rotate(90 * Math.PI / 180);
          break;
        case 3:
          element.css('transform','rotate(180deg)');
          //ctx.rotate(180 * Math.PI / 180);
          break;
        case 6:
          element.css('transform','rotate(90deg)');
          //ctx.rotate(-90 * Math.PI / 180);
          break;
      }        
    });      
  }
};

