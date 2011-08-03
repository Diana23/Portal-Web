// bea.wlp.disc.xie._impl[Rewrite]
/*
 * B E A   S Y S T E M S
 *
 * Copyright (c) 2000-2008  BEA Systems, Inc.
 *
 * All Rights Reserved. Unpublished rights reserved under the copyright laws of the United States. The software
 * contained on this media is proprietary to and embodies the confidential technology of BEA Systems, Inc. The
 * possession or receipt of this information does not convey any right to disclose its contents, reproduce it, or use,
 * or license the use, for manufacture or sale, the information or anything described therein. Any use, disclosure, or
 * reproduction without BEA System's prior written permission is strictly prohibited.
 *
 * Any entity defined in this scope that is named with a leading underscore ('_') is reserved for internal use only.
 */

bea.wlp.disc.Module.contribute({
    require: ["bea.wlp.disc._util"],
    declare: function($, L) {
        var $Util = bea.wlp.disc._util;
        $.Rewrite = {
            all: function(dom) {
                var root = (dom ? dom : document);
                this._links(root);
                this._forms(root);
            },
            _submitButtonHandler: function(event) {
                event = $Util.Event.getEvent(event);
                var button = (event.srcElement) ? event.srcElement : ((event.currentTarget) ? event.currentTarget : this);
                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", button.getAttribute("name"));
                input.setAttribute("value", button.getAttribute("value"));
                button.form.appendChild(input);
                if (button.type == "image") {
                    var posX = (event.offsetX !== undefined)
                             ? event.offsetX
                             : event.layerX - button.offsetLeft - ((button.scrollWidth - button.clientWidth) / 2);
                    input = document.createElement("input");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("name", button.getAttribute("name") + ".x");
                    input.setAttribute("value", posX);
                    button.form.appendChild(input);
                    var posY = (event.offsetY !== undefined)
                             ? event.offsetY
                             : event.layerY - button.offsetTop - ((button.scrollHeight - button.clientHeight) / 2);
                    input = document.createElement("input");
                    input.setAttribute("type", "hidden");
                    input.setAttribute("name", button.getAttribute("name") + ".y");
                    input.setAttribute("value", posY);
                    button.form.appendChild(input);
                }
            },
            _links: function(root) {
                var list = root.getElementsByTagName("a");
                for (var i = 0; i < list.length; i++) {
                    this._link(list.item(i));
                }
            },
            _forms: function(root) {
                var forms = root.getElementsByTagName("form");
                for (var i = 0; i < forms.length; i++) {
                    this._form(forms[i]);
                }
            },
            _JS_UPDATE_FUNCTION: "javascript:bea.wlp.disc.xie._Service.update",
            _link: function(link) {
                var screen = this._screenUri(link.href);
                if (screen.rewrite) {
                    link.href = this._JS_UPDATE_FUNCTION + "('" + screen.uri + "');";
                }
                else {
                    link.href = screen.uri;
                }
            },
            _form: function(form) {
                if (form.enctype != "multipart/form-data") {
                    if (!form.id || form.id.length == 0) {
                        var result = this._getUniqueFormId();
                        form.id = result.id;
                    }
                    var screen = this._screenUri(form.action);
                    if (screen.rewrite) {
                                        var action = screen.uri;
                        form.action = this._JS_UPDATE_FUNCTION + "('" + screen.uri + "', '" + form.id + "');";
                    }
                    else {
                        form.action = screen.uri;
                    }
                    var submits = [];
                    var inputs = form.getElementsByTagName("input");
                    var j;
                    for (j = 0; j < inputs.length; j++) {
                        submits.push(inputs[j]);
                    }
                    var buttons = form.getElementsByTagName("button");
                    for (j = 0; j < buttons.length; j++) {
                        submits.push(buttons[j]);
                    }
                    for (j = 0; j < submits.length; j++) {
                        var type = submits[j].type;
                        var name = submits[j].name;
                        if ((type == "submit" || type == "image") && name && name.length > 0) {
                            $Util.Event.connect(submits[j], "click", this._submitButtonHandler);
                        }
                    }
                }
            },
            _screenUri: function(uri) {
                var rewrite;
                if (uri) {
                    uri = uri.replace(new RegExp("([?&])_portlet\\.async=(\\w+)(&)?", "i"),
                        function(match, delim1, value, delim2) {
                            rewrite = (value.toLowerCase() == "true");
                            return delim1 + "_nfxr=" + value + (delim2 || "");
                        }
                    );
                    var script = uri.indexOf("javascript:") == 0;
                    var portal = uri.indexOf("_nfpb=true") >= 0 || uri.indexOf("__c=") >= 0;
                    rewrite = (!script && portal && (typeof rewrite == "undefined" || rewrite));
                    
                    if(uri.split("#")[0]===location.href.split("#")[0]){
                    	rewrite = false;
                    }
                    
                    // The _nfxr param is a client processing artifact and should not be relied upon on the server-side
                    if (rewrite && uri.indexOf("_nfxr") < 0) {
                        var split = uri.split("#");
                        split[0] += (split[0].indexOf("?") >= 0 ? "&" : "?") + "_nfxr=true";
                        uri = split[0] + (split[1] && split[1].length > 0 ? "#" + split[1] : "");
                    }
                }
                return {uri: uri, rewrite: rewrite};
            },
            _getFormId: function(counter) {
                return "ajax_form_" + counter;
            },
            _getUniqueFormId: function() {
                var counter = 0;
                var id = this._getFormId(counter);
                while (document.getElementById(id)) {
                    counter++;
                    id = this._getFormId(counter);
                }
                return {id: id, counter: counter};
            }
        }
    }
});
