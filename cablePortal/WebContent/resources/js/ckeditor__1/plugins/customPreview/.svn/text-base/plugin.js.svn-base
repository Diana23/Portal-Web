/*
Copyright (c) 2003-2009, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/**
 * @file Preview plugin.
 */

(function()
{
	var previewCmd2 =
	{
		modes : { wysiwyg:1, source:1 },
		canUndo : false,
		exec : function( editor )
		{
			var sHTML;
			sHTML = editor.getData();
			

			var iWidth	= 640,	// 800 * 0.8,
				iHeight	= 420,	// 600 * 0.7,
				iLeft	= 80;	// (800 - 0.8 * 800) /2 = 800 * 0.1.
			try
			{
				var screen = window.screen;
				iWidth = Math.round( screen.width * 0.8 );
				iHeight = Math.round( screen.height * 0.7 );
				iLeft = Math.round( screen.width * 0.1 );
			}
			catch ( e ){}

			var sOpenUrl = '';
			var customUrl = false;
			if(editor.config.previewURL.length>0){
				sOpenUrl = editor.config.previewURL;
				customUrl = true;
			}
			else if ( isCustomDomain )
			{
				window._cke_htmlToLoad = sHTML;
				sOpenUrl = 'javascript:void( (function(){' +
					'document.open();' +
					'document.domain="' + document.domain + '";' +
					'document.write( window.opener._cke_htmlToLoad );' +
					'document.close();' +
					'window.opener._cke_htmlToLoad = null;' +
					'})() )';
			}

			
			if(!customUrl){
				var oWindow = window.open( sOpenUrl, null, 'toolbar=yes,location=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=' +
					iWidth + ',height=' + iHeight + ',left=' + iLeft );
	
				if ( !isCustomDomain && !customUrl)
				{
					oWindow.document.write( sHTML );
					oWindow.document.close();
				}
			}else{
				//get the form to submit the data (a custom one, not the real)
				var theForm = document.getElementById('serverPreviewForm') ;
				if (!theForm) {
					//it doesn't exist still, we create it here
					theForm = document.createElement('FORM') ;
					theForm.method = 'POST' ;
					theForm.name = 'serverPreviewForm' ;
					theForm.id=theForm.name ;
					theForm.style.display = 'none' ;
			
					
					//URL form
					theForm.action = editor.config.previewURL;
					
			
					//new window please
					theForm.target='_blank';
					document.body.appendChild( theForm );
				}
			
				//clear previous data
				theForm.innerHTML = '' ;
				//set the new content
				var input = document.createElement('INPUT') ;
				input.type = 'hidden';
				//change the name as needed -->
				input.name = 'htmlData' ;
				//set the data
				input.value = sHTML;
				//append the new input to the form
				theForm.appendChild( input );
			
				//that's all, append additional fields as needed, or set the variables in the previewPath
			
				//send the data to the server
				theForm.submit();
			}
		}
	};

	var pluginName = 'customPreview';

	// Register a plugin named "preview".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			editor.addCommand( pluginName, previewCmd2 );
			editor.ui.addButton( 'customPreview',
				{
					label : editor.lang.preview,
					icon: this.path + 'etreat_preview_icon.png',
					command : pluginName
				});
		},
		beforeInit : function( editor )
      {
      }
	});
})();

CKEDITOR.config.previewURL = "";  