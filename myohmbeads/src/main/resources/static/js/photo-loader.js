$$(document).on('page:init', '.page[data-name="photo-loader"]', function (e) {
  $$("#photo-file").on('change', function (e, page) {
    if (e.target.files && e.target.files[0].type.match('image.*')) {
      var blobFile = e.target.files[0];
      imageDB.processImage(blobFile).then(function(processedImageUrl) {
        $$('#photo-label').css('background-image', 'url(' + processedImageUrl + ')');
        $$('#photo-label').css('background-size','contain');
        $$('#use').removeClass('disabled');
        $$('#retake').removeClass('disabled');
        $$('#use').on('click', function() {
          var record = {
            file: processedImageUrl,
            caption: $$('#photo-caption').val(),
            type: blobFile.type,
            name: blobFile.name,
            size: blobFile.size,
            lastModified: blobFile.lastModified,
            lastModifiedDate: blobFile.lastModifiedDate,
            exifData: blobFile.exifdata
          };
          imageDB.saveFile(record);
        });
      });
    } else {
      app.methods.displayFormErrors('Invalid image file');
    }
  });
  $$('.page[data-name="photo-loader"] a#retake').on('click', function() {
    $$('#photo-file').click()
  });
});
