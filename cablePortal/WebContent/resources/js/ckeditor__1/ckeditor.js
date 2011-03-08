﻿/*
Copyright (c) 2003-2009, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

(function () {
    if (!window.CKEDITOR) window.CKEDITOR = (function () {
        var a = {
            timestamp: '97KD',
            version: '3.0',
            revision: '4148',
            _: {},
            status: 'unloaded',
            basePath: (function () {
                var d = window.CKEDITOR_BASEPATH || '';
                if (!d) {
                    var e = document.getElementsByTagName('script');
                    for (var f = 0; f < e.length; f++) {
                        var g = e[f].src.match(/(^|.*[\\\/])ckeditor(?:_basic)?(?:_source)?.js(?:\?.*)?$/i);
                        if (g) {
                            d = g[1];
                            break;
                        }
                    }
                }
                if (d.indexOf('://') == -1) if (d.indexOf('/') === 0) d = location.href.match(/^.*?:\/\/[^\/]*/)[0] + d;
                else d = location.href.match(/^[^\?]*\/(?:)/)[0] + d;
                return d;
            })(),
            getUrl: function (d) {
                if (d.indexOf('://') == -1 && d.indexOf('/') !== 0) d = this.basePath + d;
                if (this.timestamp && d.charAt(d.length - 1) != '/') d += (d.indexOf('?') >= 0 ? '&' : '?') + ('t=') + this.timestamp;
                return d;
            }
        },
            b = window.CKEDITOR_GETURL;
        if (b) {
            var c = a.getUrl;
            a.getUrl = function (d) {
                return b.call(a, d) || c.call(a, d);
            };
        }
        return a;
    })();
    var a = CKEDITOR;
    if (!a.event) {
        a.event = function () {};
        a.event.implementOn = function (b, c) {
            var d = a.event.prototype;
            for (var e in d) if (b[e] == undefined) b[e] = d[e];
        };
        a.event.prototype = (function () {
            var b = function (d) {
                var e = d.getPrivate && d.getPrivate() || d._ || (d._ = {});
                return e.events || (e.events = {});
            },
                c = function (d) {
                this.name = d;
                this.listeners = [];
            };
            c.prototype = {
                getListenerIndex: function (d) {
                    for (var e = 0, f = this.listeners; e < f.length; e++) if (f[e].fn == d) return e;
                    return -1;
                }
            };
            return {
                on: function (d, e, f, g, h) {
                    var i = b(this),
                        j = i[d] || (i[d] = new c(d));
                    if (j.getListenerIndex(e) < 0) {
                        var k = j.listeners;
                        if (!f) f = this;
                        if (isNaN(h)) h = 10;
                        var l = this,
                            m = function (o, p, q, r) {
                            var s = {
                                name: d,
                                sender: this,
                                editor: o,
                                data: p,
                                listenerData: g,
                                stop: q,
                                cancel: r,
                                removeListener: function () {
                                    l.removeListener(d, e);
                                }
                            };
                            e.call(f, s);
                            return s.data;
                        };
                        m.fn = e;
                        m.priority = h;
                        for (var n = k.length - 1; n >= 0; n--) if (k[n].priority <= h) {
                            k.splice(n + 1, 0, m);
                            return;
                        }
                        k.unshift(m);
                    }
                },
                fire: (function () {
                    var d = false,
                        e = function () {
                        d = true;
                    },
                        f = false,
                        g = function () {
                        f = true;
                    };
                    return function (h, i, j) {
                        var k = b(this)[h],
                            l = d,
                            m = f;
                        d = f = false;
                        if (k) {
                            var n = k.listeners;
                            if (n.length) {
                                n = n.slice(0);
                                for (var o = 0; o < n.length; o++) {
                                    var p = n[o].call(this, j, i, e, g);
                                    if (typeof p != 'undefined') i = p;
                                    if (d || f) break;
                                }
                            }
                        }
                        var q = f || (typeof i == 'undefined' ? false : i);
                        d = l;
                        f = m;
                        return q;
                    };
                })(),
                fireOnce: function (d, e, f) {
                    var g = this.fire(d, e, f);
                    delete b(this)[d];
                    return g;
                },
                removeListener: function (d, e) {
                    var f = b(this)[d];
                    if (f) {
                        var g = f.getListenerIndex(e);
                        if (g >= 0) f.listeners.splice(g, 1);
                    }
                },
                hasListeners: function (d) {
                    var e = b(this)[d];
                    return e && e.listeners.length > 0;
                }
            };
        })();
    }
    if (!a.editor) {
        a.ELEMENT_MODE_NONE = 0;
        a.ELEMENT_MODE_REPLACE = 1;
        a.ELEMENT_MODE_APPENDTO = 2;
        a.editor = function (b, c, d) {
            var e = this;
            e._ = {
                instanceConfig: b,
                element: c
            };
            e.elementMode = d || 0;
            a.event.call(e);
            e._init();
        };
        a.editor.replace = function (b, c) {
            var d = b;
            if (typeof d != 'object') {
                d = document.getElementById(b);
                if (!d) {
                    var e = 0,
                        f = document.getElementsByName(b);
                    while ((d = f[e++]) && (d.tagName.toLowerCase() != 'textarea')) {}
                }
                if (!d) throw '[CKEDITOR.editor.replace] The element with id or name "' + b + '" was not found.';
            }
            d.style.visibility = 'hidden';
            return new a.editor(c, d, 1);
        };
        a.editor.appendTo = function (b, c) {
            if (typeof b != 'object') {
                b = document.getElementById(b);
                if (!b) throw '[CKEDITOR.editor.appendTo] The element with id "' + b + '" was not found.';
            }
            return new a.editor(c, b, 2);
        };
        a.editor.prototype = {
            _init: function () {
                var b = a.editor._pending || (a.editor._pending = []);
                b.push(this);
            },
            fire: function (b, c) {
                return a.event.prototype.fire.call(this, b, c, this);
            },
            fireOnce: function (b, c) {
                return a.event.prototype.fireOnce.call(this, b, c, this);
            }
        };
        a.event.implementOn(a.editor.prototype, true);
    }
    if (!a.env) a.env = (function () {
        var b = navigator.userAgent.toLowerCase(),
            c = window.opera,
            d = {
            ie:
            /*@cc_on!@*/
            false,
            opera: !!c && c.version,
            webkit: b.indexOf(' applewebkit/') > -1,
            air: b.indexOf(' adobeair/') > -1,
            mac: b.indexOf('macintosh') > -1,
            quirks: document.compatMode == 'BackCompat',
            isCustomDomain: function () {
                return this.ie && document.domain != window.location.hostname;
            }
        };
        d.gecko = navigator.product == 'Gecko' && !d.webkit && !d.opera;
        var e = 0;
        if (d.ie) {
            e = parseFloat(b.match(/msie (\d+)/)[1]);
            d.ie8 = !!document.documentMode;
            d.ie8Compat = document.documentMode == 8;
            d.ie7Compat = e == 7 && !document.documentMode || document.documentMode == 7;
            d.ie6Compat = e < 7 || d.quirks;
        }
        if (d.gecko) {
            var f = b.match(/rv:([\d\.]+)/);
            if (f) {
                f = f[1].split('.');
                e = f[0] * 10000 + (f[1] || 0) * (100) + +(f[2] || 0);
            }
        }
        if (d.opera) e = parseFloat(c.version());
        if (d.air) e = parseFloat(b.match(/ adobeair\/(\d+)/)[1]);
        if (d.webkit) e = parseFloat(b.match(/ applewebkit\/(\d+)/)[1]);
        d.version = e;
        d.isCompatible = d.ie && e >= 6 || d.gecko && e >= 10801 || d.opera && e >= 9.5 || d.air && e >= 1 || d.webkit && e >= 522 || false;
        d.cssClass = 'cke_browser_' + (d.ie ? 'ie' : d.gecko ? 'gecko' : d.opera ? 'opera' : d.air ? 'air' : d.webkit ? 'webkit' : 'unknown');
        if (d.quirks) d.cssClass += ' cke_browser_quirks';
        if (d.ie) {
            d.cssClass += ' cke_browser_ie' + (d.version < 7 ? '6' : d.version >= 8 ? '8' : '7');
            if (d.quirks) d.cssClass += ' cke_browser_iequirks';
        }
        if (d.gecko && e < 10900) d.cssClass += ' cke_browser_gecko18';
        return d;
    })();
    var b = a.env;
    var c = b.ie;
    if (a.status == 'unloaded')(function () {
        a.event.implementOn(a);
        a.loadFullCore = function () {
            if (a.status != 'basic_ready') {
                a.loadFullCore._load = true;
                return;
            }
            delete a.loadFullCore;
            var e = document.createElement('script');
            e.type = 'text/javascript';
            e.src = a.basePath + 'ckeditor.js';
            document.getElementsByTagName('head')[0].appendChild(e);
        };
        a.loadFullCoreTimeout = 0;
        a.replaceClass = 'ckeditor';
        a.replaceByClassEnabled = true;
        var d = function (e, f, g) {
            if (b.isCompatible) {
                if (a.loadFullCore) a.loadFullCore();
                var h = g(e, f);
                a.add(h);
                return h;
            }
            return null;
        };
        a.replace = function (e, f) {
            return d(e, f, a.editor.replace);
        };
        a.appendTo = function (e, f) {
            return d(e, f, a.editor.appendTo);
        };
        a.add = function (e) {
            var f = this._.pending || (this._.pending = []);
            f.push(e);
        };
        a.replaceAll = function () {
            var e = document.getElementsByTagName('textarea');
            for (var f = 0; f < e.length; f++) {
                var g = null,
                    h = e[f],
                    i = h.name;
                if (!h.name && !h.id) continue;
                if (typeof arguments[0] == 'string') {
                    var j = new RegExp('(?:^| )' + arguments[0] + '(?:$| )');
                    if (!j.test(h.className)) continue;
                } else if (typeof arguments[0] == 'function') {
                    g = {};
                    if (arguments[0](h, g) === false) continue;
                }
                this.replace(h, g);
            }
        };
        (function () {
            var e = function () {
                var f = a.loadFullCore,
                    g = a.loadFullCoreTimeout;
                if (a.replaceByClassEnabled) a.replaceAll(a.replaceClass);
                a.status = 'basic_ready';
                if (f && f._load) f();
                else if (g) setTimeout(function () {
                    if (a.loadFullCore) a.loadFullCore();
                },
                g * 1000);
            };
            if (window.addEventListener) window.addEventListener('load', e, false);
            else if (window.attachEvent) window.attachEvent('onload', e);
        })();
        a.status = 'basic_loaded';
    })();
    a.dom = {};
    var d = a.dom;
    (function () {
        var e = [];
        a.tools = {
            arrayCompare: function (f, g) {
                if (!f && !g) return true;
                if (!f || !g || f.length != g.length) return false;
                for (var h = 0; h < f.length; h++) if (f[h] != g[h]) return false;
                return true;
            },
            clone: function (f) {
                var g;
                if (f && f instanceof Array) {
                    g = [];
                    for (var h = 0; h < f.length; h++) g[h] = this.clone(f[h]);
                    return g;
                }
                if (f === null || typeof f != 'object' || f instanceof String || f instanceof Number || f instanceof Boolean || f instanceof Date) return f;
                g = new f.constructor();
                for (var i in f) {
                    var j = f[i];
                    g[i] = this.clone(j);
                }
                return g;
            },
            extend: function (f) {
                var g = arguments.length,
                    h, i;
                if (typeof(h = arguments[g - 1]) == 'boolean') g--;
                else if (typeof(h = arguments[g - 2]) == 'boolean') {
                    i = arguments[g - 1];
                    g -= 2;
                }
                for (var j = 1; j < g; j++) {
                    var k = arguments[j];
                    for (var l in k) if (h === true || f[l] == undefined) if (!i || l in i) f[l] = k[l];
                }
                return f;
            },
            prototypedCopy: function (f) {
                var g = function () {};
                g.prototype = f;
                return new g();
            },
            isArray: function (f) {
                return !!f && f instanceof Array;
            },
            cssStyleToDomStyle: function (f) {
                if (f == 'float') return 'cssFloat';
                else return f.replace(/-./g, function (g) {
                    return g.substr(1).toUpperCase();
                });
            },
            htmlEncode: function (f) {
                var g = function (k) {
                    var l = new d.element('span');
                    l.setText(k);
                    return l.getHtml();
                },
                    h = g('\n').toLowerCase() == '<br>' ?
                function (k) {
                    return g(k).replace(/<br>/gi, '\n');
                } : g,
                i = g('>') == '>' ?
                function (k) {
                    return h(k).replace(/>/g, '&gt;');
                } : h,
                j = g('  ') == '&nbsp; ' ?
                function (k) {
                    return i(k).replace(/&nbsp;/g, ' ');
                } : i;
                this.htmlEncode = j;
                return this.htmlEncode(f);
            },
            getNextNumber: (function () {
                var f = 0;
                return function () {
                    return ++f;
                };
            })(),
            override: function (f, g) {
                return g(f);
            },
            setTimeout: function (f, g, h, i, j) {
                if (!j) j = window;
                if (!h) h = j;
                return j.setTimeout(function () {
                    if (i) f.apply(h, [].concat(i));
                    else f.apply(h);
                },
                g || 0);
            },
            trim: (function () {
                var f = /(?:^[ \t\n\r]+)|(?:[ \t\n\r]+$)/g;
                return function (g) {
                    return g.replace(f, '');
                };
            })(),
            ltrim: (function () {
                var f = /^[ \t\n\r]+/g;
                return function (g) {
                    return g.replace(f, '');
                };
            })(),
            rtrim: (function () {
                var f = /[ \t\n\r]+$/g;
                return function (g) {
                    return g.replace(f, '');
                };
            })(),
            indexOf: Array.prototype.indexOf ?
            function (f, g) {
                return f.indexOf(g);
            } : function (f, g) {
                for (var h = 0, i = f.length; h < i; h++) if (f[h] === g) return h;
                return -1;
            },
            bind: function (f, g) {
                return function () {
                    return f.apply(g, arguments);
                };
            },
            createClass: function (f) {
                var g = f.$,
                    h = f.base,
                    i = f.privates || f._,
                    j = f.proto,
                    k = f.statics;
                if (i) {
                    var l = g;
                    g = function () {
                        var p = this;
                        var m = p._ || (p._ = {});
                        for (var n in i) {
                            var o = i[n];
                            m[n] = typeof o == 'function' ? a.tools.bind(o, p) : o;
                        }
                        l.apply(p, arguments);
                    };
                }
                if (h) {
                    g.prototype = this.prototypedCopy(h.prototype);
                    g.prototype['constructor'] = g;
                    g.prototype.base = function () {
                        this.base = h.prototype.base;
                        h.apply(this, arguments);
                        this.base = arguments.callee;
                    };
                }
                if (j) this.extend(g.prototype, j, true);
                if (k) this.extend(g, k, true);
                return g;
            },
            addFunction: function (f, g) {
                return e.push(function () {
                    f.apply(g || this, arguments);
                }) - 1;
            },
            callFunction: function (f) {
                var g = e[f];
                return g.apply(window, Array.prototype.slice.call(arguments, 1));
            },
            cssLength: (function () {
                var f = /^\d+(?:\.\d+)?$/;
                return function (g) {
                    return g + (f.test(g) ? 'px' : '');
                };
            })(),
            repeat: function (f, g) {
                return new Array(g + 1).join(f);
            }
        };
    })();
    var e = a.tools;
    a.dtd = (function () {
        var f = e.extend,
            g = {
            isindex: 1,
            fieldset: 1
        },
            h = {
            input: 1,
            button: 1,
            select: 1,
            textarea: 1,
            label: 1
        },
            i = f({
            a: 1
        },
        h),
            j = f({
            iframe: 1
        },
        i),
            k = {
            hr: 1,
            ul: 1,
            menu: 1,
            div: 1,
            blockquote: 1,
            noscript: 1,
            table: 1,
            center: 1,
            address: 1,
            dir: 1,
            pre: 1,
            h5: 1,
            dl: 1,
            h4: 1,
            noframes: 1,
            h6: 1,
            ol: 1,
            h1: 1,
            h3: 1,
            h2: 1
        },
            l = {
            ins: 1,
            del: 1,
            script: 1
        },
            m = f({
            b: 1,
            acronym: 1,
            bdo: 1,
            'var': 1,
            '#': 1,
            abbr: 1,
            code: 1,
            br: 1,
            i: 1,
            cite: 1,
            kbd: 1,
            u: 1,
            strike: 1,
            s: 1,
            tt: 1,
            strong: 1,
            q: 1,
            samp: 1,
            em: 1,
            dfn: 1,
            span: 1
        },
        l),
            n = f({
            sub: 1,
            img: 1,
            object: 1,
            sup: 1,
            basefont: 1,
            map: 1,
            applet: 1,
            font: 1,
            big: 1,
            small: 1
        },
        m),
            o = f({
            p: 1
        },
        n),
            p = f({
            iframe: 1
        },
        n, h),
            q = {
            img: 1,
            noscript: 1,
            br: 1,
            kbd: 1,
            center: 1,
            button: 1,
            basefont: 1,
            h5: 1,
            h4: 1,
            samp: 1,
            h6: 1,
            ol: 1,
            h1: 1,
            h3: 1,
            h2: 1,
            form: 1,
            font: 1,
            '#': 1,
            select: 1,
            menu: 1,
            ins: 1,
            abbr: 1,
            label: 1,
            code: 1,
            table: 1,
            script: 1,
            cite: 1,
            input: 1,
            iframe: 1,
            strong: 1,
            textarea: 1,
            noframes: 1,
            big: 1,
            small: 1,
            span: 1,
            hr: 1,
            sub: 1,
            bdo: 1,
            'var': 1,
            div: 1,
            object: 1,
            sup: 1,
            strike: 1,
            dir: 1,
            map: 1,
            dl: 1,
            applet: 1,
            del: 1,
            isindex: 1,
            fieldset: 1,
            ul: 1,
            b: 1,
            acronym: 1,
            a: 1,
            blockquote: 1,
            i: 1,
            u: 1,
            s: 1,
            tt: 1,
            address: 1,
            q: 1,
            pre: 1,
            p: 1,
            em: 1,
            dfn: 1
        },
            r = f({
            a: 1
        },
        p),
            s = {
            tr: 1
        },
            t = {
            '#': 1
        },
            u = f({
            param: 1
        },
        q),
            v = f({
            form: 1
        },
        g, j, k, o),
            w = {
            li: 1
        },
            x = {
            address: 1,
            blockquote: 1,
            center: 1,
            dir: 1,
            div: 1,
            dl: 1,
            fieldset: 1,
            form: 1,
            h1: 1,
            h2: 1,
            h3: 1,
            h4: 1,
            h5: 1,
            h6: 1,
            hr: 1,
            isindex: 1,
            menu: 1,
            noframes: 1,
            ol: 1,
            p: 1,
            pre: 1,
            table: 1,
            ul: 1
        };
        return {
            $block: x,
            $body: f({
                script: 1
            },
            x),
            $cdata: {
                script: 1,
                style: 1
            },
            $empty: {
                area: 1,
                base: 1,
                br: 1,
                col: 1,
                hr: 1,
                img: 1,
                input: 1,
                link: 1,
                meta: 1,
                param: 1
            },
            $listItem: {
                dd: 1,
                dt: 1,
                li: 1
            },
            $nonEditable: {
                applet: 1,
                button: 1,
                embed: 1,
                iframe: 1,
                map: 1,
                object: 1,
                option: 1,
                script: 1,
                textarea: 1
            },
            $removeEmpty: {
                abbr: 1,
                acronym: 1,
                address: 1,
                b: 1,
                bdo: 1,
                big: 1,
                cite: 1,
                code: 1,
                del: 1,
                dfn: 1,
                em: 1,
                font: 1,
                i: 1,
                ins: 1,
                label: 1,
                kbd: 1,
                q: 1,
                s: 1,
                samp: 1,
                small: 1,
                span: 1,
                strike: 1,
                strong: 1,
                sub: 1,
                sup: 1,
                tt: 1,
                u: 1,
                'var': 1
            },
            $tabIndex: {
                a: 1,
                area: 1,
                button: 1,
                input: 1,
                object: 1,
                select: 1,
                textarea: 1
            },
            $tableContent: {
                caption: 1,
                col: 1,
                colgroup: 1,
                tbody: 1,
                td: 1,
                tfoot: 1,
                th: 1,
                thead: 1,
                tr: 1
            },
            col: {},
            tr: {
                td: 1,
                th: 1
            },
            img: {},
            colgroup: {
                col: 1
            },
            noscript: v,
            td: v,
            br: {},
            th: v,
            center: v,
            kbd: r,
            button: f(o, k),
            basefont: {},
            h5: r,
            h4: r,
            samp: r,
            h6: r,
            ol: w,
            h1: r,
            h3: r,
            option: t,
            h2: r,
            form: f(g, j, k, o),
            select: {
                optgroup: 1,
                option: 1
            },
            font: r,
            ins: v,
            menu: w,
            abbr: r,
            label: r,
            table: {
                thead: 1,
                col: 1,
                tbody: 1,
                tr: 1,
                colgroup: 1,
                caption: 1,
                tfoot: 1
            },
            code: r,
            script: t,
            tfoot: s,
            cite: r,
            li: v,
            input: {},
            iframe: v,
            strong: r,
            textarea: t,
            noframes: v,
            big: r,
            small: r,
            span: r,
            hr: {},
            dt: r,
            sub: r,
            optgroup: {
                option: 1
            },
            param: {},
            bdo: r,
            'var': r,
            div: v,
            object: u,
            sup: r,
            dd: v,
            strike: r,
            area: {},
            dir: w,
            map: f({
                area: 1,
                form: 1,
                p: 1
            },
            g, l, k),
            applet: u,
            dl: {
                dt: 1,
                dd: 1
            },
            del: v,
            isindex: {},
            fieldset: f({
                legend: 1
            },
            q),
            thead: s,
            ul: w,
            acronym: r,
            b: r,
            a: p,
            blockquote: v,
            caption: r,
            i: r,
            u: r,
            tbody: s,
            s: r,
            address: f(j, o),
            tt: r,
            legend: r,
            q: r,
            pre: f(m, i),
            p: r,
            em: r,
            dfn: r
        };
    })();
    var f = a.dtd;
    d.event = function (g) {
        this.$ = g;
    };
    d.event.prototype = {
        getKey: function () {
            return this.$.keyCode || this.$.which;
        },
        getKeystroke: function () {
            var h = this;
            var g = h.getKey();
            if (h.$.ctrlKey || h.$.metaKey) g += 1000;
            if (h.$.shiftKey) g += 2000;
            if (h.$.altKey) g += 4000;
            return g;
        },
        preventDefault: function (g) {
            var h = this.$;
            if (h.preventDefault) h.preventDefault();
            else h.returnValue = false;
            if (g) if (h.stopPropagation) h.stopPropagation();
            else h.cancelBubble = true;
        },
        getTarget: function () {
            var g = this.$.target || this.$.srcElement;
            return g ? new d.node(g) : null;
        }
    };
    a.CTRL = 1000;
    a.SHIFT = 2000;
    a.ALT = 4000;
    d.domObject = function (g) {
        if (g) this.$ = g;
    };
    d.domObject.prototype = (function () {
        var g = function (h, i) {
            return function (j) {
                if (typeof a != 'undefined') h.fire(i, new d.event(j));
            };
        };
        return {
            getPrivate: function () {
                var h;
                if (! (h = this.getCustomData('_'))) this.setCustomData('_', h = {});
                return h;
            },
            on: function (h) {
                var k = this;
                var i = k.getCustomData('_cke_nativeListeners');
                if (!i) {
                    i = {};
                    k.setCustomData('_cke_nativeListeners', i);
                }
                if (!i[h]) {
                    var j = i[h] = g(k, h);
                    if (k.$.addEventListener) k.$.addEventListener(h, j, !!a.event.useCapture);
                    else if (k.$.attachEvent) k.$.attachEvent('on' + h, j);
                }
                return a.event.prototype.on.apply(k, arguments);
            },
            removeListener: function (h) {
                var k = this;
                a.event.prototype.removeListener.apply(k, arguments);
                if (!k.hasListeners(h)) {
                    var i = k.getCustomData('_cke_nativeListeners'),
                        j = i && i[h];
                    if (j) {
                        if (k.$.removeEventListener) k.$.removeEventListener(h, j, false);
                        else if (k.$.detachEvent) k.$.detachEvent('on' + h, j);
                        delete i[h];
                    }
                }
            }
        };
    })();
    (function (g) {
        var h = {};
        g.equals = function (i) {
            return i && i.$ === this.$;
        };
        g.setCustomData = function (i, j) {
            var k = this.getUniqueId(),
                l = h[k] || (h[k] = {});
            l[i] = j;
            return this;
        };
        g.getCustomData = function (i) {
            var j = this.$._cke_expando,
                k = j && h[j];
            return k && k[i];
        };
        g.removeCustomData = function (i) {
            var j = this.$._cke_expando,
                k = j && h[j],
                l = k && k[i];
            if (typeof l != 'undefined') delete k[i];
            return l || null;
        };
        g.getUniqueId = function () {
            return this.$._cke_expando || (this.$._cke_expando = e.getNextNumber());
        };
        a.event.implementOn(g);
    })(d.domObject.prototype);
    d.window = function (g) {
        d.domObject.call(this, g);
    };
    d.window.prototype = new d.domObject();
    e.extend(d.window.prototype, {
        focus: function () {
            if (b.webkit && this.$.parent) this.$.parent.focus();
            this.$.focus();
        },
        getViewPaneSize: function () {
            var g = this.$.document,
                h = g.compatMode == 'CSS1Compat';
            return {
                width: (h ? g.documentElement.clientWidth : g.body.clientWidth) || (0),
                height: (h ? g.documentElement.clientHeight : g.body.clientHeight) || (0)
            };
        },
        getScrollPosition: function () {
            var g = this.$;
            if ('pageXOffset' in g) return {
                x: g.pageXOffset || 0,
                y: g.pageYOffset || 0
            };
            else {
                var h = g.document;
                return {
                    x: h.documentElement.scrollLeft || h.body.scrollLeft || 0,
                    y: h.documentElement.scrollTop || h.body.scrollTop || 0
                };
            }
        }
    });
    d.document = function (g) {
        d.domObject.call(this, g);
    };
    var g = d.document;
    g.prototype = new d.domObject();
    e.extend(g.prototype, {
        appendStyleSheet: function (h) {
            if (this.$.createStyleSheet) this.$.createStyleSheet(h);
            else {
                var i = new d.element('link');
                i.setAttributes({
                    rel: 'stylesheet',
                    type: 'text/css',
                    href: h
                });
                this.getHead().append(i);
            }
        },
        createElement: function (h, i) {
            var j = new d.element(h, this);
            if (i) {
                if (i.attributes) j.setAttributes(i.attributes);
                if (i.styles) j.setStyles(i.styles);
            }
            return j;
        },
        createText: function (h) {
            return new d.text(h, this);
        },
        focus: function () {
            this.getWindow().focus();
        },
        getById: function (h) {
            var i = this.$.getElementById(h);
            return i ? new d.element(i) : null;
        },
        getByAddress: function (h, i) {
            var j = this.$.documentElement;
            for (var k = 0; j && k < h.length; k++) {
                var l = h[k];
                if (!i) {
                    j = j.childNodes[l];
                    continue;
                }
                var m = -1;
                for (var n = 0; n < j.childNodes.length; n++) {
                    var o = j.childNodes[n];
                    if (i === true && o.nodeType == 3 && o.previousSibling && o.previousSibling.nodeType == 3) continue;
                    m++;
                    if (m == l) {
                        j = o;
                        break;
                    }
                }
            }
            return j ? new d.node(j) : null;
        },
        getElementsByTag: function (h, i) {
            if (!c && i) h = i + ':' + h;
            return new d.nodeList(this.$.getElementsByTagName(h));
        },
        getHead: function () {
            var h = this.$.getElementsByTagName('head')[0];
            h = new d.element(h);
            return (this.getHead = function () {
                return h;
            })();
        },
        getBody: function () {
            var h = new d.element(this.$.body);
            return (this.getBody = function () {
                return h;
            })();
        },
        getDocumentElement: function () {
            var h = new d.element(this.$.documentElement);
            return (this.getDocumentElement = function () {
                return h;
            })();
        },
        getWindow: function () {
            var h = new d.window(this.$.parentWindow || this.$.defaultView);
            return (this.getWindow = function () {
                return h;
            })();
        }
    });
    d.node = function (h) {
        if (h) {
            switch (h.nodeType) {
            case 1:
                return new d.element(h);
            case 3:
                return new d.text(h);
            }
            d.domObject.call(this, h);
        }
        return this;
    };
    d.node.prototype = new d.domObject();
    a.NODE_ELEMENT = 1;
    a.NODE_TEXT = 3;
    a.NODE_COMMENT = 8;
    a.NODE_DOCUMENT_FRAGMENT = 11;
    a.POSITION_IDENTICAL = 0;
    a.POSITION_DISCONNECTED = 1;
    a.POSITION_FOLLOWING = 2;
    a.POSITION_PRECEDING = 4;
    a.POSITION_IS_CONTAINED = 8;
    a.POSITION_CONTAINS = 16;
    e.extend(d.node.prototype, {
        appendTo: function (h, i) {
            h.append(this, i);
            return h;
        },
        clone: function (h, i) {
            var j = this.$.cloneNode(h);
            if (!i) {
                var k = function (l) {
                    if (l.nodeType != 1) return;
                    l.removeAttribute('id', false);
                    l.removeAttribute('_cke_expando', false);
                    var m = l.childNodes;
                    for (var n = 0; n < m.length; n++) k(m[n]);
                };
                k(j);
            }
            return new d.node(j);
        },
        hasPrevious: function () {
            return !!this.$.previousSibling;
        },
        hasNext: function () {
            return !!this.$.nextSibling;
        },
        insertAfter: function (h) {
            h.$.parentNode.insertBefore(this.$, h.$.nextSibling);
            return h;
        },
        insertBefore: function (h) {
            h.$.parentNode.insertBefore(this.$, h.$);
            return h;
        },
        insertBeforeMe: function (h) {
            this.$.parentNode.insertBefore(h.$, this.$);
            return h;
        },
        getAddress: function (h) {
            var i = [],
                j = this.getDocument().$.documentElement,
                k = this.$;
            while (k && k != j) {
                var l = k.parentNode,
                    m = -1;
                for (var n = 0; n < l.childNodes.length; n++) {
                    var o = l.childNodes[n];
                    if (h && o.nodeType == 3 && o.previousSibling && o.previousSibling.nodeType == 3) continue;
                    m++;
                    if (o == k) break;
                }
                i.unshift(m);
                k = k.parentNode;
            }
            return i;
        },
        getDocument: function () {
            var h = new g(this.$.ownerDocument || this.$.parentNode.ownerDocument);
            return (this.getDocument = function () {
                return h;
            })();
        },
        getIndex: function () {
            var h = this.$,
                i = h.parentNode && h.parentNode.firstChild,
                j = -1;
            while (i) {
                j++;
                if (i == h) return j;
                i = i.nextSibling;
            }
            return -1;
        },
        getNextSourceNode: function (h, i, j) {
            if (j && !j.call) {
                var k = j;
                j = function (n) {
                    return !n.equals(k);
                };
            }
            var l = !h && this.getFirst && this.getFirst(),
                m;
            if (!l) {
                if (this.type == 1 && j && j(this, true) === false) return null;
                l = this.getNext();
            }
            while (!l && (m = (m || this).getParent())) {
                if (j && j(m, true) === false) return null;
                l = m.getNext();
            }
            if (!l) return null;
            if (j && j(l) === false) return null;
            if (i && i != l.type) return l.getNextSourceNode(false, i, j);
            return l;
        },
        getPreviousSourceNode: function (h, i, j) {
            if (j && !j.call) {
                var k = j;
                j = function (n) {
                    return !n.equals(k);
                };
            }
            var l = !h && this.getLast && this.getLast(),
                m;
            if (!l) {
                if (this.type == 1 && j && j(this, true) === false) return null;
                l = this.getPrevious();
            }
            while (!l && (m = (m || this).getParent())) {
                if (j && j(m, true) === false) return null;
                l = m.getPrevious();
            }
            if (!l) return null;
            if (j && j(l) === false) return null;
            if (i && l.type != i) return l.getPreviousSourceNode(false, i, j);
            return l;
        },
        getPrevious: function (h) {
            var i = this.$,
                j;
            do {
                i = i.previousSibling;
                j = i && new d.node(i);
            } while (j && h && !h(j)) return j;
        },
        getNext: function (h) {
            var i = this.$,
                j;
            do {
                i = i.nextSibling;
                j = i && new d.node(i);
            } while (j && h && !h(j)) return j;
        },
        getParent: function () {
            var h = this.$.parentNode;
            return h && h.nodeType == 1 ? new d.node(h) : null;
        },
        getParents: function (h) {
            var i = this,
                j = [];
            do j[h ? 'push' : 'unshift'](i);
            while (i = i.getParent()) return j;
        },
        getCommonAncestor: function (h) {
            var j = this;
            if (h.equals(j)) return j;
            if (h.contains && h.contains(j)) return h;
            var i = j.contains ? j : j.getParent();
            do
            if (i.contains(h)) return i;
            while (i = i.getParent()) return null;
        },
        getPosition: function (h) {
            var i = this.$,
                j = h.$;
            if (i.compareDocumentPosition) return i.compareDocumentPosition(j);
            if (i == j) return 0;
            if (this.type == 1 && h.type == 1) {
                if (i.contains) {
                    if (i.contains(j)) return 16 + 4;
                    if (j.contains(i)) return 8 + 2;
                }
                if ('sourceIndex' in i) return i.sourceIndex < 0 || j.sourceIndex < 0 ? 1 : i.sourceIndex < j.sourceIndex ? 4 : 2;
            }
            var k = this.getAddress(),
                l = h.getAddress(),
                m = Math.min(k.length, l.length);
            for (var n = 0; n <= m - 1; n++) if (k[n] != l[n]) {
                if (n < m) return k[n] < l[n] ? 4 : 2;
                break;
            }
            return k.length < l.length ? 16 + 4 : 8 + 2;
        },
        getAscendant: function (h, i) {
            var j = this.$;
            if (!i) j = j.parentNode;
            while (j) {
                if (j.nodeName && j.nodeName.toLowerCase() == h) return new d.node(j);
                j = j.parentNode;
            }
            return null;
        },
        hasAscendant: function (h, i) {
            var j = this.$;
            if (!i) j = j.parentNode;
            while (j) {
                if (j.nodeName && j.nodeName.toLowerCase() == h) return true;
                j = j.parentNode;
            }
            return false;
        },
        move: function (h, i) {
            h.append(this.remove(), i);
        },
        remove: function (h) {
            var i = this.$,
                j = i.parentNode;
            if (j) {
                if (h) for (var k; k = i.firstChild;) j.insertBefore(i.removeChild(k), i);
                j.removeChild(i);
            }
            return this;
        },
        replace: function (h) {
            this.insertBefore(h);
            h.remove();
        },
        trim: function () {
            this.ltrim();
            this.rtrim();
        },
        ltrim: function () {
            var k = this;
            var h;
            while (k.getFirst && (h = k.getFirst())) {
                if (h.type == 3) {
                    var i = e.ltrim(h.getText()),
                        j = h.getLength();
                    if (!i) {
                        h.remove();
                        continue;
                    } else if (i.length < j) {
                        h.split(j - i.length);
                        k.$.removeChild(k.$.firstChild);
                    }
                }
                break;
            }
        },
        rtrim: function () {
            var k = this;
            var h;
            while (k.getLast && (h = k.getLast())) {
                if (h.type == 3) {
                    var i = e.rtrim(h.getText()),
                        j = h.getLength();
                    if (!i) {
                        h.remove();
                        continue;
                    } else if (i.length < j) {
                        h.split(i.length);
                        k.$.lastChild.parentNode.removeChild(k.$.lastChild);
                    }
                }
                break;
            }
            if (!c && !b.opera) {
                h = k.$.lastChild;
                if (h && h.type == 1 && h.nodeName.toLowerCase() == 'br') h.parentNode.removeChild(h);
            }
        }
    });
    d.nodeList = function (h) {
        this.$ = h;
    };
    d.nodeList.prototype = {
        count: function () {
            return this.$.length;
        },
        getItem: function (h) {
            var i = this.$[h];
            return i ? new d.node(i) : null;
        }
    };
    d.element = function (h, i) {
        if (typeof h == 'string') h = (i ? i.$ : document).createElement(h);
        d.domObject.call(this, h);
    };
    var h = d.element;
    h.get = function (i) {
        return i && (i.$ ? i : new h(i));
    };
    h.prototype = new d.node();
    h.createFromHtml = function (i, j) {
        var k = new h('div', j);
        k.setHtml(i);
        return k.getFirst().remove();
    };
    h.setMarker = function (i, j, k, l) {
        var m = j.getCustomData('list_marker_id') || j.setCustomData('list_marker_id', e.getNextNumber()).getCustomData('list_marker_id'),
            n = j.getCustomData('list_marker_names') || j.setCustomData('list_marker_names', {}).getCustomData('list_marker_names');
        i[m] = j;
        n[k] = 1;
        return j.setCustomData(k, l);
    };
    h.clearAllMarkers = function (i) {
        for (var j in i) h.clearMarkers(i, i[j], true);
    };
    h.clearMarkers = function (i, j, k) {
        var l = j.getCustomData('list_marker_names'),
            m = j.getCustomData('list_marker_id');
        for (var n in l) j.removeCustomData(n);
        j.removeCustomData('list_marker_names');
        if (k) {
            j.removeCustomData('list_marker_id');
            delete i[m];
        }
    };
    e.extend(h.prototype, {
        type: 1,
        addClass: function (i) {
            var j = this.$.className;
            if (j) {
                var k = new RegExp('(?:^|\\s)' + i + '(?:\\s|$)', '');
                if (!k.test(j)) j += ' ' + i;
            }
            this.$.className = j || i;
        },
        removeClass: function (i) {
            var j = this.getAttribute('class');
            if (j) {
                var k = new RegExp('(?:^|\\s+)' + i + '(?=\\s|$)', 'i');
                if (k.test(j)) {
                    j = j.replace(k, '').replace(/^\s+/, '');
                    if (j) this.setAttribute('class', j);
                    else this.removeAttribute('class');
                }
            }
        },
        hasClass: function (i) {
            var j = new RegExp('(?:^|\\s+)' + i + '(?=\\s|$)', '');
            return j.test(this.getAttribute('class'));
        },
        append: function (i, j) {
            var k = this;
            if (typeof i == 'string') i = k.getDocument().createElement(i);
            if (j) k.$.insertBefore(i.$, k.$.firstChild);
            else k.$.appendChild(i.$);
            return i;
        },
        appendHtml: function (i) {
            var k = this;
            if (!k.$.childNodes.length) k.setHtml(i);
            else {
                var j = new h('div', k.getDocument());
                j.setHtml(i);
                j.moveChildren(k);
            }
        },
        appendText: function (i) {
            if (this.$.text != undefined) this.$.text += i;
            else this.append(new d.text(i));
        },
        appendBogus: function () {
            var j = this;
            var i = j.getLast();
            while (i && i.type == 3 && !e.rtrim(i.getText())) i = i.getPrevious();
            if (!i || !i.is || !i.is('br')) j.append(b.opera ? j.getDocument().createText('') : j.getDocument().createElement('br'));
        },
        breakParent: function (i) {
            var l = this;
            var j = new d.range(l.getDocument());
            j.setStartAfter(l);
            j.setEndAfter(i);
            var k = j.extractContents();
            j.insertNode(l.remove());
            k.insertAfterNode(l);
        },
        contains: c || b.webkit ?
        function (i) {
            var j = this.$;
            return i.type != 1 ? j.contains(i.getParent().$) : j != i.$ && j.contains(i.$);
        } : function (i) {
            return !! (this.$.compareDocumentPosition(i.$) & 16);
        },
        focus: function () {
            try {
                this.$.focus();
            } catch(i) {}
        },
        getHtml: function () {
            return this.$.innerHTML;
        },
        getOuterHtml: function () {
            var j = this;
            if (j.$.outerHTML) return j.$.outerHTML.replace(/<\?[^>]*>/, '');
            var i = j.$.ownerDocument.createElement('div');
            i.appendChild(j.$.cloneNode(true));
            return i.innerHTML;
        },
        setHtml: function (i) {
            return this.$.innerHTML = i;
        },
        setText: function (i) {
            h.prototype.setText = this.$.innerText != undefined ?
            function (j) {
                return this.$.innerText = j;
            } : function (j) {
                return this.$.textContent = j;
            };
            return this.setText(i);
        },
        getAttribute: (function () {
            var i = function (j) {
                return this.$.getAttribute(j, 2);
            };
            if (c && (b.ie7Compat || b.ie6Compat)) return function (j) {
                var l = this;
                switch (j) {
                case 'class':
                    j = 'className';
                    break;
                case 'tabindex':
                    var k = i.call(l, j);
                    if (k !== 0 && l.$.tabIndex === 0) k = null;
                    return k;
                    break;
                case 'checked':
                    return l.$.checked;
                    break;
                case 'style':
                    return l.$.style.cssText;
                }
                return i.call(l, j);
            };
            else return i;
        })(),
        getChildren: function () {
            return new d.nodeList(this.$.childNodes);
        },
        getComputedStyle: c ?
        function (i) {
            return this.$.currentStyle[e.cssStyleToDomStyle(i)];
        } : function (i) {
            return this.getWindow().$.getComputedStyle(this.$, '').getPropertyValue(i);
        },
        getDtd: function () {
            var i = f[this.getName()];
            this.getDtd = function () {
                return i;
            };
            return i;
        },
        getElementsByTag: g.prototype.getElementsByTag,
        getTabIndex: c ?
        function () {
            var i = this.$.tabIndex;
            if (i === 0 && !f.$tabIndex[this.getName()] && parseInt(this.getAttribute('tabindex'), 10) !== 0) i = -1;
            return i;
        } : b.webkit ?
        function () {
            var i = this.$.tabIndex;
            if (i == undefined) {
                i = parseInt(this.getAttribute('tabindex'), 10);
                if (isNaN(i)) i = -1;
            }
            return i;
        } : function () {
            return this.$.tabIndex;
        },
        getText: function () {
            return this.$.textContent || this.$.innerText || '';
        },
        getWindow: function () {
            return this.getDocument().getWindow();
        },
        getId: function () {
            return this.$.id || null;
        },
        getNameAtt: function () {
            return this.$.name || null;
        },
        getName: function () {
            var i = this.$.nodeName.toLowerCase();
            if (c) {
                var j = this.$.scopeName;
                if (j != 'HTML') i = j.toLowerCase() + ':' + i;
            }
            return (this.getName = function () {
                return i;
            })();
        },
        getValue: function () {
            return this.$.value;
        },
        getFirst: function () {
            var i = this.$.firstChild;
            return i ? new d.node(i) : null;
        },
        getLast: function (i) {
            var j = this.$.lastChild,
                k = j && new d.node(j);
            if (k && i && !i(k)) k = k.getPrevious(i);
            return k;
        },
        getStyle: function (i) {
            return this.$.style[e.cssStyleToDomStyle(i)];
        },
        is: function () {
            var i = this.getName();
            for (var j = 0; j < arguments.length; j++) if (arguments[j] == i) return true;
            return false;
        },
        isEditable: function () {
            var i = this.getName(),
                j = !f.$nonEditable[i] && (f[i] || f.span);
            return j && j['#'];
        },
        isIdentical: function (i) {
            if (this.getName() != i.getName()) return false;
            var j = this.$.attributes,
                k = i.$.attributes,
                l = j.length,
                m = k.length;
            if (!c && l != m) return false;
            for (var n = 0; n < l; n++) {
                var o = j[n];
                if ((!c || o.specified && o.nodeName != '_cke_expando') && (o.nodeValue != i.getAttribute(o.nodeName))) return false;
            }
            if (c) for (n = 0; n < m; n++) {
                o = k[n];
                if ((!c || o.specified && o.nodeName != '_cke_expando') && (o.nodeValue != j.getAttribute(o.nodeName))) return false;
            }
            return true;
        },
        isVisible: function () {
            return this.$.offsetWidth && this.$.style.visibility != 'hidden';
        },
        hasAttributes: c && (b.ie7Compat || b.ie6Compat) ?
        function () {
            var i = this.$.attributes;
            for (var j = 0; j < i.length; j++) {
                var k = i[j];
                switch (k.nodeName) {
                case 'class':
                    if (this.getAttribute('class')) return true;
                case '_cke_expando':
                    continue;
                default:
                    if (k.specified) return true;
                }
            }
            return false;
        } : function () {
            var i = this.$.attributes;
            return i.length > 1 || i.length == 1 && i[0].nodeName != '_cke_expando';
        },
        hasAttribute: function (i) {
            var j = this.$.attributes.getNamedItem(i);
            return !! (j && j.specified);
        },
        hide: function () {
            this.setStyle('display', 'none');
        },
        moveChildren: function (i, j) {
            var k = this.$;
            i = i.$;
            if (k == i) return;
            var l;
            if (j) while (l = k.lastChild) i.insertBefore(k.removeChild(l), i.firstChild);
            else while (l = k.firstChild) i.appendChild(k.removeChild(l));
        },
        show: function () {
            this.setStyles({
                display: '',
                visibility: ''
            });
        },
        setAttribute: (function () {
            var i = function (j, k) {
                this.$.setAttribute(j, k);
                return this;
            };
            if (c && (b.ie7Compat || b.ie6Compat)) return function (j, k) {
                var l = this;
                if (j == 'class') l.$.className = k;
                else if (j == 'style') l.$.style.cssText = k;
                else if (j == 'tabindex') l.$.tabIndex = k;
                else if (j == 'checked') l.$.checked = k;
                else i.apply(l, arguments);
                return l;
            };
            else return i;
        })(),
        setAttributes: function (i) {
            for (var j in i) this.setAttribute(j, i[j]);
            return this;
        },
        setValue: function (i) {
            this.$.value = i;
            return this;
        },
        removeAttribute: (function () {
            var i = function (j) {
                this.$.removeAttribute(j);
            };
            if (c && (b.ie7Compat || b.ie6Compat)) return function (j) {
                if (j == 'class') j = 'className';
                else if (j == 'tabindex') j = 'tabIndex';
                i.call(this, j);
            };
            else return i;
        })(),
        removeAttributes: function (i) {
            for (var j = 0; j < i.length; j++) this.removeAttribute(i[j]);
        },
        removeStyle: function (i) {
            var j = this;
            if (j.$.style.removeAttribute) j.$.style.removeAttribute(e.cssStyleToDomStyle(i));
            else j.setStyle(i, '');
            if (!j.$.style.cssText) j.removeAttribute('style');
        },
        setStyle: function (i, j) {
            this.$.style[e.cssStyleToDomStyle(i)] = j;
            return this;
        },
        setStyles: function (i) {
            for (var j in i) this.setStyle(j, i[j]);
            return this;
        },
        setOpacity: function (i) {
            if (c) {
                i = Math.round(i * 100);
                this.setStyle('filter', i >= 100 ? '' : 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + i + ')');
            } else this.setStyle('opacity', i);
        },
        unselectable: b.gecko ?
        function () {
            this.$.style.MozUserSelect = 'none';
        } : b.webkit ?
        function () {
            this.$.style.KhtmlUserSelect = 'none';
        } : function () {
            if (c || b.opera) {
                var i = this.$,
                    j, k = 0;
                i.unselectable = 'on';
                while (j = i.all[k++]) switch (j.tagName.toLowerCase()) {
                case 'iframe':
                case 'textarea':
                case 'input':
                case 'select':
                    break;
                default:
                    j.unselectable = 'on';
                }
            }
        },
        getPositionedAncestor: function () {
            var i = this;
            while (i.getName() != 'html') {
                if (i.getComputedStyle('position') != 'static') return i;
                i = i.getParent();
            }
            return null;
        },
        getDocumentPosition: function (i) {
            var D = this;
            var j = 0,
                k = 0,
                l = D.getDocument().getBody(),
                m = D.getDocument().$.compatMode == 'BackCompat',
                n = D.getDocument();
            if (document.documentElement.getBoundingClientRect) {
                var o = D.$.getBoundingClientRect(),
                    p = n.$,
                    q = p.documentElement,
                    r = q.clientTop || l.$.clientTop || 0,
                    s = q.clientLeft || l.$.clientLeft || 0,
                    t = true;
                if (c) {
                    var u = n.getDocumentElement().contains(D),
                        v = n.getBody().contains(D);
                    t = m && v || !m && u;
                }
                if (t) {
                    j = o.left + (!m && q.scrollLeft || l.$.scrollLeft);
                    j -= s;
                    k = o.top + (!m && q.scrollTop || l.$.scrollTop);
                    k -= r;
                }
            } else {
                var w = D,
                    x = null,
                    y;
                while (w && !(w.getName() == 'body' || w.getName() == 'html')) {
                    j += w.$.offsetLeft - w.$.scrollLeft;
                    k += w.$.offsetTop - w.$.scrollTop;
                    if (!w.equals(D)) {
                        j += w.$.clientLeft || 0;
                        k += w.$.clientTop || 0;
                    }
                    var z = x;
                    while (z && !z.equals(w)) {
                        j -= z.$.scrollLeft;
                        k -= z.$.scrollTop;
                        z = z.getParent();
                    }
                    x = w;
                    w = (y = w.$.offsetParent) ? new h(y) : null;
                }
            }
            if (i) {
                var A = D.getWindow(),
                    B = i.getWindow();
                if (!A.equals(B) && A.$.frameElement) {
                    var C = new h(A.$.frameElement).getDocumentPosition(i);
                    j += C.x;
                    k += C.y;
                }
            }
            if (!document.documentElement.getBoundingClientRect) if (b.gecko && !m) {
                j += D.$.clientLeft ? 1 : 0;
                k += D.$.clientTop ? 1 : 0;
            }
            return {
                x: j,
                y: k
            };
        },
        scrollIntoView: function (i) {
            var o = this;
            var j = o.getWindow(),
                k = j.getViewPaneSize().height,
                l = k * -1;
            if (i) l += k;
            else {
                l += o.$.offsetHeight || 0;
                l += parseInt(o.getComputedStyle('marginBottom') || 0, 10) || 0;
            }
            var m = o.getDocumentPosition();
            l += m.y;
            l = l < 0 ? 0 : l;
            var n = j.getScrollPosition().y;
            if (l > n || l < n - k) j.$.scrollTo(0, l);
        },
        setState: function (i) {
            var j = this;
            switch (i) {
            case 1:
                j.addClass('cke_on');
                j.removeClass('cke_off');
                j.removeClass('cke_disabled');
                break;
            case 0:
                j.addClass('cke_disabled');
                j.removeClass('cke_off');
                j.removeClass('cke_on');
                break;
            default:
                j.addClass('cke_off');
                j.removeClass('cke_on');
                j.removeClass('cke_disabled');
                break;
            }
        },
        getFrameDocument: function () {
            var i = this.$;
            try {
                i.contentWindow.document;
            } catch(j) {
                i.src = i.src;
                if (c && b.version < 7) window.showModalDialog('javascript:document.write("<script>window.setTimeout(function(){window.close();},50);</script>")');
            }
            return i && new g(i.contentWindow.document);
        },
        copyAttributes: function (i, j) {
            var p = this;
            var k = p.$.attributes;
            j = j || {};
            for (var l = 0; l < k.length; l++) {
                var m = k[l];
                if (m.specified || c && m.nodeValue && m.nodeName.toLowerCase() == 'value') {
                    var n = m.nodeName;
                    if (n in j) continue;
                    var o = p.getAttribute(n);
                    if (o === null) o = m.nodeValue;
                    i.setAttribute(n, o);
                }
            }
            if (p.$.style.cssText !== '') i.$.style.cssText = p.$.style.cssText;
        },
        renameNode: function (i) {
            var l = this;
            if (l.getName() == i) return;
            var j = l.getDocument(),
                k = new h(i, j);
            l.copyAttributes(k);
            l.moveChildren(k);
            l.$.parentNode.replaceChild(k.$, l.$);
            k.$._cke_expando = l.$._cke_expando;
            l.$ = k.$;
        },
        getChild: function (i) {
            var j = this.$;
            if (!i.slice) j = j.childNodes[i];
            else while (i.length > 0 && j) j = j.childNodes[i.shift()];
            return j ? new d.node(j) : null;
        },
        getChildCount: function () {
            return this.$.childNodes.length;
        }
    });
    a.command = function (i, j) {
        this.exec = function (k) {
            if (this.state == 0) return false;
            i.focus();
            return j.exec.call(this, i, k) !== false;
        };
        e.extend(this, j, {
            modes: {
                wysiwyg: 1
            },
            state: 2
        });
        a.event.call(this);
    };
    a.command.prototype = {
        enable: function () {
            var i = this;
            if (i.state == 0) i.setState(!i.preserveState || typeof i.previousState == 'undefined' ? 2 : i.previousState);
        },
        disable: function () {
            this.setState(0);
        },
        setState: function (i) {
            var j = this;
            if (j.state == i) return false;
            j.previousState = j.state;
            j.state = i;
            j.fire('state');
            return true;
        },
        toggleState: function () {
            var i = this;
            if (i.state == 2) i.setState(1);
            else if (i.state == 1) i.setState(2);
        }
    };
    a.event.implementOn(a.command.prototype, true);
    a.ENTER_P = 1;
    a.ENTER_BR = 2;
    a.ENTER_DIV = 3;
    a.config = {
        customConfig: a.getUrl('config.js'),
        autoUpdateElement: true,
        baseHref: '',
        contentsCss: a.basePath + 'contents.css',
        contentsLangDirection: 'ltr',
        language: '',
        defaultLanguage: 'en',
        enterMode: 1,
        shiftEnterMode: 2,
        corePlugins: '',
        docType: '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',
        fullPage: false,
        height: 200,
        plugins: 'encuestas,about,basicstyles,blockquote,button,clipboard,colorbutton,contextmenu,elementspath,enterkey,entities,filebrowser,find,flash,font,format,forms,horizontalrule,htmldataprocessor,image,indent,justify,keystrokes,link,list,maximize,newpage,pagebreak,pastefromword,pastetext,popup,preview,customPreview,print,removeformat,resize,save,scayt,smiley,showblocks,sourcearea,stylescombo,table,tabletools,specialchar,tab,templates,toolbar,undo,wysiwygarea,wsc',
        extraPlugins: '',
        removePlugins: '',
        protectedSource: [],
        tabIndex: 0,
        theme: 'default',
        skin: 'kama',
        width: '',
        baseFloatZIndex: 10000
    };
    var i = a.config;
    a.focusManager = function (j) {
        if (j.focusManager) return j.focusManager;
        this.hasFocus = false;
        this._ = {
            editor: j
        };
        return this;
    };
    a.focusManager.prototype = {
        focus: function () {
            var k = this;
            if (k._.timer) clearTimeout(k._.timer);
            if (!k.hasFocus) {
                if (a.currentInstance) a.currentInstance.focusManager.forceBlur();
                var j = k._.editor;
                j.container.getFirst().addClass('cke_focus');
                k.hasFocus = true;
                j.fire('focus');
            }
        },
        blur: function () {
            var j = this;
            if (j._.timer) clearTimeout(j._.timer);
            j._.timer = setTimeout(function () {
                delete j._.timer;
                j.forceBlur();
            },
            100);
        },
        forceBlur: function () {
            if (this.hasFocus) {
                var j = this._.editor;
                j.container.getFirst().removeClass('cke_focus');
                this.hasFocus = false;
                j.fire('blur');
            }
        }
    };
    (function () {
        var j = {};
        a.lang = {
            languages: {
                af: 1,
                ar: 1,
                bg: 1,
                bn: 1,
                bs: 1,
                ca: 1,
                cs: 1,
                da: 1,
                de: 1,
                el: 1,
                'en-au': 1,
                'en-ca': 1,
                'en-uk': 1,
                en: 1,
                eo: 1,
                es: 1,
                et: 1,
                eu: 1,
                fa: 1,
                fi: 1,
                fo: 1,
                'fr-ca': 1,
                fr: 1,
                gl: 1,
                gu: 1,
                he: 1,
                hi: 1,
                hr: 1,
                hu: 1,
                is: 1,
                it: 1,
                ja: 1,
                km: 1,
                ko: 1,
                lt: 1,
                lv: 1,
                mn: 1,
                ms: 1,
                nb: 1,
                nl: 1,
                no: 1,
                pl: 1,
                'pt-br': 1,
                pt: 1,
                ro: 1,
                ru: 1,
                sk: 1,
                sl: 1,
                'sr-latn': 1,
                sr: 1,
                sv: 1,
                th: 1,
                tr: 1,
                uk: 1,
                vi: 1,
                'zh-cn': 1,
                zh: 1
            },
            load: function (k, l, m) {
                if (!k) k = this.detect(l);
                if (!this[k]) a.scriptLoader.load(a.getUrl('lang/' + k + '.js'), function () {
                    m(k, this[k]);
                },
                this);
                else m(k, this[k]);
            },
            detect: function (k) {
                var l = this.languages,
                    m = (navigator.userLanguage || navigator.language).toLowerCase().match(/([a-z]+)(?:-([a-z]+))?/),
                    n = m[1],
                    o = m[2];
                if (l[n + '-' + o]) n = n + '-' + o;
                else if (!l[n]) n = null;
                a.lang.detect = n ?
                function () {
                    return n;
                } : function (p) {
                    return p;
                };
                return n || k;
            }
        };
    })();
    a.scriptLoader = (function () {
        var j = {},
            k = {};
        return {
            load: function (l, m, n, o) {
                var p = typeof l == 'string';
                if (p) l = [l];
                if (!n) n = a;
                var q = l.length,
                    r = [],
                    s = [],
                    t = function (y) {
                    if (m) if (p) m.call(n, y);
                    else m.call(n, r, s);
                };
                if (q === 0) {
                    t(true);
                    return;
                }
                var u = function (y, z) {
                    (z ? r : s).push(y);
                    if (--q <= 0) t(z);
                },
                    v = function (y, z) {
                    j[y] = 1;
                    var A = k[y];
                    delete k[y];
                    for (var B = 0; B < A.length; B++) A[B](y, z);
                },
                    w = function (y) {
                    if (o !== true && j[y]) {
                        u(y, true);
                        return;
                    }
                    var z = k[y] || (k[y] = []);
                    z.push(u);
                    if (z.length > 1) return;
                    var A = new h('script');
                    A.setAttributes({
                        type: 'text/javascript',
                        src: y
                    });
                    if (m) if (c) A.$.onreadystatechange = function () {
                        if (A.$.readyState == 'loaded' || A.$.readyState == 'complete') {
                            A.$.onreadystatechange = null;
                            v(y, true);
                        }
                    };
                    else {
                        A.$.onload = function () {
                            setTimeout(function () {
                                v(y, true);
                            },
                            0);
                        };
                        A.$.onerror = function () {
                            v(y, false);
                        };
                    }
                    A.appendTo(a.document.getHead());
                };
                for (var x = 0; x < q; x++) w(l[x]);
            },
            loadCode: function (l) {
                var m = new h('script');
                m.setAttribute('type', 'text/javascript');
                m.appendText(l);
                m.appendTo(a.document.getHead());
            }
        };
    })();
    a.resourceManager = function (j, k) {
        var l = this;
        l.basePath = j;
        l.fileName = k;
        l.registered = {};
        l.loaded = {};
        l.externals = {};
        l._ = {
            waitingList: {}
        };
    };
    a.resourceManager.prototype = {
        add: function (j, k) {
            if (this.registered[j]) throw '[CKEDITOR.resourceManager.add] The resource name "' + j + '" is already registered.';
            this.registered[j] = k || {};
        },
        get: function (j) {
            return this.registered[j] || null;
        },
        getPath: function (j) {
            var k = this.externals[j];
            return a.getUrl(k && k.dir || this.basePath + j + '/');
        },
        getFilePath: function (j) {
            var k = this.externals[j];
            return a.getUrl(this.getPath(j) + (k && k.file || this.fileName + '.js'));
        },
        addExternal: function (j, k, l) {
            j = j.split(',');
            for (var m = 0; m < j.length; m++) {
                var n = j[m];
                this.externals[n] = {
                    dir: k,
                    file: l
                };
            }
        },
        load: function (j, k, l) {
            if (!e.isArray(j)) j = j ? [j] : [];
            var m = this.loaded,
                n = this.registered,
                o = [],
                p = {},
                q = {};
            for (var r = 0; r < j.length; r++) {
                var s = j[r];
                if (!s) continue;
                if (!m[s] && !n[s]) {
                    var t = this.getFilePath(s);
                    o.push(t);
                    if (! (t in p)) p[t] = [];
                    p[t].push(s);
                } else q[s] = this.get(s);
            }
            a.scriptLoader.load(o, function (u, v) {
                if (v.length) throw '[CKEDITOR.resourceManager.load] Resource name "' + p[v[0]].join(',') + '" was not found at "' + v[0] + '".';
                for (var w = 0; w < u.length; w++) {
                    var x = p[u[w]];
                    for (var y = 0; y < x.length; y++) {
                        var z = x[y];
                        q[z] = this.get(z);
                        m[z] = 1;
                    }
                }
                k.call(l, q);
            },
            this);
        }
    };
    a.plugins = new a.resourceManager('plugins/', 'plugin');
    var j = a.plugins;
    j.load = e.override(j.load, function (k) {
        return function (l, m, n) {
            var o = {},
                p = function (q) {
                k.call(this, q, function (r) {
                    e.extend(o, r);
                    var s = [];
                    for (var t in r) {
                        var u = r[t],
                            v = u && u.requires;
                        if (v) for (var w = 0; w < v.length; w++) if (!o[v[w]]) s.push(v[w]);
                    }
                    if (s.length) p.call(this, s);
                    else {
                        for (t in o) {
                            u = o[t];
                            if (u.onLoad && !u.onLoad._called) {
                                u.onLoad();
                                u.onLoad._called = 1;
                            }
                        }
                        if (m) m.call(n || window, o);
                    }
                },
                this);
            };
            p.call(this, l);
        };
    });
    j.setLang = function (k, l, m) {
        var n = this.get(k);
        n.lang[l] = m;
    };
    (function () {
        var k = {},
            l = function (m, n) {
            var o = function () {
                k[m] = 1;
                n();
            },
                p = new h('img');
            p.on('load', o);
            p.on('error', o);
            p.setAttribute('src', m);
        };
        a.imageCacher = {
            load: function (m, n) {
                var o = m.length,
                    p = function () {
                    if (--o === 0) n();
                };
                for (var q = 0; q < m.length; q++) {
                    var r = m[q];
                    if (k[r]) p();
                    else l(r, p);
                }
            }
        };
    })();
    a.skins = (function () {
        var k = {},
            l = {},
            m = {},
            n = function (o, p, q) {
            var r = k[o],
                s = function (A) {
                for (var B = 0; B < A.length; B++) A[B] = a.getUrl(m[o] + A[B]);
            };
            if (!l[o]) {
                var t = r.preload;
                if (t && t.length > 0) {
                    s(t);
                    a.imageCacher.load(t, function () {
                        l[o] = 1;
                        n(o, p, q);
                    });
                    return;
                }
                l[o] = 1;
            }
            p = r[p];
            var u = !p || !!p._isLoaded;
            if (u) q && q();
            else {
                var v = p._pending || (p._pending = []);
                v.push(q);
                if (v.length > 1) return;
                var w = !p.css || !p.css.length,
                    x = !p.js || !p.js.length,
                    y = function () {
                    if (w && x) {
                        p._isLoaded = 1;
                        for (var A = 0; A < v.length; A++) if (v[A]) v[A]();
                    }
                };
                if (!w) {
                    s(p.css);
                    for (var z = 0; z < p.css.length; z++) a.document.appendStyleSheet(p.css[z]);
                    w = 1;
                }
                if (!x) {
                    s(p.js);
                    a.scriptLoader.load(p.js, function () {
                        x = 1;
                        y();
                    });
                }
                y();
            }
        };
        return {
            add: function (o, p) {
                k[o] = p;
                p.skinPath = m[o] || (m[o] = a.getUrl('skins/' + o + '/'));
            },
            load: function (o, p, q) {
                var r = o.skinName,
                    s = o.skinPath;
                if (k[r]) {
                    n(r, p, q);
                    var t = k[r];
                    if (t.init) t.init(o);
                } else {
                    m[r] = s;
                    a.scriptLoader.load(s + 'skin.js', function () {
                        n(r, p, q);
                        var u = k[r];
                        if (u.init) u.init(o);
                    });
                }
            }
        };
    })();
    a.themes = new a.resourceManager('themes/', 'theme');
    a.ui = function (k) {
        if (k.ui) return k.ui;
        this._ = {
            handlers: {},
            items: {}
        };
        return this;
    };
    var k = a.ui;
    k.prototype = {
        add: function (l, m, n) {
            this._.items[l] = {
                type: m,
                args: Array.prototype.slice.call(arguments, 2)
            };
        },
        create: function (l) {
            var m = this._.items[l],
                n = m && this._.handlers[m.type];
            return n && n.create.apply(this, m.args);
        },
        addHandler: function (l, m) {
            this._.handlers[l] = m;
        }
    };
    (function () {
        var l = 0,
            m = function () {
            var x = 'editor' + ++l;
            return a.instances && a.instances[x] ? m() : x;
        },
            n = {},
            o = function (x) {
            var y = x.config.customConfig;
            if (!y) return false;
            var z = n[y] || (n[y] = {});
            if (z.fn) {
                z.fn.call(x, x.config);
                if (x.config.customConfig == y || !o(x)) x.fireOnce('customConfigLoaded');
            } else a.scriptLoader.load(y, function () {
                if (a.editorConfig) z.fn = a.editorConfig;
                else z.fn = function () {};
                o(x);
            });
            return true;
        },
            p = function (x, y) {
            x.on('customConfigLoaded', function () {
                if (y) {
                    if (y.on) for (var z in y.on) x.on(z, y.on[z]);
                    e.extend(x.config, y, true);
                    delete x.config.on;
                }
                q(x);
            });
            if (y && y.customConfig != undefined) x.config.customConfig = y.customConfig;
            if (!o(x)) x.fireOnce('customConfigLoaded');
        },
            q = function (x) {
            var y = x.config.skin.split(','),
                z = y[0],
                A = a.getUrl(y[1] || 'skins/' + z + '/');
            x.skinName = z;
            x.skinPath = A;
            x.skinClass = 'cke_skin_' + z;
            x.fireOnce('configLoaded');
            r(x);
        },
            r = function (x) {
            a.lang.load(x.config.language, x.config.defaultLanguage, function (y, z) {
                x.langCode = y;
                x.lang = e.prototypedCopy(z);
                if (b.gecko && b.version < 10900 && x.lang.dir == 'rtl') x.lang.dir = 'ltr';
                s(x);
            });
        },
            s = function (x) {
            var y = x.config,
                z = y.plugins,
                A = y.extraPlugins,
                B = y.removePlugins;
            if (A) {
                var C = new RegExp('(?:^|,)(?:' + A.replace(/\s*,\s*/g, '|') + ')(?=,|$)', 'g');
                z = z.replace(C, '');
                z += ',' + A;
            }
            if (B) {
                C = new RegExp('(?:^|,)(?:' + B.replace(/\s*,\s*/g, '|') + ')(?=,|$)', 'g');
                z = z.replace(C, '');
            }
            j.load(z.split(','), function (D) {
                var E = [],
                    F = [],
                    G = [];
                x.plugins = D;
                for (var H in D) {
                    var I = D[H],
                        J = I.lang,
                        K = j.getPath(H),
                        L = null;
                    I.path = K;
                    if (J) {
                        L = e.indexOf(J, x.langCode) >= 0 ? x.langCode : J[0];
                        if (!I.lang[L]) G.push(a.getUrl(K + 'lang/' + L + '.js'));
                        else {
                            e.extend(x.lang, I.lang[L]);
                            L = null;
                        }
                    }
                    F.push(L);
                    E.push(I);
                }
                a.scriptLoader.load(G, function () {
                    var M = ['beforeInit', 'init', 'afterInit'];
                    for (var N = 0; N < M.length; N++) for (var O = 0; O < E.length; O++) {
                        var P = E[O];
                        if (N === 0 && F[O] && P.lang) e.extend(x.lang, P.lang[F[O]]);
                        if (P[M[N]]) P[M[N]](x);
                    }
                    x.fire('pluginsLoaded');
                    t(x);
                });
            });
        },
            t = function (x) {
            a.skins.load(x, 'editor', function () {
                u(x);
            });
        },
            u = function (x) {
            var y = x.config.theme;
            a.themes.load(y, function () {
                var z = x.theme = a.themes.get(y);
                z.path = a.themes.getPath(y);
                z.build(x);
                if (x.config.autoUpdateElement) v(x);
            });
        },
            v = function (x) {
            var y = x.element;
            if (x.elementMode == 1 && y.is('textarea')) {
                var z = y.$.form && new h(y.$.form);
                if (z) {
                    function A() {
                        x.updateElement();
                    };
                    z.on('submit', A);
                    if (!z.$.submit.nodeName) z.$.submit = e.override(z.$.submit, function (B) {
                        return function () {
                            x.updateElement();
                            if (B.apply) B.apply(this, arguments);
                            else B();
                        };
                    });
                    x.on('destroy', function () {
                        z.removeListener('submit', A);
                    });
                }
            }
        };

        function w() {
            var x, y = this._.commands,
                z = this.mode;
            for (var A in y) {
                x = y[A];
                x[x.modes[z] ? 'enable' : 'disable']();
            }
        };
        a.editor.prototype._init = function () {
            var z = this;
            var x = h.get(z._.element),
                y = z._.instanceConfig;
            delete z._.element;
            delete z._.instanceConfig;
            z._.commands = {};
            z._.styles = [];
            z.element = x;
            z.name = x && z.elementMode == 1 && (x.getId() || x.getNameAtt()) || m();
            if (z.name in a.instances) throw '[CKEDITOR.editor] The instance "' + z.name + '" already exists.';
            z.config = e.prototypedCopy(i);
            z.ui = new k(z);
            z.focusManager = new a.focusManager(z);
            a.fire('instanceCreated', null, z);
            z.on('mode', w, null, null, 1);
            p(z, y);
        };
    })();
    e.extend(a.editor.prototype, {
        addCommand: function (l, m) {
            return this._.commands[l] = new a.command(this, m);
        },
        addCss: function (l) {
            this._.styles.push(l);
        },
        destroy: function (l) {
            var m = this;
            if (!l) m.updateElement();
            m.theme.destroy(m);
            m.fire('destroy');
            a.remove(m);
        },
        execCommand: function (l, m) {
            var n = this.getCommand(l),
                o = {
                name: l,
                commandData: m,
                command: n
            };
            if (n && n.state != 0) if (this.fire('beforeCommandExec', o) !== true) {
                o.returnValue = n.exec(o.commandData);
                if (!n.async && this.fire('afterCommandExec', o) !== true) return o.returnValue;
            }
            return false;
        },
        getCommand: function (l) {
            return this._.commands[l];
        },
        getData: function () {
            var n = this;
            n.fire('beforeGetData');
            var l = n._.data;
            if (typeof l != 'string') {
                var m = n.element;
                if (m && n.elementMode == 1) l = m.is('textarea') ? m.getValue() : m.getHtml();
                else l = '';
            }
            l = {
                dataValue: l
            };
            n.fire('getData', l);
            return l.dataValue;
        },
        getSnapshot: function () {
            var l = this.fire('getSnapshot');
            if (typeof l != 'string') {
                var m = this.element;
                if (m && this.elementMode == 1) l = m.is('textarea') ? m.getValue() : m.getHtml();
            }
            return l;
        },
        loadSnapshot: function (l) {
            this.fire('loadSnapshot', l);
        },
        setData: function (l) {
            var m = {
                dataValue: l
            };
            this.fire('setData', m);
            this._.data = m.dataValue;
            this.fire('afterSetData', m);
        },
        insertHtml: function (l) {
            this.fire('insertHtml', l);
        },
        insertElement: function (l) {
            this.fire('insertElement', l);
        },
        checkDirty: function () {
            return this.mayBeDirty && this._.previousValue !== this.getSnapshot();
        },
        resetDirty: function () {
            if (this.mayBeDirty) this._.previousValue = this.getSnapshot();
        },
        updateElement: function () {
            var m = this;
            var l = m.element;
            if (l && m.elementMode == 1) if (l.is('textarea')) l.setValue(m.getData());
            else l.setHtml(m.getData());
        }
    });
    a.on('loaded', function () {
        var l = a.editor._pending;
        if (l) {
            delete a.editor._pending;
            for (var m = 0; m < l.length; m++) l[m]._init();
        }
    });
    a.htmlParser = function () {
        this._ = {
            htmlPartsRegex: new RegExp("<(?:(?:\\/([^>]+)>)|(?:!--([\\S|\\s]*?)-->)|(?:([^\\s>]+)\\s*((?:(?:[^\"'>]+)|(?:\"[^\"]*\")|(?:'[^']*'))*)\\/?>))", 'g')
        };
    };
    (function () {
        var l = /([\w:]+)(?:(?:\s*=\s*(?:(?:"([^"]*)")|(?:'([^']*)')|([^\s>]+)))|(?=\s|$))/g,
            m = {
            checked: 1,
            compact: 1,
            declare: 1,
            defer: 1,
            disabled: 1,
            ismap: 1,
            multiple: 1,
            nohref: 1,
            noresize: 1,
            noshade: 1,
            nowrap: 1,
            readonly: 1,
            selected: 1
        };
        a.htmlParser.prototype = {
            onTagOpen: function () {},
            onTagClose: function () {},
            onText: function () {},
            onCDATA: function () {},
            onComment: function () {},
            parse: function (n) {
                var A = this;
                var o, p, q = 0,
                    r;
                while (o = A._.htmlPartsRegex.exec(n)) {
                    var s = o.index;
                    if (s > q) {
                        var t = n.substring(q, s);
                        if (r) r.push(t);
                        else A.onText(t);
                    }
                    q = A._.htmlPartsRegex.lastIndex;
                    if (p = o[1]) {
                        p = p.toLowerCase();
                        if (r && f.$cdata[p]) {
                            A.onCDATA(r.join(''));
                            r = null;
                        }
                        if (!r) {
                            A.onTagClose(p);
                            continue;
                        }
                    }
                    if (r) {
                        r.push(o[0]);
                        continue;
                    }
                    if (p = o[3]) {
                        p = p.toLowerCase();
                        var u = {},
                            v, w = o[4],
                            x = !!(w && w.charAt(w.length - 1) == '/');
                        if (w) while (v = l.exec(w)) {
                            var y = v[1].toLowerCase(),
                                z = v[2] || v[3] || v[4] || '';
                            if (!z && m[y]) u[y] = y;
                            else u[y] = z;
                        }
                        A.onTagOpen(p, u, x);
                        if (!r && f.$cdata[p]) r = [];
                        continue;
                    }
                    if (p = o[2]) A.onComment(p);
                }
                if (n.length > q) A.onText(n.substring(q, n.length));
            }
        };
    })();
    a.htmlParser.comment = function (l) {
        this.value = l;
        this._ = {
            isBlockLike: false
        };
    };
    a.htmlParser.comment.prototype = {
        type: 8,
        writeHtml: function (l, m) {
            var n = this.value;
            if (m) {
                if (! (n = m.onComment(n))) return;
                if (typeof n != 'string') {
                    n.writeHtml(l, m);
                    return;
                }
            }
            l.comment(n);
        }
    };
    (function () {
        var l = /[\t\r\n ]{2,}|[\t\r\n]/g;
        a.htmlParser.text = function (m) {
            this.value = m;
            this._ = {
                isBlockLike: false
            };
        };
        a.htmlParser.text.prototype = {
            type: 3,
            writeHtml: function (m, n) {
                var o = this.value;
                if (n && !(o = n.onText(o, this))) return;
                m.text(o);
            }
        };
    })();
    (function () {
        a.htmlParser.cdata = function (l) {
            this.value = l;
        };
        a.htmlParser.cdata.prototype = {
            type: 3,
            writeHtml: function (l) {
                l.write(this.value);
            }
        };
    })();
    a.htmlParser.fragment = function () {
        this.children = [];
        this.parent = null;
        this._ = {
            isBlockLike: true,
            hasInlineStarted: false
        };
    };
    (function () {
        var l = {
            colgroup: 1,
            dd: 1,
            dt: 1,
            li: 1,
            option: 1,
            p: 1,
            td: 1,
            tfoot: 1,
            th: 1,
            thead: 1,
            tr: 1
        },
            m = e.extend({
            table: 1,
            ul: 1,
            ol: 1,
            dl: 1
        },
        f.table, f.ul, f.ol, f.dl);
        a.htmlParser.fragment.fromHtml = function (n, o) {
            var p = new a.htmlParser(),
                q = [],
                r = new a.htmlParser.fragment(),
                s = [],
                t = r,
                u = false,
                v;

            function w(A) {
                if (s.length > 0) for (var B = 0; B < s.length; B++) {
                    var C = s[B],
                        D = C.name,
                        E = f[D],
                        F = t.name && f[t.name];
                    if ((!F || F[D]) && (!A || !E || E[A] || !f[A])) {
                        C = C.clone();
                        C.parent = t;
                        t = C;
                        s.splice(B, 1);
                        B--;
                    }
                }
            };

            function x(A, B, C) {
                B = B || t || r;
                if (o && !B.type) {
                    var D, E;
                    if (A.attributes && (E = A.attributes._cke_real_element_type)) D = E;
                    else D = A.name;
                    if (! (D in f.$body)) {
                        var F = t;
                        t = B;
                        p.onTagOpen(o, {});
                        B = t;
                        if (C) t = F;
                    }
                }
                if (A._.isBlockLike && A.name != 'pre') {
                    var G = A.children.length,
                        H = A.children[G - 1],
                        I;
                    if (H && H.type == 3) if (! (I = e.rtrim(H.value))) A.children.length = G - 1;
                    else H.value = I;
                }
                B.add(A);
                if (A.returnPoint) {
                    t = A.returnPoint;
                    delete A.returnPoint;
                }
            };
            p.onTagOpen = function (A, B, C) {
                var D = new a.htmlParser.element(A, B);
                if (D.isUnknown && C) D.isEmpty = true;
                if (f.$removeEmpty[A]) {
                    s.push(D);
                    return;
                } else if (A == 'pre') u = true;
                else if (A == 'br' && u) {
                    t.add(new a.htmlParser.text('\n'));
                    return;
                }
                var E = t.name,
                    F = E && f[E] || (t._.isBlockLike ? f.div : f.span);
                if (!D.isUnknown && !t.isUnknown && !F[A]) {
                    if (!E) return;
                    var G = false;
                    if (A == E) x(t, t.parent);
                    else {
                        if (m[E]) {
                            if (!v) v = t;
                        } else {
                            x(t, t.parent, true);
                            if (!l[E]) s.unshift(t);
                        }
                        G = true;
                    }
                    t = t.returnPoint || t.parent;
                    if (G) {
                        p.onTagOpen.apply(this, arguments);
                        return;
                    }
                }
                w(A);
                D.parent = t;
                D.returnPoint = v;
                v = 0;
                if (D.isEmpty) x(D);
                else t = D;
            };
            p.onTagClose = function (A) {
                var B = 0,
                    C = [],
                    D = t;
                while (D.type && D.name != A) {
                    if (!D._.isBlockLike) {
                        s.unshift(D);
                        B++;
                    }
                    C.push(D);
                    D = D.parent;
                }
                if (D.type) {
                    for (var E = 0; E < C.length; E++) {
                        var F = C[E];
                        x(F, F.parent);
                    }
                    t = D;
                    if (t.name == 'pre') u = false;
                    x(D, D.parent);
                    if (D == t) t = t.parent;
                } else {
                    s.splice(0, B);
                    B = 0;
                }
                for (; B < s.length; B++) if (A == s[B].name) {
                    s.splice(B, 1);
                    B--;
                }
            };
            p.onText = function (A) {
                if (!t._.hasInlineStarted && !u) {
                    A = e.ltrim(A);
                    if (A.length === 0) return;
                }
                w();
                if (o && !t.type) this.onTagOpen(o, {});
                if (!u) A = A.replace(/[\t\r\n ]{2,}|[\t\r\n]/g, ' ');
                t.add(new a.htmlParser.text(A));
            };
            p.onCDATA = function (A) {
                t.add(new a.htmlParser.cdata(A));
            };
            p.onComment = function (A) {
                t.add(new a.htmlParser.comment(A));
            };
            p.parse(n);
            while (t.type) {
                var y = t.parent,
                    z = t;
                if (o && !y.type && !f.$body[z.name]) {
                    t = y;
                    p.onTagOpen(o, {});
                    y = t;
                }
                y.add(z);
                t = y;
            }
            return r;
        };
        a.htmlParser.fragment.prototype = {
            add: function (n) {
                var q = this;
                var o = q.children.length,
                    p = o > 0 && q.children[o - 1] || null;
                if (p) {
                    if (n._.isBlockLike && p.type == 3) {
                        p.value = e.rtrim(p.value);
                        if (p.value.length === 0) {
                            q.children.pop();
                            q.add(n);
                            return;
                        }
                    }
                    p.next = n;
                }
                n.previous = p;
                n.parent = q;
                q.children.push(n);
                q._.hasInlineStarted = n.type == 3 || n.type == 1 && !n._.isBlockLike;
            },
            writeHtml: function (n, o) {
                for (var p = 0, q = this.children.length; p < q; p++) this.children[p].writeHtml(n, o);
            }
        };
    })();
    a.htmlParser.element = function (l, m) {
        var q = this;
        q.name = l;
        q.attributes = m;
        q.children = [];
        var n = f,
            o = !!(n.$block[l] || n.$listItem[l] || n.$tableContent[l]),
            p = !!n.$empty[l];
        q.isEmpty = p;
        q.isUnknown = !n[l];
        q._ = {
            isBlockLike: o,
            hasInlineStarted: p || !o
        };
    };
    (function () {
        var l = function (m, n) {
            m = m[0];
            n = n[0];
            return m < n ? -1 : m > n ? 1 : 0;
        };
        a.htmlParser.element.prototype = {
            type: 1,
            add: a.htmlParser.fragment.prototype.add,
            clone: function () {
                return new a.htmlParser.element(this.name, this.attributes);
            },
            writeHtml: function (m, n) {
                var o = this.attributes;
                if (o._cke_replacedata) {
                    m.write(o._cke_replacedata);
                    return;
                }
                var p = this,
                    q = p.name,
                    r, s;
                if (n) {
                    for (;;) {
                        if (! (q = n.onElementName(q))) return;
                        p.name = q;
                        if (! (p = n.onElement(p))) return;
                        if (p.name == q) break;
                        q = p.name;
                        if (!q) {
                            a.htmlParser.fragment.prototype.writeHtml.apply(p, arguments);
                            return;
                        }
                    }
                    o = p.attributes;
                }
                m.openTag(q, o);
                if (m.sortAttributes) {
                    var t = [];
                    for (r in o) {
                        s = o[r];
                        if (n && (!(r = n.onAttributeName(r)) || (s = n.onAttribute(p, r, s)) === (false))) continue;
                        t.push([r, s]);
                    }
                    t.sort(l);
                    for (var u = 0, v = t.length; u < v; u++) {
                        var w = t[u];
                        m.attribute(w[0], w[1]);
                    }
                } else for (r in o) {
                    s = o[r];
                    if (n && (!(r = n.onAttributeName(r)) || (s = n.onAttribute(p, r, s)) === (false))) continue;
                    m.attribute(r, s);
                }
                m.openTagClose(q, p.isEmpty);
                if (!p.isEmpty) {
                    a.htmlParser.fragment.prototype.writeHtml.apply(p, arguments);
                    m.closeTag(q);
                }
            }
        };
    })();
    (function () {
        a.htmlParser.filter = e.createClass({
            $: function (q) {
                this._ = {
                    elementNames: [],
                    attributeNames: [],
                    elements: {
                        $length: 0
                    },
                    attributes: {
                        $length: 0
                    }
                };
                if (q) this.addRules(q, 10);
            },
            proto: {
                addRules: function (q, r) {
                    var s = this;
                    if (typeof r != 'number') r = 10;
                    m(s._.elementNames, q.elementNames, r);
                    m(s._.attributeNames, q.attributeNames, r);
                    n(s._.elements, q.elements, r);
                    n(s._.attributes, q.attributes, r);
                    s._.text = o(s._.text, q.text, r) || s._.text;
                    s._.comment = o(s._.comment, q.comment, r) || s._.comment;
                },
                onElementName: function (q) {
                    return l(q, this._.elementNames);
                },
                onAttributeName: function (q) {
                    return l(q, this._.attributeNames);
                },
                onText: function (q) {
                    var r = this._.text;
                    return r ? r.filter(q) : q;
                },
                onComment: function (q) {
                    var r = this._.comment;
                    return r ? r.filter(q) : q;
                },
                onElement: function (q) {
                    var v = this;
                    var r = [v._.elements[q.name], v._.elements.$],
                        s, t;
                    for (var u = 0; u < 2; u++) {
                        s = r[u];
                        if (s) {
                            t = s.filter(q, v);
                            if (t === false) return null;
                            if (t && t != q) return v.onElement(t);
                        }
                    }
                    return q;
                },
                onAttribute: function (q, r, s) {
                    var t = this._.attributes[r];
                    if (t) {
                        var u = t.filter(s, q, this);
                        if (u === false) return false;
                        if (typeof u != 'undefined') return u;
                    }
                    return s;
                }
            }
        });

        function l(q, r) {
            for (var s = 0; q && s < r.length; s++) {
                var t = r[s];
                q = q.replace(t[0], t[1]);
            }
            return q;
        };

        function m(q, r, s) {
            var t, u, v = q.length,
                w = r && r.length;
            if (w) {
                for (t = 0; t < v && q[t].pri < s; t++) {}
                for (u = w - 1; u >= 0; u--) {
                    var x = r[u];
                    x.pri = s;
                    q.splice(t, 0, x);
                }
            }
        };

        function n(q, r, s) {
            if (r) for (var t in r) {
                var u = q[t];
                q[t] = o(u, r[t], s);
                if (!u) q.$length++;
            }
        };

        function o(q, r, s) {
            if (r) {
                r.pri = s;
                if (q) {
                    if (!q.splice) {
                        if (q.pri > s) q = [r, q];
                        else q = [q, r];
                        q.filter = p;
                    } else m(q, r, s);
                    return q;
                } else {
                    r.filter = r;
                    return r;
                }
            }
        };

        function p(q) {
            var r = typeof q == 'object';
            for (var s = 0; s < this.length; s++) {
                var t = this[s],
                    u = t.apply(window, arguments);
                if (typeof u != 'undefined') {
                    if (u === false) return false;
                    if (r && u != q) return u;
                }
            }
            return null;
        };
    })();
    a.htmlParser.basicWriter = e.createClass({
        $: function () {
            this._ = {
                output: []
            };
        },
        proto: {
            openTag: function (l, m) {
                this._.output.push('<', l);
            },
            openTagClose: function (l, m) {
                if (m) this._.output.push(' />');
                else this._.output.push('>');
            },
            attribute: function (l, m) {
                this._.output.push(' ', l, '="', m, '"');
            },
            closeTag: function (l) {
                this._.output.push('</', l, '>');
            },
            text: function (l) {
                this._.output.push(l);
            },
            comment: function (l) {
                this._.output.push('<!--', l, '-->');
            },
            write: function (l) {
                this._.output.push(l);
            },
            reset: function () {
                this._.output = [];
            },
            getHtml: function (l) {
                var m = this._.output.join('');
                if (l) this.reset();
                return m;
            }
        }
    });
    delete a.loadFullCore;
    a.instances = {};
    a.document = new g(document);
    a.add = function (l) {
        a.instances[l.name] = l;
        l.on('focus', function () {
            if (a.currentInstance != l) {
                a.currentInstance = l;
                a.fire('currentInstance');
            }
        });
        l.on('blur', function () {
            if (a.currentInstance == l) {
                a.currentInstance = null;
                a.fire('currentInstance');
            }
        });
    };
    a.remove = function (l) {
        delete a.instances[l.name];
    };
    a.TRISTATE_ON = 1;
    a.TRISTATE_OFF = 2;
    a.TRISTATE_DISABLED = 0;
    (function () {
        var l = {
            address: 1,
            blockquote: 1,
            dl: 1,
            h1: 1,
            h2: 1,
            h3: 1,
            h4: 1,
            h5: 1,
            h6: 1,
            p: 1,
            pre: 1,
            li: 1,
            dt: 1,
            de: 1
        },
            m = {
            body: 1,
            div: 1,
            table: 1,
            tbody: 1,
            tr: 1,
            td: 1,
            th: 1,
            caption: 1,
            form: 1
        },
            n = function (o) {
            var p = o.getChildren();
            for (var q = 0, r = p.count(); q < r; q++) {
                var s = p.getItem(q);
                if (s.type == 1 && f.$block[s.getName()]) return true;
            }
            return false;
        };
        d.elementPath = function (o) {
            var u = this;
            var p = null,
                q = null,
                r = [],
                s = o;
            while (s) {
                if (s.type == 1) {
                    if (!u.lastElement) u.lastElement = s;
                    var t = s.getName();
                    if (c && s.$.scopeName != 'HTML') t = s.$.scopeName.toLowerCase() + ':' + t;
                    if (!q) {
                        if (!p && l[t]) p = s;
                        if (m[t]) if (!p && t == 'div' && !n(s)) p = s;
                        else q = s;
                    }
                    r.push(s);
                    if (t == 'body') break;
                }
                s = s.getParent();
            }
            u.block = p;
            u.blockLimit = q;
            u.elements = r;
        };
    })();
    d.elementPath.prototype = {
        compare: function (l) {
            var m = this.elements,
                n = l && l.elements;
            if (!n || m.length != n.length) return false;
            for (var o = 0; o < m.length; o++) if (!m[o].equals(n[o])) return false;
            return true;
        }
    };
    d.text = function (l, m) {
        if (typeof l == 'string') l = (m ? m.$ : document).createTextNode(l);
        this.$ = l;
    };
    d.text.prototype = new d.node();
    e.extend(d.text.prototype, {
        type: 3,
        getLength: function () {
            return this.$.nodeValue.length;
        },
        getText: function () {
            return this.$.nodeValue;
        },
        split: function (l) {
            var q = this;
            if (c && l == q.getLength()) {
                var m = q.getDocument().createText('');
                m.insertAfter(q);
                return m;
            }
            var n = q.getDocument(),
                o = new d.text(q.$.splitText(l), n);
            if (b.ie8) {
                var p = new d.text('', n);
                p.insertAfter(o);
                p.remove();
            }
            return o;
        },
        substring: function (l, m) {
            if (typeof m != 'number') return this.$.nodeValue.substr(l);
            else return this.$.nodeValue.substring(l, m);
        }
    });
    d.documentFragment = function (l) {
        l = l || a.document;
        this.$ = l.$.createDocumentFragment();
    };
    e.extend(d.documentFragment.prototype, h.prototype, {
        type: 11,
        insertAfterNode: function (l) {
            l = l.$;
            l.parentNode.insertBefore(this.$, l.nextSibling);
        }
    },
    true, {
        append: 1,
        appendBogus: 1,
        getFirst: 1,
        getLast: 1,
        appendTo: 1,
        moveChildren: 1,
        insertBefore: 1,
        insertAfterNode: 1,
        replace: 1,
        trim: 1,
        type: 1,
        ltrim: 1,
        rtrim: 1,
        getDocument: 1,
        getChildCount: 1,
        getChild: 1,
        getChildren: 1
    });
    (function () {
        function l(p, q) {
            if (this._.end) return null;
            var r, s = this.range,
                t, u = this.guard,
                v = this.type,
                w = p ? 'getPreviousSourceNode' : 'getNextSourceNode';
            if (!this._.start) {
                this._.start = 1;
                s.trim();
                if (s.collapsed) {
                    this.end();
                    return null;
                }
            }
            if (!p && !this._.guardLTR) {
                var x = s.endContainer,
                    y = x.getChild(s.endOffset);
                this._.guardLTR = function (C, D) {
                    return (!D || !x.equals(C)) && ((!y || !C.equals(y)) && (C.type != 1 || C.getName() != 'body'));
                };
            }
            if (p && !this._.guardRTL) {
                var z = s.startContainer,
                    A = s.startOffset > 0 && z.getChild(s.startOffset - 1);
                this._.guardRTL = function (C, D) {
                    return (!D || !z.equals(C)) && ((!A || !C.equals(A)) && (C.type != 1 || C.getName() != 'body'));
                };
            }
            var B = p ? this._.guardRTL : this._.guardLTR;
            if (u) t = function (C, D) {
                if (B(C, D) === false) return false;
                return u(C);
            };
            else t = B;
            if (this.current) r = this.current[w](false, v, t);
            else if (p) {
                r = s.endContainer;
                if (s.endOffset > 0) {
                    r = r.getChild(s.endOffset - 1);
                    if (t(r) === false) r = null;
                } else r = t(r) === false ? null : r.getPreviousSourceNode(true, v, t);
            } else {
                r = s.startContainer;
                r = r.getChild(s.startOffset);
                if (r) {
                    if (t(r) === false) r = null;
                } else r = t(s.startContainer) === false ? null : s.startContainer.getNextSourceNode(true, v, t);
            }
            while (r && !this._.end) {
                this.current = r;
                if (!this.evaluator || this.evaluator(r) !== false) {
                    if (!q) return r;
                } else if (q && this.evaluator) return false;
                r = r[w](false, v, t);
            }
            this.end();
            return this.current = null;
        };

        function m(p) {
            var q, r = null;
            while (q = l.call(this, p)) r = q;
            return r;
        };
        d.walker = e.createClass({
            $: function (p) {
                this.range = p;
                this._ = {};
            },
            proto: {
                end: function () {
                    this._.end = 1;
                },
                next: function () {
                    return l.call(this);
                },
                previous: function () {
                    return l.call(this, true);
                },
                checkForward: function () {
                    return l.call(this, false, true) !== false;
                },
                checkBackward: function () {
                    return l.call(this, true, true) !== false;
                },
                lastForward: function () {
                    return m.call(this);
                },
                lastBackward: function () {
                    return m.call(this, true);
                },
                reset: function () {
                    delete this.current;
                    this._ = {};
                }
            }
        });
        var n = {
            block: 1,
            'list-item': 1,
            table: 1,
            'table-row-group': 1,
            'table-header-group': 1,
            'table-footer-group': 1,
            'table-row': 1,
            'table-column-group': 1,
            'table-column': 1,
            'table-cell': 1,
            'table-caption': 1
        },
            o = {
            hr: 1
        };
        h.prototype.isBlockBoundary = function (p) {
            var q = e.extend({},
            o, p || {});
            return n[this.getComputedStyle('display')] || q[this.getName()];
        };
        d.walker.blockBoundary = function (p) {
            return function (q, r) {
                return ! (q.type == 1 && q.isBlockBoundary(p));
            };
        };
        d.walker.listItemBoundary = function () {
            return this.blockBoundary({
                br: 1
            });
        };
        d.walker.bookmarkContents = function (p) {},
        d.walker.bookmark = function (p, q) {
            function r(s) {
                return s && s.getName && s.getName() == 'span' && s.hasAttribute('_fck_bookmark');
            };
            return function (s) {
                var t, u;
                t = s && !s.getName && (u = s.getParent()) && (r(u));
                t = p ? t : t || r(s);
                return q ^ t;
            };
        };
        d.walker.whitespaces = function (p) {
            return function (q) {
                var r = q && q.type == 3 && !e.trim(q.getText());
                return p ^ r;
            };
        };
    })();
    d.range = function (l) {
        var m = this;
        m.startContainer = null;
        m.startOffset = null;
        m.endContainer = null;
        m.endOffset = null;
        m.collapsed = true;
        m.document = l;
    };
    (function () {
        var l = function (q) {
            q.collapsed = q.startContainer && q.endContainer && q.startContainer.equals(q.endContainer) && q.startOffset == q.endOffset;
        },
            m = function (q, r, s) {
            q.optimizeBookmark();
            var t = q.startContainer,
                u = q.endContainer,
                v = q.startOffset,
                w = q.endOffset,
                x, y;
            if (u.type == 3) u = u.split(w);
            else if (u.getChildCount() > 0) if (w >= u.getChildCount()) {
                u = u.append(q.document.createText(''));
                y = true;
            } else u = u.getChild(w);
            if (t.type == 3) {
                t.split(v);
                if (t.equals(u)) u = t.getNext();
            } else if (!v) {
                t = t.getFirst().insertBeforeMe(q.document.createText(''));
                x = true;
            } else if (v >= t.getChildCount()) {
                t = t.append(q.document.createText(''));
                x = true;
            } else t = t.getChild(v).getPrevious();
            var z = t.getParents(),
                A = u.getParents(),
                B, C, D;
            for (B = 0; B < z.length; B++) {
                C = z[B];
                D = A[B];
                if (!C.equals(D)) break;
            }
            var E = s,
                F, G, H, I;
            for (var J = B; J < z.length; J++) {
                F = z[J];
                if (E && !F.equals(t)) G = E.append(F.clone());
                H = F.getNext();
                while (H) {
                    if (H.equals(A[J]) || H.equals(u)) break;
                    I = H.getNext();
                    if (r == 2) E.append(H.clone(true));
                    else {
                        H.remove();
                        if (r == 1) E.append(H);
                    }
                    H = I;
                }
                if (E) E = G;
            }
            E = s;
            for (var K = B; K < A.length; K++) {
                F = A[K];
                if (r > 0 && !F.equals(u)) G = E.append(F.clone());
                if (!z[K] || F.$.parentNode != z[K].$.parentNode) {
                    H = F.getPrevious();
                    while (H) {
                        if (H.equals(z[K]) || H.equals(t)) break;
                        I = H.getPrevious();
                        if (r == 2) E.$.insertBefore(H.$.cloneNode(true), E.$.firstChild);
                        else {
                            H.remove();
                            if (r == 1) E.$.insertBefore(H.$, E.$.firstChild);
                        }
                        H = I;
                    }
                }
                if (E) E = G;
            }
            if (r == 2) {
                var L = q.startContainer;
                if (L.type == 3) {
                    L.$.data += L.$.nextSibling.data;
                    L.$.parentNode.removeChild(L.$.nextSibling);
                }
                var M = q.endContainer;
                if (M.type == 3 && M.$.nextSibling) {
                    M.$.data += M.$.nextSibling.data;
                    M.$.parentNode.removeChild(M.$.nextSibling);
                }
            } else {
                if (C && D && (t.$.parentNode != C.$.parentNode || u.$.parentNode != D.$.parentNode)) {
                    var N = D.getIndex();
                    if (x && D.$.parentNode == t.$.parentNode) N--;
                    q.setStart(D.getParent(), N);
                }
                q.collapse(true);
            }
            if (x) t.remove();
            if (y && u.$.parentNode) u.remove();
        },
            n = {
            abbr: 1,
            acronym: 1,
            b: 1,
            bdo: 1,
            big: 1,
            cite: 1,
            code: 1,
            del: 1,
            dfn: 1,
            em: 1,
            font: 1,
            i: 1,
            ins: 1,
            label: 1,
            kbd: 1,
            q: 1,
            samp: 1,
            small: 1,
            span: 1,
            strike: 1,
            strong: 1,
            sub: 1,
            sup: 1,
            tt: 1,
            u: 1,
            'var': 1
        };

        function o(q) {
            var r = false,
                s = d.walker.bookmark(true);
            return function (t) {
                if (s(t)) return true;
                if (t.type == 3) {
                    if (e.trim(t.getText()).length) return false;
                } else if (!n[t.getName()]) if (!q && !c && t.getName() == 'br' && !r) r = true;
                else return false;
                return true;
            };
        };

        function p(q) {
            return q.type != 3 && q.getName() in f.$removeEmpty || !e.trim(q.getText()) || q.getParent().hasAttribute('_fck_bookmark');
        };
        d.range.prototype = {
            clone: function () {
                var r = this;
                var q = new d.range(r.document);
                q.startContainer = r.startContainer;
                q.startOffset = r.startOffset;
                q.endContainer = r.endContainer;
                q.endOffset = r.endOffset;
                q.collapsed = r.collapsed;
                return q;
            },
            collapse: function (q) {
                var r = this;
                if (q) {
                    r.endContainer = r.startContainer;
                    r.endOffset = r.startOffset;
                } else {
                    r.startContainer = r.endContainer;
                    r.startOffset = r.endOffset;
                }
                r.collapsed = true;
            },
            cloneContents: function () {
                var q = new d.documentFragment(this.document);
                if (!this.collapsed) m(this, 2, q);
                return q;
            },
            deleteContents: function () {
                if (this.collapsed) return;
                m(this, 0);
            },
            extractContents: function () {
                var q = new d.documentFragment(this.document);
                if (!this.collapsed) m(this, 1, q);
                return q;
            },
            createBookmark: function (q) {
                var v = this;
                var r, s, t, u;
                r = v.document.createElement('span');
                r.setAttribute('_fck_bookmark', 1);
                r.setStyle('display', 'none');
                r.setHtml('&nbsp;');
                if (q) {
                    t = 'cke_bm_' + e.getNextNumber();
                    r.setAttribute('id', t + 'S');
                }
                if (!v.collapsed) {
                    s = r.clone();
                    s.setHtml('&nbsp;');
                    if (q) s.setAttribute('id', t + 'E');
                    u = v.clone();
                    u.collapse();
                    u.insertNode(s);
                }
                u = v.clone();
                u.collapse(true);
                u.insertNode(r);
                if (s) {
                    v.setStartAfter(r);
                    v.setEndBefore(s);
                } else v.moveToPosition(r, 4);
                return {
                    startNode: q ? t + 'S' : r,
                    endNode: q ? t + 'E' : s,
                    serializable: q
                };
            },
            createBookmark2: function (q) {
                var x = this;
                var r = x.startContainer,
                    s = x.endContainer,
                    t = x.startOffset,
                    u = x.endOffset,
                    v, w;
                if (!r || !s) return {
                    start: 0,
                    end: 0
                };
                if (q) {
                    if (r.type == 1) {
                        v = r.getChild(t);
                        if (v && v.type == 3 && t > 0 && v.getPrevious().type == 3) {
                            r = v;
                            t = 0;
                        }
                    }
                    while (r.type == 3 && (w = r.getPrevious()) && (w.type == 3)) {
                        r = w;
                        t += w.getLength();
                    }
                    if (!x.isCollapsed) {
                        if (s.type == 1) {
                            v = s.getChild(u);
                            if (v && v.type == 3 && u > 0 && v.getPrevious().type == 3) {
                                s = v;
                                u = 0;
                            }
                        }
                        while (s.type == 3 && (w = s.getPrevious()) && (w.type == 3)) {
                            s = w;
                            u += w.getLength();
                        }
                    }
                }
                return {
                    start: r.getAddress(q),
                    end: x.isCollapsed ? null : s.getAddress(q),
                    startOffset: t,
                    endOffset: u,
                    normalized: q,
                    is2: true
                };
            },
            moveToBookmark: function (q) {
                var y = this;
                if (q.is2) {
                    var r = y.document.getByAddress(q.start, q.normalized),
                        s = q.startOffset,
                        t = q.end && y.document.getByAddress(q.end, q.normalized),
                        u = q.endOffset;
                    y.setStart(r, s);
                    if (t) y.setEnd(t, u);
                    else y.collapse(true);
                } else {
                    var v = q.serializable,
                        w = v ? y.document.getById(q.startNode) : q.startNode,
                    x = v ? y.document.getById(q.endNode) : q.endNode;
                    y.setStartBefore(w);
                    w.remove();
                    if (x) {
                        y.setEndBefore(x);
                        x.remove();
                    } else y.collapse(true);
                }
            },
            getBoundaryNodes: function () {
                var v = this;
                var q = v.startContainer,
                    r = v.endContainer,
                    s = v.startOffset,
                    t = v.endOffset,
                    u;
                if (q.type == 1) {
                    u = q.getChildCount();
                    if (u > s) q = q.getChild(s);
                    else if (u < 1) q = q.getPreviousSourceNode();
                    else {
                        q = q.$;
                        while (q.lastChild) q = q.lastChild;
                        q = new d.node(q);
                        q = q.getNextSourceNode() || q;
                    }
                }
                if (r.type == 1) {
                    u = r.getChildCount();
                    if (u > t) r = r.getChild(t).getPreviousSourceNode(true);
                    else if (u < 1) r = r.getPreviousSourceNode();
                    else {
                        r = r.$;
                        while (r.lastChild) r = r.lastChild;
                        r = new d.node(r);
                    }
                }
                if (q.getPosition(r) & 2) q = r;
                return {
                    startNode: q,
                    endNode: r
                };
            },
            getCommonAncestor: function (q, r) {
                var v = this;
                var s = v.startContainer,
                    t = v.endContainer,
                    u;
                if (s.equals(t)) {
                    if (q && s.type == 1 && v.startOffset == v.endOffset - 1) u = s.getChild(v.startOffset);
                    else u = s;
                } else u = s.getCommonAncestor(t);
                return r && !u.is ? u.getParent() : u;
            },
            optimize: function () {
                var s = this;
                var q = s.startContainer,
                    r = s.startOffset;
                if (q.type != 1) if (!r) s.setStartBefore(q);
                else if (r >= q.getLength()) s.setStartAfter(q);
                q = s.endContainer;
                r = s.endOffset;
                if (q.type != 1) if (!r) s.setEndBefore(q);
                else if (r >= q.getLength()) s.setEndAfter(q);
            },
            optimizeBookmark: function () {
                var s = this;
                var q = s.startContainer,
                    r = s.endContainer;
                if (q.is && q.is('span') && q.hasAttribute('_fck_bookmark')) s.setStartAt(q, 3);
                if (r && r.is && r.is('span') && r.hasAttribute('_fck_bookmark')) s.setEndAt(r, 4);
            },
            trim: function (q, r) {
                var y = this;
                var s = y.startContainer,
                    t = y.startOffset,
                    u = y.collapsed;
                if ((!q || u) && (s && s.type == 3)) {
                    if (!t) {
                        t = s.getIndex();
                        s = s.getParent();
                    } else if (t >= s.getLength()) {
                        t = s.getIndex() + 1;
                        s = s.getParent();
                    } else {
                        var v = s.split(t);
                        t = s.getIndex() + 1;
                        s = s.getParent();
                        if (!u && y.startContainer.equals(y.endContainer)) y.setEnd(v, y.endOffset - y.startOffset);
                    }
                    y.setStart(s, t);
                    if (u) y.collapse(true);
                }
                var w = y.endContainer,
                    x = y.endOffset;
                if (! (r || u) && w && w.type == 3) {
                    if (!x) {
                        x = w.getIndex();
                        w = w.getParent();
                    } else if (x >= w.getLength()) {
                        x = w.getIndex() + 1;
                        w = w.getParent();
                    } else {
                        w.split(x);
                        x = w.getIndex() + 1;
                        w = w.getParent();
                    }
                    y.setEnd(w, x);
                }
            },
            enlarge: function (q) {
                switch (q) {
                case 1:
                    if (this.collapsed) return;
                    var r = this.getCommonAncestor(),
                        s = this.document.getBody(),
                        t, u, v, w, x, y = false,
                        z, A, B = this.startContainer,
                        C = this.startOffset;
                    if (B.type == 3) {
                        if (C) {
                            B = !e.trim(B.substring(0, C)).length && B;
                            y = !!B;
                        }
                        if (B) if (! (w = B.getPrevious())) v = B.getParent();
                    } else {
                        if (C) w = B.getChild(C - 1) || B.getLast();
                        if (!w) v = B;
                    }
                    while (v || w) {
                        if (v && !w) {
                            if (!x && v.equals(r)) x = true;
                            if (!s.contains(v)) break;
                            if (!y || v.getComputedStyle('display') != 'inline') {
                                y = false;
                                if (x) t = v;
                                else this.setStartBefore(v);
                            }
                            w = v.getPrevious();
                        }
                        while (w) {
                            z = false;
                            if (w.type == 3) {
                                A = w.getText();
                                if (/[^\s\ufeff]/.test(A)) w = null;
                                z = /[\s\ufeff]$/.test(A);
                            } else if (w.$.offsetWidth > 0 && !w.getAttribute('_fck_bookmark')) if (y && f.$removeEmpty[w.getName()]) {
                                A = w.getText();
                                if (!/[^\s\ufeff]/.test(A)) w = null;
                                else {
                                    var D = w.$.all || w.$.getElementsByTagName('*');
                                    for (var E = 0, F; F = D[E++];) if (!f.$removeEmpty[F.nodeName.toLowerCase()]) {
                                        w = null;
                                        break;
                                    }
                                }
                                if (w) z = !!A.length;
                            } else w = null;
                            if (z) if (y) {
                                if (x) t = v;
                                else if (v) this.setStartBefore(v);
                            } else y = true;
                            if (w) {
                                var G = w.getPrevious();
                                if (!v && !G) {
                                    v = w;
                                    w = null;
                                    break;
                                }
                                w = G;
                            } else v = null;
                        }
                        if (v) v = v.getParent();
                    }
                    B = this.endContainer;
                    C = this.endOffset;
                    v = w = null;
                    x = y = false;
                    if (B.type == 3) {
                        B = !e.trim(B.substring(C)).length && B;
                        y = !(B && B.getLength());
                        if (B) if (! (w = B.getNext())) v = B.getParent();
                    } else {
                        w = B.getChild(C);
                        if (!w) v = B;
                    }
                    while (v || w) {
                        if (v && !w) {
                            if (!x && v.equals(r)) x = true;
                            if (!s.contains(v)) break;
                            if (!y || v.getComputedStyle('display') != 'inline') {
                                y = false;
                                if (x) u = v;
                                else if (v) this.setEndAfter(v);
                            }
                            w = v.getNext();
                        }
                        while (w) {
                            z = false;
                            if (w.type == 3) {
                                A = w.getText();
                                if (/[^\s\ufeff]/.test(A)) w = null;
                                z = /^[\s\ufeff]/.test(A);
                            } else if (w.$.offsetWidth > 0 && !w.getAttribute('_fck_bookmark')) if (y && f.$removeEmpty[w.getName()]) {
                                A = w.getText();
                                if (!/[^\s\ufeff]/.test(A)) w = null;
                                else {
                                    D = w.$.all || w.$.getElementsByTagName('*');
                                    for (E = 0; F = D[E++];) if (!f.$removeEmpty[F.nodeName.toLowerCase()]) {
                                        w = null;
                                        break;
                                    }
                                }
                                if (w) z = !!A.length;
                            } else w = null;
                            if (z) if (y) if (x) u = v;
                            else this.setEndAfter(v);
                            if (w) {
                                G = w.getNext();
                                if (!v && !G) {
                                    v = w;
                                    w = null;
                                    break;
                                }
                                w = G;
                            } else v = null;
                        }
                        if (v) v = v.getParent();
                    }
                    if (t && u) {
                        r = t.contains(u) ? u : t;
                        this.setStartBefore(r);
                        this.setEndAfter(r);
                    }
                    break;
                case 2:
                case 3:
                    var H = new d.range(this.document);
                    s = this.document.getBody();
                    H.setStartAt(s, 1);
                    H.setEnd(this.startContainer, this.startOffset);
                    var I = new d.walker(H),
                        J, K, L = d.walker.blockBoundary(q == 3 ? {
                        br: 1
                    } : null),
                        M = function (O) {
                        var P = L(O);
                        if (!P) J = O;
                        return P;
                    },
                        N = function (O) {
                        var P = M(O);
                        if (!P && O.is && O.is('br')) K = O;
                        return P;
                    };
                    I.guard = M;
                    v = I.lastBackward();
                    J = J || s;
                    this.setStartAt(J, !J.is('br') && (!v || J.contains(v)) ? 1 : 4);
                    H = this.clone();
                    H.collapse();
                    H.setEndAt(s, 2);
                    I = new d.walker(H);
                    I.guard = q == 3 ? N : M;
                    J = null;
                    v = I.lastForward();
                    J = J || s;
                    this.setEndAt(J, !J.is('br') && (!v || J.contains(v)) ? 2 : 3);
                    if (K) this.setEndAfter(K);
                }
            },
            insertNode: function (q) {
                var u = this;
                u.optimizeBookmark();
                u.trim(false, true);
                var r = u.startContainer,
                    s = u.startOffset,
                    t = r.getChild(s);
                if (t) q.insertBefore(t);
                else r.append(q);
                if (q.getParent().equals(u.endContainer)) u.endOffset++;
                u.setStartBefore(q);
            },
            moveToPosition: function (q, r) {
                this.setStartAt(q, r);
                this.collapse(true);
            },
            selectNodeContents: function (q) {
                this.setStart(q, 0);
                this.setEnd(q, q.type == 3 ? q.getLength() : q.getChildCount());
            },
            setStart: function (q, r) {
                var s = this;
                s.startContainer = q;
                s.startOffset = r;
                if (!s.endContainer) {
                    s.endContainer = q;
                    s.endOffset = r;
                }
                l(s);
            },
            setEnd: function (q, r) {
                var s = this;
                s.endContainer = q;
                s.endOffset = r;
                if (!s.startContainer) {
                    s.startContainer = q;
                    s.startOffset = r;
                }
                l(s);
            },
            setStartAfter: function (q) {
                this.setStart(q.getParent(), q.getIndex() + 1);
            },
            setStartBefore: function (q) {
                this.setStart(q.getParent(), q.getIndex());
            },
            setEndAfter: function (q) {
                this.setEnd(q.getParent(), q.getIndex() + 1);
            },
            setEndBefore: function (q) {
                this.setEnd(q.getParent(), q.getIndex());
            },
            setStartAt: function (q, r) {
                var s = this;
                switch (r) {
                case 1:
                    s.setStart(q, 0);
                    break;
                case 2:
                    if (q.type == 3) s.setStart(q, q.getLength());
                    else s.setStart(q, q.getChildCount());
                    break;
                case 3:
                    s.setStartBefore(q);
                    break;
                case 4:
                    s.setStartAfter(q);
                }
                l(s);
            },
            setEndAt: function (q, r) {
                var s = this;
                switch (r) {
                case 1:
                    s.setEnd(q, 0);
                    break;
                case 2:
                    if (q.type == 3) s.setEnd(q, q.getLength());
                    else s.setEnd(q, q.getChildCount());
                    break;
                case 3:
                    s.setEndBefore(q);
                    break;
                case 4:
                    s.setEndAfter(q);
                }
                l(s);
            },
            fixBlock: function (q, r) {
                var u = this;
                var s = u.createBookmark(),
                    t = u.document.createElement(r);
                u.collapse(q);
                u.enlarge(2);
                u.extractContents().appendTo(t);
                t.trim();
                if (!c) t.appendBogus();
                u.insertNode(t);
                u.moveToBookmark(s);
                return t;
            },
            splitBlock: function (q) {
                var B = this;
                var r = new d.elementPath(B.startContainer),
                    s = new d.elementPath(B.endContainer),
                    t = r.blockLimit,
                    u = s.blockLimit,
                    v = r.block,
                    w = s.block,
                    x = null;
                if (!t.equals(u)) return null;
                if (q != 'br') {
                    if (!v) {
                        v = B.fixBlock(true, q);
                        w = new d.elementPath(B.endContainer).block;
                    }
                    if (!w) w = B.fixBlock(false, q);
                }
                var y = v && B.checkStartOfBlock(),
                    z = w && B.checkEndOfBlock();
                B.deleteContents();
                if (v && v.equals(w)) if (z) {
                    x = new d.elementPath(B.startContainer);
                    B.moveToPosition(w, 4);
                    w = null;
                } else if (y) {
                    x = new d.elementPath(B.startContainer);
                    B.moveToPosition(v, 3);
                    v = null;
                } else {
                    B.setEndAt(v, 2);
                    var A = B.extractContents();
                    w = v.clone(false);
                    A.appendTo(w);
                    w.insertAfter(v);
                    B.moveToPosition(v, 4);
                    if (!c && !v.is('ul', 'ol')) v.appendBogus();
                }
                return {
                    previousBlock: v,
                    nextBlock: w,
                    wasStartOfBlock: y,
                    wasEndOfBlock: z,
                    elementPath: x
                };
            },
            checkBoundaryOfElement: function (q, r) {
                var s = this.clone();
                s[r == 1 ? 'setStartAt' : 'setEndAt'](q, r == 1 ? 1 : 2);
                var t = new d.walker(s),
                    u = false;
                t.evaluator = p;
                return t[r == 1 ? 'checkBackward' : 'checkForward']();
            },
            checkStartOfBlock: function () {
                var w = this;
                var q = w.startContainer,
                    r = w.startOffset;
                if (r && q.type == 3) {
                    var s = e.ltrim(q.substring(0, r));
                    if (s.length) return false;
                }
                w.trim();
                var t = new d.elementPath(w.startContainer),
                    u = w.clone();
                u.collapse(true);
                u.setStartAt(t.block || t.blockLimit, 1);
                var v = new d.walker(u);
                v.evaluator = o(true);
                return v.checkBackward();
            },
            checkEndOfBlock: function () {
                var w = this;
                var q = w.endContainer,
                    r = w.endOffset;
                if (q.type == 3) {
                    var s = e.rtrim(q.substring(r));
                    if (s.length) return false;
                }
                w.trim();
                var t = new d.elementPath(w.endContainer),
                    u = w.clone();
                u.collapse(false);
                u.setEndAt(t.block || t.blockLimit, 2);
                var v = new d.walker(u);
                v.evaluator = o(false);
                return v.checkForward();
            },
            moveToElementEditStart: function (q) {
                var r;
                while (q && q.type == 1) {
                    if (q.isEditable()) r = q;
                    else if (r) break;
                    q = q.getFirst();
                }
                if (r) {
                    this.moveToPosition(r, 1);
                    return true;
                } else return false;
            },
            getEnclosedNode: function () {
                var q = this.clone(),
                    r = new d.walker(q),
                    s = d.walker.bookmark(true),
                    t = d.walker.whitespaces(true),
                    u = function (w) {
                    return t(w) && s(w);
                };
                q.evaluator = u;
                var v = r.next();
                r.reset();
                return v && v.equals(r.previous()) ? v : null;
            },
            getTouchedStartNode: function () {
                var q = this.startContainer;
                if (this.collapsed || q.type != 1) return q;
                return q.getChild(this.startOffset) || q;
            },
            getTouchedEndNode: function () {
                var q = this.endContainer;
                if (this.collapsed || q.type != 1) return q;
                return q.getChild(this.endOffset - 1) || q;
            }
        };
    })();
    a.POSITION_AFTER_START = 1;
    a.POSITION_BEFORE_END = 2;
    a.POSITION_BEFORE_START = 3;
    a.POSITION_AFTER_END = 4;
    a.ENLARGE_ELEMENT = 1;
    a.ENLARGE_BLOCK_CONTENTS = 2;
    a.ENLARGE_LIST_ITEM_CONTENTS = 3;
    a.START = 1;
    a.END = 2;
    a.STARTEND = 3;
    (function () {
        var l = c && b.version < 7 ? a.basePath + 'images/spacer.gif' : 'about:blank',
        m = h.createFromHtml('<div style="width:0px;height:0px;position:absolute;left:-10000px;background-image:url(' + l + ')"></div>', a.document);
        m.appendTo(a.document.getHead());
        if (b.hc = m.getComputedStyle('background-image') == 'none') b.cssClass += ' cke_hc';
        m.remove();
    })();
    j.load(i.corePlugins.split(','), function () {
        a.status = 'loaded';
        a.fire('loaded');
        var l = a._.pending;
        if (l) {
            delete a._.pending;
            for (var m = 0; m < l.length; m++) a.add(l[m]);
        }
    });
    j.add('about', {
        init: function (l) {
            var m = l.addCommand('about', new a.dialogCommand('about'));
            m.modes = {
                wysiwyg: 1,
                source: 1
            };
            m.canUndo = false;
            l.ui.addButton('About', {
                label: l.lang.about.title,
                command: 'about'
            });
            a.dialog.add('about', this.path + 'dialogs/about.js');
        }
    });
    j.add('basicstyles', {
        requires: ['styles', 'button'],
        init: function (l) {
            var m = function (p, q, r, s) {
                var t = new a.style(s);
                l.attachStyleStateChange(t, function (u) {
                    l.getCommand(r).setState(u);
                });
                l.addCommand(r, new a.styleCommand(t));
                l.ui.addButton(p, {
                    label: q,
                    command: r
                });
            },
                n = l.config,
                o = l.lang;
            m('Bold', o.bold, 'bold', n.coreStyles_bold);
            m('Italic', o.italic, 'italic', n.coreStyles_italic);
            m('Underline', o.underline, 'underline', n.coreStyles_underline);
            m('Strike', o.strike, 'strike', n.coreStyles_strike);
            m('Subscript', o.subscript, 'subscript', n.coreStyles_subscript);
            m('Superscript', o.superscript, 'superscript', n.coreStyles_superscript);
        }
    });
    i.coreStyles_bold = {
        element: 'strong',
        overrides: 'b'
    };
    i.coreStyles_italic = {
        element: 'em',
        overrides: 'i'
    };
    i.coreStyles_underline = {
        element: 'u'
    };
    i.coreStyles_strike = {
        element: 'strike'
    };
    i.coreStyles_subscript = {
        element: 'sub'
    };
    i.coreStyles_superscript = {
        element: 'sup'
    };
    (function () {
        function l(p, q) {
            var r = q.block || q.blockLimit;
            if (!r || r.getName() == 'body') return 2;
            if (r.getAscendant('blockquote', true)) return 1;
            return 2;
        };

        function m(p) {
            var q = p.editor,
                r = q.getCommand('blockquote');
            r.state = l(q, p.data.path);
            r.fire('state');
        };

        function n(p) {
            for (var q = 0, r = p.getChildCount(), s; q < r && (s = p.getChild(q)); q++) if (s.type == 1 && s.isBlockBoundary()) return false;
            return true;
        };
        var o = {
            exec: function (p) {
                var q = p.getCommand('blockquote').state,
                    r = p.getSelection(),
                    s = r && r.getRanges()[0];
                if (!s) return;
                var t = r.createBookmarks();
                if (c) {
                    var u = t[0].startNode,
                        v = t[0].endNode,
                        w;
                    if (u && u.getParent().getName() == 'blockquote') {
                        w = u;
                        while (w = w.getNext()) if (w.type == 1 && w.isBlockBoundary()) {
                            u.move(w, true);
                            break;
                        }
                    }
                    if (v && v.getParent().getName() == 'blockquote') {
                        w = v;
                        while (w = w.getPrevious()) if (w.type == 1 && w.isBlockBoundary()) {
                            v.move(w);
                            break;
                        }
                    }
                }
                var x = s.createIterator(),
                    y;
                if (q == 2) {
                    var z = [];
                    while (y = x.getNextParagraph()) z.push(y);
                    if (z.length < 1) {
                        var A = p.document.createElement(p.config.enterMode == 1 ? 'p' : 'div'),
                            B = t.shift();
                        s.insertNode(A);
                        A.append(new d.text('﻿', p.document));
                        s.moveToBookmark(B);
                        s.selectNodeContents(A);
                        s.collapse(true);
                        B = s.createBookmark();
                        z.push(A);
                        t.unshift(B);
                    }
                    var C = z[0].getParent(),
                        D = [];
                    for (var E = 0; E < z.length; E++) {
                        y = z[E];
                        C = C.getCommonAncestor(y.getParent());
                    }
                    var F = {
                        table: 1,
                        tbody: 1,
                        tr: 1,
                        ol: 1,
                        ul: 1
                    };
                    while (F[C.getName()]) C = C.getParent();
                    var G = null;
                    while (z.length > 0) {
                        y = z.shift();
                        while (!y.getParent().equals(C)) y = y.getParent();
                        if (!y.equals(G)) D.push(y);
                        G = y;
                    }
                    while (D.length > 0) {
                        y = D.shift();
                        if (y.getName() == 'blockquote') {
                            var H = new d.documentFragment(p.document);
                            while (y.getFirst()) {
                                H.append(y.getFirst().remove());
                                z.push(H.getLast());
                            }
                            H.replace(y);
                        } else z.push(y);
                    }
                    var I = p.document.createElement('blockquote');
                    I.insertBefore(z[0]);
                    while (z.length > 0) {
                        y = z.shift();
                        I.append(y);
                    }
                } else if (q == 1) {
                    var J = [],
                        K = {};
                    while (y = x.getNextParagraph()) {
                        var L = null,
                            M = null;
                        while (y.getParent()) {
                            if (y.getParent().getName() == 'blockquote') {
                                L = y.getParent();
                                M = y;
                                break;
                            }
                            y = y.getParent();
                        }
                        if (L && M && !M.getCustomData('blockquote_moveout')) {
                            J.push(M);
                            h.setMarker(K, M, 'blockquote_moveout', true);
                        }
                    }
                    h.clearAllMarkers(K);
                    var N = [],
                        O = [];
                    K = {};
                    while (J.length > 0) {
                        var P = J.shift();
                        I = P.getParent();
                        if (!P.getPrevious()) P.remove().insertBefore(I);
                        else if (!P.getNext()) P.remove().insertAfter(I);
                        else {
                            P.breakParent(P.getParent());
                            O.push(P.getNext());
                        }
                        if (!I.getCustomData('blockquote_processed')) {
                            O.push(I);
                            h.setMarker(K, I, 'blockquote_processed', true);
                        }
                        N.push(P);
                    }
                    h.clearAllMarkers(K);
                    for (E = O.length - 1; E >= 0; E--) {
                        I = O[E];
                        if (n(I)) I.remove();
                    }
                    if (p.config.enterMode == 2) {
                        var Q = true;
                        while (N.length) {
                            P = N.shift();
                            if (P.getName() == 'div') {
                                H = new d.documentFragment(p.document);
                                var R = Q && P.getPrevious() && !(P.getPrevious().type == 1 && P.getPrevious().isBlockBoundary());
                                if (R) H.append(p.document.createElement('br'));
                                var S = P.getNext() && !(P.getNext().type == 1 && P.getNext().isBlockBoundary());
                                while (P.getFirst()) P.getFirst().remove().appendTo(H);
                                if (S) H.append(p.document.createElement('br'));
                                H.replace(P);
                                Q = false;
                            }
                        }
                    }
                }
                r.selectBookmarks(t);
                p.focus();
            }
        };
        j.add('blockquote', {
            init: function (p) {
                p.addCommand('blockquote', o);
                p.ui.addButton('Blockquote', {
                    label: p.lang.blockquote,
                    command: 'blockquote'
                });
                p.on('selectionChange', m);
            },
            requires: ['domiterator']
        });
    })();
    j.add('button', {
        beforeInit: function (l) {
            l.ui.addHandler(1, k.button.handler);
        }
    });
    a.UI_BUTTON = 1;
    k.button = function (l) {
        e.extend(this, l, {
            title: l.label,
            className: l.className || l.command && 'cke_button_' + l.command || '',
            click: l.click || (function (m) {
                m.execCommand(l.command);
            })
        });
        this._ = {};
    };
    k.button.handler = {
        create: function (l) {
            return new k.button(l);
        }
    };
    k.button.prototype = {
        canGroup: true,
        render: function (l, m) {
            var n = b,
                o = this._.id = 'cke_' + e.getNextNumber();
            this._.editor = l;
            var p = {
                id: o,
                button: this,
                editor: l,
                focus: function () {
                    var v = a.document.getById(o);
                    v.focus();
                },
                execute: function () {
                    this.button.click(l);
                }
            },
                q = e.addFunction(p.execute, p),
                r = k.button._.instances.push(p) - 1,
                s = '',
                t = this.command;
            if (this.modes) l.on('mode', function () {
                this.setState(this.modes[l.mode] ? 2 : 0);
            },
            this);
            else if (t) {
                t = l.getCommand(t);
                if (t) {
                    t.on('state', function () {
                        this.setState(t.state);
                    },
                    this);
                    s += 'cke_' + (t.state == 1 ? 'on' : t.state == 0 ? 'disabled' : 'off');
                }
            }
            if (!t) s += 'cke_off';
            if (this.className) s += ' ' + this.className;
            m.push('<span class="cke_button">', '<a id="', o, '" class="', s, '" href="javascript:void(\'', (this.title || '').replace("'", ''), '\')" title="', this.title, '" tabindex="-1" hidefocus="true"');
            if (n.opera || n.gecko && n.mac) m.push(' onkeypress="return false;"');
            if (n.gecko) m.push(' onblur="this.style.cssText = this.style.cssText;"');
            m.push(' onkeydown="return CKEDITOR.ui.button._.keydown(', r, ', event);" onfocus="return CKEDITOR.ui.button._.focus(', r, ', event);" onclick="CKEDITOR.tools.callFunction(', q, ', this); return false;"><span class="cke_icon"');
            if (this.icon) {
                var u = (this.iconOffset || 0) * (-16);
                m.push(' style="background-image:url(', a.getUrl(this.icon), ');background-position:0 ' + u + 'px;"');
            }
            m.push('></span><span class="cke_label">', this.label, '</span>');
            if (this.hasArrow) m.push('<span class="cke_buttonarrow"></span>');
            m.push('</a>', '</span>');
            if (this.onRender) this.onRender();
            return p;
        },
        setState: function (l) {
            var q = this;
            if (q._.state == l) return;
            var m = a.document.getById(q._.id);
            if (m) {
                m.setState(l);
                var n = q.title,
                    o = q._.editor.lang.common.unavailable,
                    p = m.getChild(1);
                if (l == 0) n = o.replace('%1', q.title);
                p.setHtml(n);
            }
            q._.state = l;
        }
    };
    k.button._ = {
        instances: [],
        keydown: function (l, m) {
            var n = k.button._.instances[l];
            if (n.onkey) {
                m = new d.event(m);
                return n.onkey(n, m.getKeystroke()) !== false;
            }
        },
        focus: function (l, m) {
            var n = k.button._.instances[l],
                o;
            if (n.onfocus) o = n.onfocus(n, new d.event(m)) !== false;
            if (b.gecko && b.version < 10900) m.preventBubble();
            return o;
        }
    };
    k.prototype.addButton = function (l, m) {
        this.add(l, 1, m);
    };
    (function () {
        var l = function (q, r) {
            var s = q.document,
                t = s.getBody(),
                u = false,
                v = function () {
                u = true;
            };
            t.on(r, v);
            s.$.execCommand(r);
            t.removeListener(r, v);
            return u;
        },
            m = c ?
        function (q, r) {
            return l(q, r);
        } : function (q, r) {
            try {
                return q.document.$.execCommand(r);
            } catch(s) {
                return false;
            }
        },
        n = function (q) {
            this.type = q;
            this.canUndo = this.type == 'cut';
        };
        n.prototype = {
            exec: function (q, r) {
                var s = m(q, this.type);
                if (!s) alert(q.lang.clipboard[this.type + 'Error']);
                return s;
            }
        };
        var o = c ? {
            exec: function (q, r) {
                q.focus();
                if (!q.fire('beforePaste') && !l(q, 'paste')) q.openDialog('paste');
            }
        } : {
            exec: function (q) {
                try {
                    if (!q.fire('beforePaste') && !q.document.$.execCommand('Paste', false, null)) throw 0;
                } catch(r) {
                    q.openDialog('paste');
                }
            }
        },
        p = function (q) {
            switch (q.data.keyCode) {
            case 1000 + 86:
            case 2000 + 45:
                var r = this;
                r.fire('saveSnapshot');
                if (r.fire('beforePaste')) q.cancel();
                setTimeout(function () {
                    r.fire('saveSnapshot');
                },
                0);
                return;
            case 1000 + 88:
            case 2000 + 46:
                r = this;
                r.fire('saveSnapshot');
                setTimeout(function () {
                    r.fire('saveSnapshot');
                },
                0);
            }
        };
        j.add('clipboard', {
            init: function (q) {
                function r(t, u, v, w) {
                    var x = q.lang[u];
                    q.addCommand(u, v);
                    q.ui.addButton(t, {
                        label: x,
                        command: u
                    });
                    if (q.addMenuItems) q.addMenuItem(u, {
                        label: x,
                        command: u,
                        group: 'clipboard',
                        order: w
                    });
                };
                r('Cut', 'cut', new n('cut'), 1);
                r('Copy', 'copy', new n('copy'), 4);
                r('Paste', 'paste', o, 8);
                a.dialog.add('paste', a.getUrl(this.path + 'dialogs/paste.js'));
                q.on('key', p, q);
                if (q.contextMenu) {
                    function s(t) {
                        return q.document.$.queryCommandEnabled(t) ? 2 : 0;
                    };
                    q.contextMenu.addListener(function () {
                        return {
                            cut: s('Cut'),
                            copy: s('Cut'),
                            paste: b.webkit ? 2 : s('Paste')
                        };
                    });
                }
            }
        });
    })();
    j.add('colorbutton', {
        requires: ['panelbutton', 'floatpanel', 'styles'],
        init: function (l) {
            var m = l.config,
                n = l.lang.colorButton,
                o;
            if (!b.hc) {
                p('TextColor', 'fore', n.textColorTitle);
                p('BGColor', 'back', n.bgColorTitle);
            }

            function p(r, s, t) {
                l.ui.add(r, 4, {
                    label: t,
                    title: t,
                    className: 'cke_button_' + r.toLowerCase(),
                    modes: {
                        wysiwyg: 1
                    },
                    panel: {
                        css: [a.getUrl(l.skinPath + 'editor.css')]
                    },
                    onBlock: function (u, v) {
                        var w = u.addBlock(v);
                        w.autoSize = true;
                        w.element.addClass('cke_colorblock');
                        w.element.setHtml(q(u, s));
                        var x = w.keys;
                        x[39] = 'next';
                        x[9] = 'next';
                        x[37] = 'prev';
                        x[2000 + 9] = 'prev';
                        x[32] = 'click';
                    }
                });
            };

            function q(r, s) {
                var t = [],
                    u = m.colorButton_colors.split(','),
                    v = e.addFunction(function (z, A) {
                    if (z == '?') return;
                    l.focus();
                    r.hide();
                    var B = new a.style(m['colorButton_' + A + 'Style'], z && {
                        color: z
                    });
                    l.fire('saveSnapshot');
                    if (z) B.apply(l.document);
                    else B.remove(l.document);
                    l.fire('saveSnapshot');
                });
                t.push('<a class="cke_colorauto" _cke_focus=1 hidefocus=true title="', n.auto, '" onclick="CKEDITOR.tools.callFunction(', v, ",null,'", s, "');return false;\" href=\"javascript:void('", n.auto, '\')"><table cellspacing=0 cellpadding=0 width="100%"><tr><td><span class="cke_colorbox" style="background-color:#000"></span></td><td colspan=7 align=center>', n.auto, '</td></tr></table></a><table cellspacing=0 cellpadding=0 width="100%">');
                for (var w = 0; w < u.length; w++) {
                    if (w % 8 === 0) t.push('</tr><tr>');
                    var x = u[w],
                        y = l.lang.colors[x] || x;
                    t.push('<td><a class="cke_colorbox" _cke_focus=1 hidefocus=true title="', y, '" onclick="CKEDITOR.tools.callFunction(', v, ",'#", x, "','", s, "'); return false;\" href=\"javascript:void('", y, '\')"><span class="cke_colorbox" style="background-color:#', x, '"></span></a></td>');
                }
                if (m.colorButton_enableMore) t.push('</tr><tr><td colspan=8 align=center><a class="cke_colormore" _cke_focus=1 hidefocus=true title="', n.more, '" onclick="CKEDITOR.tools.callFunction(', v, ",'?','", s, "');return false;\" href=\"javascript:void('", n.more, "')\">", n.more, '</a></td>');
                t.push('</tr></table>');
                return t.join('');
            };
        }
    });
    i.colorButton_enableMore = false;
    i.colorButton_colors = '000,800000,8B4513,2F4F4F,008080,000080,4B0082,696969,B22222,A52A2A,DAA520,006400,40E0D0,0000CD,800080,808080,F00,FF8C00,FFD700,008000,0FF,00F,EE82EE,A9A9A9,FFA07A,FFA500,FFFF00,00FF00,AFEEEE,ADD8E6,DDA0DD,D3D3D3,FFF0F5,FAEBD7,FFFFE0,F0FFF0,F0FFFF,F0F8FF,E6E6FA,FFF';
    i.colorButton_foreStyle = {
        element: 'span',
        styles: {
            color: '#(color)'
        },
        overrides: [{
            element: 'font',
            attributes: {
                color: null
            }
        }]
    };
    i.colorButton_backStyle = {
        element: 'span',
        styles: {
            'background-color': '#(color)'
        }
    };
    j.add('contextmenu', {
        requires: ['menu'],
        beforeInit: function (l) {
            l.contextMenu = new j.contextMenu(l);
            l.addCommand('contextMenu', {
                exec: function () {
                    l.contextMenu.show();
                }
            });
        }
    });
    j.contextMenu = e.createClass({
        $: function (l) {
            this.id = 'cke_' + e.getNextNumber();
            this.editor = l;
            this._.listeners = [];
            this._.functionId = e.addFunction(function (m) {
                this._.panel.hide();
                l.focus();
                l.execCommand(m);
            },
            this);
        },
        _: {
            onMenu: function (l, m, n, o) {
                var p = this._.menu,
                    q = this.editor;
                if (p) {
                    p.hide();
                    p.removeAll();
                } else {
                    p = this._.menu = new a.menu(q);
                    p.onClick = e.bind(function (z) {
                        var A = true;
                        p.hide();
                        if (c) p.onEscape();
                        if (z.onClick) z.onClick();
                        else if (z.command) q.execCommand(z.command);
                        A = false;
                    },
                    this);
                    p.onEscape = function () {
                        q.focus();
                        if (c) q.getSelection().unlock(true);
                    };
                }
                var r = this._.listeners,
                    s = [],
                    t = this.editor.getSelection(),
                    u = t && t.getStartElement();
                if (c) t.lock();
                p.onHide = e.bind(function () {
                    p.onHide = null;
                    if (c) q.getSelection().unlock();
                    this.onHide && this.onHide();
                },
                this);
                for (var v = 0; v < r.length; v++) {
                    var w = r[v](u, t);
                    if (w) for (var x in w) {
                        var y = this.editor.getMenuItem(x);
                        if (y) {
                            y.state = w[x];
                            p.add(y);
                        }
                    }
                }
                p.show(l, m || (q.lang.dir == 'rtl' ? 2 : 1), n, o);
            }
        },
        proto: {
            addTarget: function (l) {
                l.on('contextmenu', function (m) {
                    var n = m.data;
                    n.preventDefault();
                    var o = n.getTarget().getDocument().getDocumentElement(),
                        p = n.$.clientX,
                        q = n.$.clientY;
                    e.setTimeout(function () {
                        this._.onMenu(o, null, p, q);
                    },
                    0, this);
                },
                this);
            },
            addListener: function (l) {
                this._.listeners.push(l);
            },
            show: function (l, m, n, o) {
                this.editor.focus();
                this._.onMenu(l || a.document.getDocumentElement(), m, n || 0, o || 0);
            }
        }
    });
    (function () {
        var l = {
            toolbarFocus: {
                exec: function (n) {
                    var o = n._.elementsPath.idBase,
                        p = a.document.getById(o + '0');
                    if (p) p.focus();
                }
            }
        },
            m = '<span class="cke_empty">&nbsp;</span>';
        j.add('elementspath', {
            requires: ['selection'],
            init: function (n) {
                var o = 'cke_path_' + n.name,
                    p, q = function () {
                    if (!p) p = a.document.getById(o);
                    return p;
                },
                    r = 'cke_elementspath_' + e.getNextNumber() + '_';
                n._.elementsPath = {
                    idBase: r
                };
                n.on('themeSpace', function (s) {
                    if (s.data.space == 'bottom') s.data.html += '<div id="' + o + '" class="cke_path">' + m + '</div>';
                });
                n.on('selectionChange', function (s) {
                    var t = b,
                        u = s.data.selection,
                        v = u.getStartElement(),
                        w = [],
                        x = this._.elementsPath.list = [];
                    while (v) {
                        var y = x.push(v) - 1,
                            z;
                        if (v.getAttribute('_cke_real_element_type')) z = v.getAttribute('_cke_real_element_type');
                        else z = v.getName();
                        var A = '';
                        if (t.opera || t.gecko && t.mac) A += ' onkeypress="return false;"';
                        if (t.gecko) A += ' onblur="this.style.cssText = this.style.cssText;"';
                        w.unshift('<a id="', r, y, '" href="javascript:void(\'', z, '\')" tabindex="-1" title="', n.lang.elementsPath.eleTitle.replace(/%1/, z), '"' + (b.gecko && b.version < 10900 ? ' onfocus="event.preventBubble();"' : '') + ' hidefocus="true" ' + " onkeydown=\"return CKEDITOR._.elementsPath.keydown('", this.name, "',", y, ', event);"' + A, " onclick=\"return CKEDITOR._.elementsPath.click('", this.name, "',", y, ');">', z, '</a>');
                        if (z == 'body') break;
                        v = v.getParent();
                    }
                    q().setHtml(w.join('') + m);
                });
                n.on('contentDomUnload', function () {
                    q().setHtml(m);
                });
                n.addCommand('elementsPathFocus', l.toolbarFocus);
            }
        });
    })();
    a._.elementsPath = {
        click: function (l, m) {
            var n = a.instances[l];
            n.focus();
            var o = n._.elementsPath.list[m];
            n.getSelection().selectElement(o);
            return false;
        },
        keydown: function (l, m, n) {
            var o = k.button._.instances[m],
                p = a.instances[l],
                q = p._.elementsPath.idBase,
                r;
            n = new d.event(n);
            switch (n.getKeystroke()) {
            case 37:
            case 9:
                r = a.document.getById(q + (m + 1));
                if (!r) r = a.document.getById(q + '0');
                r.focus();
                return false;
            case 39:
            case 2000 + 9:
                r = a.document.getById(q + (m - 1));
                if (!r) r = a.document.getById(q + (p._.elementsPath.list.length - 1));
                r.focus();
                return false;
            case 27:
                p.focus();
                return false;
            case 13:
            case 32:
                this.click(l, m);
                return false;
            }
            return true;
        }
    };
    (function () {
        j.add('enterkey', {
            requires: ['keystrokes', 'indent'],
            init: function (s) {
                var t = s.specialKeys;
                t[13] = o;
                t[2000 + 13] = n;
            }
        });
        var l, m = /^h[1-6]$/;

        function n(s) {
            l = 1;
            return o(s, s.config.shiftEnterMode);
        };

        function o(s, t) {
            if (s.mode != 'wysiwyg') return false;
            if (!t) t = s.config.enterMode;
            setTimeout(function () {
                s.fire('saveSnapshot');
                if (t == 2 || s.getSelection().getStartElement().hasAscendant('pre', true)) q(s, t);
                else p(s, t);
                l = 0;
            },
            0);
            return true;
        };

        function p(s, t, u) {
            u = u || r(s);
            var v = u.document,
                w = t == 3 ? 'div' : 'p',
            x = u.splitBlock(w);
            if (!x) return;
            var y = x.previousBlock,
                z = x.nextBlock,
                A = x.wasStartOfBlock,
                B = x.wasEndOfBlock,
                C;
            if (z) {
                C = z.getParent();
                if (C.is('li')) {
                    z.breakParent(C);
                    z.move(z.getNext(), true);
                }
            } else if (y && (C = y.getParent()) && (C.is('li'))) {
                y.breakParent(C);
                u.moveToElementEditStart(y.getNext());
                y.move(y.getPrevious());
            }
            if (!A && !B) {
                if (z.is('li') && (C = z.getFirst()) && (C.is && C.is('ul', 'ol'))) z.insertBefore(v.createText('\xa0'), C);
                if (z) u.moveToElementEditStart(z);
            } else {
                if (A && B && y.is('li')) {
                    s.execCommand('outdent');
                    return;
                }
                var D;
                if (y) {
                    if (!l && !m.test(y.getName())) D = y.clone();
                } else if (z) D = z.clone();
                if (!D) D = v.createElement(w);
                var E = x.elementPath;
                if (E) for (var F = 0, G = E.elements.length; F < G; F++) {
                    var H = E.elements[F];
                    if (H.equals(E.block) || H.equals(E.blockLimit)) break;
                    if (f.$removeEmpty[H.getName()]) {
                        H = H.clone();
                        D.moveChildren(H);
                        D.append(H);
                    }
                }
                if (!c) D.appendBogus();
                u.insertNode(D);
                if (c && A && (!B || !y.getChildCount())) {
                    u.moveToElementEditStart(B ? y : D);
                    u.select();
                }
                u.moveToElementEditStart(A && !B ? z : D);
            }
            if (!c) if (z) {
                var I = v.createElement('span');
                I.setHtml('&nbsp;');
                u.insertNode(I);
                I.scrollIntoView();
                u.deleteContents();
            } else D.scrollIntoView();
            u.select();
        };

        function q(s, t) {
            var u = r(s),
                v = u.document,
                w = t == 3 ? 'div' : 'p',
            x = u.checkEndOfBlock(),
            y = new d.elementPath(s.getSelection().getStartElement()),
            z = y.block,
            A = z && y.block.getName(),
            B = false;
            if (!l && A == 'li') {
                p(s, t, u);
                return;
            }
            if (!l && x && m.test(A)) {
                v.createElement('br').insertAfter(z);
                if (b.gecko) v.createText('').insertAfter(z);
                u.setStartAt(z.getNext(), c ? 3 : 1);
            } else {
                var C;
                B = A == 'pre';
                if (B) C = v.createText(c ? '\r' : '\n');
                else C = v.createElement('br');
                u.deleteContents();
                u.insertNode(C);
                if (!c) v.createText('﻿').insertAfter(C);
                if (x && !c) C.getParent().appendBogus();
                if (!c) C.getNext().$.nodeValue = '';
                if (c) u.setStartAt(C, 4);
                else u.setStartAt(C.getNext(), 1);
                if (!c) {
                    var D = null;
                    if (!b.gecko) {
                        D = v.createElement('span');
                        D.setHtml('&nbsp;');
                    } else D = v.createElement('br');
                    D.insertBefore(C.getNext());
                    D.scrollIntoView();
                    D.remove();
                }
            }
            u.collapse(true);
            u.select(B);
        };

        function r(s) {
            var t = s.getSelection().getRanges();
            for (var u = t.length - 1; u > 0; u--) t[u].deleteContents();
            return t[0];
        };
    })();
    (function () {
        var l = 'nbsp,gt,lt,quot,iexcl,cent,pound,curren,yen,brvbar,sect,uml,copy,ordf,laquo,not,shy,reg,macr,deg,plusmn,sup2,sup3,acute,micro,para,middot,cedil,sup1,ordm,raquo,frac14,frac12,frac34,iquest,times,divide,fnof,bull,hellip,prime,Prime,oline,frasl,weierp,image,real,trade,alefsym,larr,uarr,rarr,darr,harr,crarr,lArr,uArr,rArr,dArr,hArr,forall,part,exist,empty,nabla,isin,notin,ni,prod,sum,minus,lowast,radic,prop,infin,ang,and,or,cap,cup,int,there4,sim,cong,asymp,ne,equiv,le,ge,sub,sup,nsub,sube,supe,oplus,otimes,perp,sdot,lceil,rceil,lfloor,rfloor,lang,rang,loz,spades,clubs,hearts,diams,circ,tilde,ensp,emsp,thinsp,zwnj,zwj,lrm,rlm,ndash,mdash,lsquo,rsquo,sbquo,ldquo,rdquo,bdquo,dagger,Dagger,permil,lsaquo,rsaquo,euro',
            m = 'Agrave,Aacute,Acirc,Atilde,Auml,Aring,AElig,Ccedil,Egrave,Eacute,Ecirc,Euml,Igrave,Iacute,Icirc,Iuml,ETH,Ntilde,Ograve,Oacute,Ocirc,Otilde,Ouml,Oslash,Ugrave,Uacute,Ucirc,Uuml,Yacute,THORN,szlig,agrave,aacute,acirc,atilde,auml,aring,aelig,ccedil,egrave,eacute,ecirc,euml,igrave,iacute,icirc,iuml,eth,ntilde,ograve,oacute,ocirc,otilde,ouml,oslash,ugrave,uacute,ucirc,uuml,yacute,thorn,yuml,OElig,oelig,Scaron,scaron,Yuml',
            n = 'Alpha,Beta,Gamma,Delta,Epsilon,Zeta,Eta,Theta,Iota,Kappa,Lambda,Mu,Nu,Xi,Omicron,Pi,Rho,Sigma,Tau,Upsilon,Phi,Chi,Psi,Omega,alpha,beta,gamma,delta,epsilon,zeta,eta,theta,iota,kappa,lambda,mu,nu,xi,omicron,pi,rho,sigmaf,sigma,tau,upsilon,phi,chi,psi,omega,thetasym,upsih,piv';


        function o(p) {
            var q = {},
                r = [],
                s = {
                nbsp: '\xa0',
                shy: '­',
                gt: '>',
                lt: '<'
            };
            p = p.replace(/\b(nbsp|shy|gt|lt|amp)(?:,|$)/g, function (x, y) {
                q[s[y]] = '&' + y + ';';
                r.push(s[y]);
                return '';
            });
            p = p.split(',');
            var t = document.createElement('div'),
                u;
            t.innerHTML = '&' + p.join(';&') + ';';
            u = t.innerHTML;
            t = null;
            for (var v = 0; v < u.length; v++) {
                var w = u.charAt(v);
                q[w] = '&' + p[v] + ';';
                r.push(w);
            }
            q.regex = r.join('');
            return q;
        };
        j.add('entities', {
            afterInit: function (p) {
                var q = p.config;
                if (!q.entities) return;
                var r = p.dataProcessor,
                    s = r && r.htmlFilter;
                if (s) {
                    var t = l;
                    if (q.entities_latin) t += ',' + m;
                    if (q.entities_greek) t += ',' + n;
                    if (q.entities_additional) t += ',' + q.entities_additional;
                    var u = o(t),
                        v = '[' + u.regex + ']';
                    delete u.regex;
                    if (q.entities_processNumerical) v = '[^ -~]|' + v;
                    v = new RegExp(v, 'g');

                    function w(x) {
                        return u[x] || '&#' + x.charCodeAt(0) + ';';
                    };
                    s.addRules({
                        text: function (x) {
                            return x.replace(v, w);
                        }
                    });
                }
            }
        });
    })();
    i.entities = true;
    i.entities_latin = true;
    i.entities_greek = true;
    i.entities_processNumerical = false;
    i.entities_additional = '#39';
    (function () {
        function l(u, v) {
            var w = [];
            if (!v) return u;
            else for (var x in v) w.push(x + '=' + encodeURIComponent(v[x]));
            return u + (u.indexOf('?') != -1 ? '&' : '?') + w.join('&');
        };

        function m(u) {
            u += '';
            var v = u.charAt(0).toUpperCase();
            return v + u.substr(1);
        };

        function n(u) {
            var B = this;
            var v = B.getDialog(),
                w = v.getParentEditor();
            w._.filebrowserSe = B;
            var x = w.config['filebrowser' + m(v.getName()) + 'WindowWidth'] || w.config.filebrowserWindowWidth || '80%',
                y = w.config['filebrowser' + m(v.getName()) + 'WindowHeight'] || w.config.filebrowserWindowHeight || '70%',
                z = B.filebrowser.params || {};
            z.CKEditor = w.name;
            z.CKEditorFuncNum = w._.filebrowserFn;
            if (!z.langCode) z.langCode = w.langCode;
            var A = l(B.filebrowser.url, z);
            w.popup(A, x, y);
        };

        function o(u) {
            var x = this;
            var v = x.getDialog(),
                w = v.getParentEditor();
            w._.filebrowserSe = x;
            if (!v.getContentElement(x['for'][0], x['for'][1]).getInputElement().$.value) return false;
            if (!v.getContentElement(x['for'][0], x['for'][1]).getAction()) return false;
            return true;
        };

        function p(u, v, w) {
            var x = w.params || {};
            x.CKEditor = u.name;
            x.CKEditorFuncNum = u._.filebrowserFn;
            if (!x.langCode) x.langCode = u.langCode;
            v.action = l(w.url, x);
            v.filebrowser = w;
        };

        function q(u, v, w, x) {
            var y, z;
            for (var A in x) {
                y = x[A];
                if (y.type == 'hbox' || y.type == 'vbox') q(u, v, w, y.children);
                if (!y.filebrowser) continue;
                if (typeof y.filebrowser == 'string') {
                    var B = {
                        action: y.type == 'fileButton' ? 'QuickUpload' : 'Browse',
                        target: y.filebrowser
                    };
                    y.filebrowser = B;
                }
                if (y.filebrowser.action == 'Browse') {
                    var C = y.filebrowser.url || u.config['filebrowser' + m(v) + 'BrowseUrl'] || u.config.filebrowserBrowseUrl;
                    if (C) {
                        y.onClick = n;
                        y.filebrowser.url = C;
                        y.hidden = false;
                    }
                } else if (y.filebrowser.action == 'QuickUpload' && y['for']) {
                    C = y.filebrowser.url || u.config['filebrowser' + m(v) + 'UploadUrl'] || u.config.filebrowserUploadUrl;
                    if (C) {
                        y.onClick = o;
                        y.filebrowser.url = C;
                        y.hidden = false;
                        p(u, w.getContents(y['for'][0]).get(y['for'][1]), y.filebrowser);
                    }
                }
            }
        };

        function r(u, v) {
            var w = v.getDialog(),
                x = v.filebrowser.target || null;
            u = u.replace(/#/g, '%23');
            if (x) {
                var y = x.split(':'),
                    z = w.getContentElement(y[0], y[1]);
                if (z) {
                    z.setValue(u);
                    w.selectPage(y[0]);
                }
            }
        };

        function s(u, v, w) {
            if (w.indexOf(';') !== -1) {
                var x = w.split(';');
                for (var y = 0; y < x.length; y++) if (s(u, v, x[y])) return true;
                return false;
            }
            return u.getContents(v).get(w).filebrowser && u.getContents(v).get(w).filebrowser.url;
        };

        function t(u, v) {
            var z = this;
            var w = z._.filebrowserSe.getDialog(),
                x = z._.filebrowserSe['for'],
                y = z._.filebrowserSe.filebrowser.onSelect;
            if (x) w.getContentElement(x[0], x[1]).reset();
            if (y && y.call(z._.filebrowserSe, u, v) === false) return;
            if (typeof v == 'string' && v) alert(v);
            if (u) r(u, z._.filebrowserSe);
        };
        j.add('filebrowser', {
            init: function (u, v) {
                u._.filebrowserFn = e.addFunction(t, u);
                a.on('dialogDefinition', function (w) {
                    for (var x in w.data.definition.contents) {
                        q(w.editor, w.data.name, w.data.definition, w.data.definition.contents[x].elements);
                        if (w.data.definition.contents[x].hidden && w.data.definition.contents[x].filebrowser) w.data.definition.contents[x].hidden = !s(w.data.definition, w.data.definition.contents[x].id, w.data.definition.contents[x].filebrowser);
                    }
                });
            }
        });
    })();
    j.add('find', {
        init: function (l) {
            var m = j.find;
            l.ui.addButton('Find', {
                label: l.lang.findAndReplace.find,
                command: 'find'
            });
            var n = l.addCommand('find', new a.dialogCommand('find'));
            n.canUndo = false;
            l.ui.addButton('Replace', {
                label: l.lang.findAndReplace.replace,
                command: 'replace'
            });
            var o = l.addCommand('replace', new a.dialogCommand('replace'));
            o.canUndo = false;
            a.dialog.add('find', this.path + 'dialogs/find.js');
            a.dialog.add('replace', this.path + 'dialogs/find.js');
        },
        requires: ['styles']
    });
    i.find_highlight = {
        element: 'span',
        styles: {
            'background-color': '#004',
            color: '#fff'
        }
    };
    (function () {
        var l = /\.swf(?:$|\?)/i,
            m = /^\d+(?:\.\d+)?$/;

        function n(q) {
            if (m.test(q)) return q + 'px';
            return q;
        };

        function o(q) {
            var r = q.attributes;
            return r.type == 'application/x-shockwave-flash' || l.test(r.src || '');
        };

        function p(q, r) {
            var s = q.createFakeParserElement(r, 'cke_flash', 'flash', true),
                t = s.attributes.style || '',
                u = r.attributes.width,
                v = r.attributes.height;
            if (typeof u != 'undefined') t = s.attributes.style = t + 'width:' + n(u) + ';';
            if (typeof v != 'undefined') t = s.attributes.style = t + 'height:' + n(v) + ';';
            return s;
        };
        j.add('flash', {
            init: function (q) {
                q.addCommand('flash', new a.dialogCommand('flash'));
                q.ui.addButton('Flash', {
                    label: q.lang.common.flash,
                    command: 'flash'
                });
                a.dialog.add('flash', this.path + 'dialogs/flash.js');
                q.addCss('img.cke_flash{background-image: url(' + a.getUrl(this.path + 'images/placeholder.png') + ');' + 'background-position: center center;' + 'background-repeat: no-repeat;' + 'border: 1px solid #a9a9a9;' + 'width: 80px;' + 'height: 80px;' + '}');
                if (q.addMenuItems) q.addMenuItems({
                    flash: {
                        label: q.lang.flash.properties,
                        command: 'flash',
                        group: 'flash'
                    }
                });
                if (q.contextMenu) q.contextMenu.addListener(function (r, s) {
                    if (r && r.is('img') && r.getAttribute('_cke_real_element_type') == 'flash') return {
                        flash: 2
                    };
                });
            },
            afterInit: function (q) {
                var r = q.dataProcessor,
                    s = r && r.dataFilter;
                if (s) s.addRules({
                    elements: {
                        'cke:object': function (t) {
                            var u = t.attributes,
                                v = u.classid && String(u.classid).toLowerCase();
                            if (!v) {
                                for (var w = 0; w < t.children.length; w++) if (t.children[w].name == 'embed') {
                                    if (!o(t.children[w])) return null;
                                    return p(q, t);
                                }
                                return null;
                            }
                            return p(q, t);
                        },
                        'cke:embed': function (t) {
                            if (!o(t)) return null;
                            return p(q, t);
                        }
                    }
                },
                5);
            },
            requires: ['fakeobjects']
        });
    })();
    e.extend(i, {
        flashEmbedTagOnly: false,
        flashAddEmbedTag: true,
        flashConvertOnEdit: false
    });
    (function () {
        function l(m, n, o, p, q, r, s) {
            var t = m.config,
                u = q.split(';'),
                v = [],
                w = {};
            for (var x = 0; x < u.length; x++) {
                var y = {},
                    z = u[x].split('/'),
                    A = u[x] = z[0];
                y[o] = v[x] = z[1] || A;
                w[A] = new a.style(s, y);
            }
            m.ui.addRichCombo(n, {
                label: p.label,
                title: p.panelTitle,
                voiceLabel: p.voiceLabel,
                className: 'cke_' + (o == 'size' ? 'fontSize' : 'font'),
                multiSelect: false,
                panel: {
                    css: [t.contentsCss, a.getUrl(m.skinPath + 'editor.css')],
                    voiceLabel: p.panelVoiceLabel
                },
                init: function () {
                    this.startGroup(p.panelTitle);
                    for (var B = 0; B < u.length; B++) {
                        var C = u[B];
                        this.add(C, '<span style="font-' + o + ':' + v[B] + '">' + C + '</span>', C);
                    }
                },
                onClick: function (B) {
                    m.focus();
                    m.fire('saveSnapshot');
                    var C = w[B];
                    if (this.getValue() == B) C.remove(m.document);
                    else C.apply(m.document);
                    m.fire('saveSnapshot');
                },
                onRender: function () {
                    m.on('selectionChange', function (B) {
                        var C = this.getValue(),
                            D = B.data.path,
                            E = D.elements;
                        for (var F = 0, G; F < E.length; F++) {
                            G = E[F];
                            for (var H in w) if (w[H].checkElementRemovable(G, true)) {
                                if (H != C) this.setValue(H);
                                return;
                            }
                        }
                        this.setValue('', r);
                    },
                    this);
                }
            });
        };
        j.add('font', {
            requires: ['richcombo', 'styles'],
            init: function (m) {
                var n = m.config;
                l(m, 'Font', 'family', m.lang.font, n.font_names, n.font_defaultLabel, n.font_style);
                l(m, 'FontSize', 'size', m.lang.fontSize, n.fontSize_sizes, n.fontSize_defaultLabel, n.fontSize_style);
            }
        });
    })();
    i.font_names = 'Arial/Arial, Helvetica, sans-serif;Comic Sans MS/Comic Sans MS, cursive;Courier New/Courier New, Courier, monospace;Georgia/Georgia, serif;Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;Tahoma/Tahoma, Geneva, sans-serif;Times New Roman/Times New Roman, Times, serif;Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;Verdana/Verdana, Geneva, sans-serif';
    i.font_defaultLabel = '';
    i.font_style = {
        element: 'span',
        styles: {
            'font-family': '#(family)'
        },
        overrides: [{
            element: 'font',
            attributes: {
                face: null
            }
        }]
    };
    i.fontSize_sizes = '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;72/72px';
    i.fontSize_defaultLabel = '';
    i.fontSize_style = {
        element: 'span',
        styles: {
            'font-size': '#(size)'
        },
        overrides: [{
            element: 'font',
            attributes: {
                size: null
            }
        }]
    };
    j.add('format', {
        requires: ['richcombo', 'styles'],
        init: function (l) {
            var m = l.config,
                n = l.lang.format,
                o = m.format_tags.split(';'),
                p = {};
            for (var q = 0; q < o.length; q++) {
                var r = o[q];
                p[r] = new a.style(m['format_' + r]);
            }
            l.ui.addRichCombo('Format', {
                label: n.label,
                title: n.panelTitle,
                voiceLabel: n.voiceLabel,
                className: 'cke_format',
                multiSelect: false,
                panel: {
                    css: [m.contentsCss, a.getUrl(l.skinPath + 'editor.css')],
                    voiceLabel: n.panelVoiceLabel
                },
                init: function () {
                    this.startGroup(n.panelTitle);
                    for (var s in p) {
                        var t = n['tag_' + s];
                        this.add(s, '<' + s + '>' + t + '</' + s + '>', t);
                    }
                },
                onClick: function (s) {
                    l.focus();
                    l.fire('saveSnapshot');
                    p[s].apply(l.document);
                    l.fire('saveSnapshot');
                },
                onRender: function () {
                    l.on('selectionChange', function (s) {
                        var t = this.getValue(),
                            u = s.data.path;
                        for (var v in p) if (p[v].checkActive(u)) {
                            if (v != t) this.setValue(v, l.lang.format['tag_' + v]);
                            return;
                        }
                        this.setValue('');
                    },
                    this);
                }
            });
        }
    });
    i.format_tags = 'p;h1;h2;h3;h4;h5;h6;pre;address;div';
    i.format_p = {
        element: 'p'
    };
    i.format_div = {
        element: 'div'
    };
    i.format_pre = {
        element: 'pre'
    };
    i.format_address = {
        element: 'address'
    };
    i.format_h1 = {
        element: 'h1'
    };
    i.format_h2 = {
        element: 'h2'
    };
    i.format_h3 = {
        element: 'h3'
    };
    i.format_h4 = {
        element: 'h4'
    };
    i.format_h5 = {
        element: 'h5'
    };
    i.format_h6 = {
        element: 'h6'
    };
    j.add('forms', {
        init: function (l) {
            var m = l.lang;
            l.addCss('form{border: 1px dotted #FF0000;padding: 2px;}');
            var n = function (p, q, r) {
                l.addCommand(q, new a.dialogCommand(q));
                l.ui.addButton(p, {
                    label: m.common[p.charAt(0).toLowerCase() + p.slice(1)],
                    command: q
                });
                a.dialog.add(q, r);
            },
                o = this.path + 'dialogs/';
            n('Form', 'form', o + 'form.js');
            n('Checkbox', 'checkbox', o + 'checkbox.js');
            n('Radio', 'radio', o + 'radio.js');
            n('TextField', 'textfield', o + 'textfield.js');
            n('Textarea', 'textarea', o + 'textarea.js');
            n('Select', 'select', o + 'select.js');
            n('Button', 'button', o + 'button.js');
            n('ImageButton', 'imagebutton', j.getPath('image') + 'dialogs/image.js');
            n('HiddenField', 'hiddenfield', o + 'hiddenfield.js');
            if (l.addMenuItems) l.addMenuItems({
                form: {
                    label: m.form.menu,
                    command: 'form',
                    group: 'form'
                },
                checkbox: {
                    label: m.checkboxAndRadio.checkboxTitle,
                    command: 'checkbox',
                    group: 'checkbox'
                },
                radio: {
                    label: m.checkboxAndRadio.radioTitle,
                    command: 'radio',
                    group: 'radio'
                },
                textfield: {
                    label: m.textfield.title,
                    command: 'textfield',
                    group: 'textfield'
                },
                hiddenfield: {
                    label: m.hidden.title,
                    command: 'hiddenfield',
                    group: 'hiddenfield'
                },
                imagebutton: {
                    label: m.image.titleButton,
                    command: 'imagebutton',
                    group: 'imagebutton'
                },
                button: {
                    label: m.button.title,
                    command: 'button',
                    group: 'button'
                },
                select: {
                    label: m.select.title,
                    command: 'select',
                    group: 'select'
                },
                textarea: {
                    label: m.textarea.title,
                    command: 'textarea',
                    group: 'textarea'
                }
            });
            if (l.contextMenu) {
                l.contextMenu.addListener(function (p) {
                    if (p && p.hasAscendant('form')) return {
                        form: 2
                    };
                });
                l.contextMenu.addListener(function (p) {
                    if (p) {
                        var q = p.getName();
                        if (q == 'select') return {
                            select: 2
                        };
                        if (q == 'textarea') return {
                            textarea: 2
                        };
                        if (q == 'input') {
                            var r = p.getAttribute('type');
                            if (r == 'text' || r == 'password') return {
                                textfield: 2
                            };
                            if (r == 'button' || r == 'submit' || r == 'reset') return {
                                button: 2
                            };
                            if (r == 'checkbox') return {
                                checkbox: 2
                            };
                            if (r == 'radio') return {
                                radio: 2
                            };
                            if (r == 'image') return {
                                imagebutton: 2
                            };
                        }
                        if (q == 'img' && p.getAttribute('_cke_real_element_type') == 'hiddenfield') return {
                            hiddenfield: 2
                        };
                    }
                });
            }
        },
        requires: ['image']
    });
    if (c) h.prototype.hasAttribute = function (l) {
        var o = this;
        var m = o.$.attributes.getNamedItem(l);
        if (o.getName() == 'input') switch (l) {
        case 'class':
            return o.$.className.length > 0;
        case 'checked':
            return !!o.$.checked;
        case 'value':
            var n = o.getAttribute('type');
            if (n == 'checkbox' || n == 'radio') return o.$.value != 'on';
            break;
        default:
        }
        return !! (m && m.specified);
    };
    (function () {
        var l = {
            exec: function (n) {
                n.insertElement(n.document.createElement('hr'));
            }
        },
            m = 'horizontalrule';
        j.add(m, {
            init: function (n) {
                n.addCommand(m, l);
                n.ui.addButton('HorizontalRule', {
                    label: n.lang.horizontalrule,
                    command: m
                });
            }
        });
    })();
    (function () {
        var l = /^[\t\r\n ]*(?:&nbsp;|\xa0)$/,
            m = '{cke_protected}';

        function n(L, M) {
            var N = L.children,
                O = N[N.length - 1];
            if (O) {
                if ((M || !c) && (O.type == 1 && O.name == 'br')) N.pop();
                if (O.type == 3 && l.test(O.value)) N.pop();
            }
        };

        function o(L) {
            if (L.children.length < 1) return true;
            var M = L.children[L.children.length - 1];
            return M.type == 1 && M.name == 'br';
        };

        function p(L) {
            n(L, true);
            if (o(L)) if (c) L.add(new a.htmlParser.text('\xa0'));
            else L.add(new a.htmlParser.element('br', {}));
        };

        function q(L) {
            n(L);
            if (o(L)) L.add(new a.htmlParser.text('\xa0'));
        };
        var r = f,
            s = e.extend({},
        r.$block, r.$listItem, r.$tableContent);
        for (var t in s) if (! ('br' in r[t])) delete s[t];
        delete s.pre;
        var u = {
            attributeNames: [
                [/^on/, '_cke_pa_on']]
        },
            v = {
            elements: {}
        };
        for (t in s) v.elements[t] = p;
        var w = {
            elementNames: [
                [/^cke:/, ''],
                [/^\?xml:namespace$/, '']],
            attributeNames: [
                [/^_cke_(saved|pa)_/, ''],
                [/^_cke.*/, '']],
            elements: {
                $: function (L) {
                    var M = L.attributes;
                    if (M) {
                        var N = ['name', 'href', 'src'],
                            O;
                        for (var P = 0; P < N.length; P++) {
                            O = '_cke_saved_' + N[P];
                            O in M && delete M[N[P]];
                        }
                    }
                },
                embed: function (L) {
                    var M = L.parent;
                    if (M && M.name == 'object') {
                        var N = M.attributes.width,
                            O = M.attributes.height;
                        N && (L.attributes.width = N);
                        O && (L.attributes.height = O);
                    }
                },
                param: function (L) {
                    L.children = [];
                    L.isEmpty = true;
                    return L;
                },
                a: function (L) {
                    if (! (L.children.length || L.attributes.name || L.attributes._cke_saved_name)) return false;
                }
            },
            attributes: {
                'class': function (L, M) {
                    return e.ltrim(L.replace(/(?:^|\s+)cke_[^\s]*/g, '')) || false;
                }
            },
            comment: function (L) {
                if (L.substr(0, m.length) == m) return new a.htmlParser.cdata(decodeURIComponent(L.substr(m.length)));
                return L;
            }
        },
            x = {
            elements: {}
        };
        for (t in s) x.elements[t] = q;
        if (c) w.attributes.style = function (L, M) {
            return L.toLowerCase();
        };
        var y = /<(?:a|area|img|input).*?\s((?:href|src|name)\s*=\s*(?:(?:"[^"]*")|(?:'[^']*')|(?:[^ "'>]+)))/gi;

        function z(L) {
            return L.replace(y, '$& _cke_saved_$1');
        };
        var A = /<(style)(?=[ >])[^>]*>[^<]*<\/\1>/gi,
            B = /<cke:encoded>([^<]*)<\/cke:encoded>/gi,
            C = /(<\/?)((?:object|embed|param).*?>)/gi,
            D = /<cke:param(.*?)\/>/gi;

        function E(L) {
            return '<cke:encoded>' + encodeURIComponent(L) + '</cke:encoded>';
        };

        function F(L) {
            return L.replace(A, E);
        };

        function G(L) {
            return L.replace(C, '$1cke:$2');
        };

        function H(L) {
            return L.replace(D, '<cke:param$1></cke:param>');
        };

        function I(L, M) {
            return decodeURIComponent(M);
        };

        function J(L) {
            return L.replace(B, I);
        };

        function K(L, M) {
            var N = [],
                O = /<\!--\{cke_temp\}(\d*?)-->/g,
                P = [/<!--[\s\S]*?-->/g, /<script[\s\S]*?<\/script>/gi, /<noscript[\s\S]*?<\/noscript>/gi].concat(M);
            for (var Q = 0; Q < P.length; Q++) L = L.replace(P[Q], function (R) {
                R = R.replace(O, function (S, T) {
                    return N[T];
                });
                return '<!--{cke_temp}' + (N.push(R) - 1) + '-->';
            });
            L = L.replace(O, function (R, S) {
                return '<!--' + m + encodeURIComponent(N[S]).replace(/--/g, '%2D%2D') + '-->';
            });
            return L;
        };
        j.add('htmldataprocessor', {
            requires: ['htmlwriter'],
            init: function (L) {
                var M = L.dataProcessor = new a.htmlDataProcessor(L);
                M.writer.forceSimpleAmpersand = L.config.forceSimpleAmpersand;
                M.dataFilter.addRules(u);
                M.dataFilter.addRules(v);
                M.htmlFilter.addRules(w);
                M.htmlFilter.addRules(x);
            }
        });
        a.htmlDataProcessor = function (L) {
            var M = this;
            M.editor = L;
            M.writer = new a.htmlWriter();
            M.dataFilter = new a.htmlParser.filter();
            M.htmlFilter = new a.htmlParser.filter();
        };
        a.htmlDataProcessor.prototype = {
            toHtml: function (L, M) {
                L = K(L, this.editor.config.protectedSource);
                L = z(L);
                if (c) L = F(L);
                L = G(L);
                L = H(L);
                //var N = document.createElement('div');
                //N.innerHTML = 'a' + L;
                //L = N.innerHTML.substr(1);
                if (c) L = J(L);
                var O = a.htmlParser.fragment.fromHtml(L, M),
                    P = new a.htmlParser.basicWriter();
                O.writeHtml(P, this.dataFilter);
                return P.getHtml(true);
            },
            toDataFormat: function (L, M) {
                var N = this.writer,
                    O = a.htmlParser.fragment.fromHtml(L, M);
                N.reset();
                O.writeHtml(N, this.htmlFilter);
                return N.getHtml(true);
            }
        };
    })();
    i.forceSimpleAmpersand = false;
    j.add('image', {
        init: function (l) {
            var m = 'image';
            a.dialog.add(m, this.path + 'dialogs/image.js');
            l.addCommand(m, new a.dialogCommand(m));
            l.ui.addButton('Image', {
                label: l.lang.common.image,
                command: m
            });
            if (l.addMenuItems) l.addMenuItems({
                image: {
                    label: l.lang.image.menu,
                    command: 'image',
                    group: 'image'
                }
            });
            if (l.contextMenu) l.contextMenu.addListener(function (n, o) {
                if (!n || !n.is('img') || n.getAttribute('_cke_realelement')) return null;
                return {
                    image: 2
                };
            });
        }
    });
    i.image_removeLinkByEmptyURL = true;
    (function () {
        var l = {
            ol: 1,
            ul: 1
        };

        function m(r, s) {
            r.getCommand(this.name).setState(s);
        };

        function n(r) {
            var C = this;
            var s = r.data.path.elements,
                t, u, v = r.editor;
            for (var w = 0; w < s.length; w++) {
                if (s[w].getName() == 'li') {
                    u = s[w];
                    continue;
                }
                if (l[s[w].getName()]) {
                    t = s[w];
                    break;
                }
            }
            if (t) if (C.name == 'outdent') return m.call(C, v, 2);
            else {
                while (u && (u = u.getPrevious(d.walker.whitespaces(true)))) if (u.getName && u.getName() == 'li') return m.call(C, v, 2);
                return m.call(C, v, 0);
            }
            if (!C.useIndentClasses && C.name == 'indent') return m.call(C, v, 2);
            var x = r.data.path,
                y = x.block || x.blockLimit;
            if (!y) return m.call(C, v, 0);
            if (C.useIndentClasses) {
                var z = y.$.className.match(C.classNameRegex),
                    A = 0;
                if (z) {
                    z = z[1];
                    A = C.indentClassMap[z];
                }
                if (C.name == 'outdent' && !A || C.name == 'indent' && A == v.config.indentClasses.length) return m.call(C, v, 0);
                return m.call(C, v, 2);
            } else {
                var B = parseInt(y.getStyle(C.indentCssProperty), 10);
                if (isNaN(B)) B = 0;
                if (B <= 0) return m.call(C, v, 0);
                return m.call(C, v, 2);
            }
        };

        function o(r, s, t) {
            var u = s.startContainer,
                v = s.endContainer;
            while (u && !u.getParent().equals(t)) u = u.getParent();
            while (v && !v.getParent().equals(t)) v = v.getParent();
            if (!u || !v) return;
            var w = u,
                x = [],
                y = false;
            while (!y) {
                if (w.equals(v)) y = true;
                x.push(w);
                w = w.getNext();
            }
            if (x.length < 1) return;
            var z = t.getParents(true);
            for (var A = 0; A < z.length; A++) if (z[A].getName && l[z[A].getName()]) {
                t = z[A];
                break;
            }
            var B = this.name == 'indent' ? 1 : -1,
            C = x[0],
            D = x[x.length - 1],
            E = {},
            F = j.list.listToArray(t, E),
            G = F[D.getCustomData('listarray_index')].indent;
            for (A = C.getCustomData('listarray_index'); A <= D.getCustomData('listarray_index'); A++) F[A].indent += B;
            for (A = D.getCustomData('listarray_index') + 1; A < F.length && F[A].indent > G; A++) F[A].indent += B;
            var H = j.list.arrayToList(F, E, null, r.config.enterMode, 0);
            if (this.name == 'outdent') {
                var I;
                if ((I = t.getParent()) && (I.is('li'))) {
                    var J = H.listNode.getChildren(),
                        K = [],
                        L = J.count(),
                        M;
                    for (A = L - 1; A >= 0; A--) if ((M = J.getItem(A)) && (M.is && M.is('li'))) K.push(M);
                }
            }
            if (H) H.listNode.replace(t);
            if (K && K.length) for (A = 0; A < K.length; A++) {
                var N = K[A],
                    O = N;
                while ((O = O.getNext()) && (O.is && O.getName() in l)) N.append(O);
                N.insertAfter(I);
            }
            h.clearAllMarkers(E);
        };

        function p(r, s) {
            var A = this;
            var t = s.createIterator(),
                u = r.config.enterMode;
            t.enforceRealBlocks = true;
            t.enlargeBr = u != 2;
            var v;
            while (v = t.getNextParagraph()) if (A.useIndentClasses) {
                var w = v.$.className.match(A.classNameRegex),
                    x = 0;
                if (w) {
                    w = w[1];
                    x = A.indentClassMap[w];
                }
                if (A.name == 'outdent') x--;
                elsex++;
                x = Math.min(x, r.config.indentClasses.length);
                x = Math.max(x, 0);
                var y = e.ltrim(v.$.className.replace(A.classNameRegex, ''));
                if (x < 1) v.$.className = y;
                else v.addClass(r.config.indentClasses[x - 1]);
            } else {
                var z = parseInt(v.getStyle(A.indentCssProperty), 10);
                if (isNaN(z)) z = 0;
                z += (A.name == 'indent' ? 1 : -1) * (r.config.indentOffset);
                z = Math.max(z, 0);
                z = Math.ceil(z / r.config.indentOffset) * r.config.indentOffset;
                v.setStyle(A.indentCssProperty, z ? z + r.config.indentUnit : '');
                if (v.getAttribute('style') === '') v.removeAttribute('style');
            }
        };

        function q(r, s) {
            var u = this;
            u.name = s;
            u.useIndentClasses = r.config.indentClasses && r.config.indentClasses.length > 0;
            if (u.useIndentClasses) {
                u.classNameRegex = new RegExp('(?:^|\\s+)(' + r.config.indentClasses.join('|') + ')(?=$|\\s)');
                u.indentClassMap = {};
                for (var t = 0; t < r.config.indentClasses.length; t++) u.indentClassMap[r.config.indentClasses[t]] = t + 1;
            } else u.indentCssProperty = r.config.contentsLangDirection == 'ltr' ? 'margin-left' : 'margin-right';
        };
        q.prototype = {
            exec: function (r) {
                var s = r.getSelection(),
                    t = s && s.getRanges()[0];
                if (!s || !t) return;
                var u = s.createBookmarks(true),
                    v = t.getCommonAncestor();
                while (v && !(v.type == 1 && l[v.getName()])) v = v.getParent();
                if (v) o.call(this, r, t, v);
                else p.call(this, r, t);
                r.focus();
                r.forceNextSelectionCheck();
                s.selectBookmarks(u);
            }
        };
        j.add('indent', {
            init: function (r) {
                var s = new q(r, 'indent'),
                    t = new q(r, 'outdent');
                r.addCommand('indent', s);
                r.addCommand('outdent', t);
                r.ui.addButton('Indent', {
                    label: r.lang.indent,
                    command: 'indent'
                });
                r.ui.addButton('Outdent', {
                    label: r.lang.outdent,
                    command: 'outdent'
                });
                r.on('selectionChange', e.bind(n, s));
                r.on('selectionChange', e.bind(n, t));
            },
            requires: ['domiterator', 'list']
        });
    })();
    e.extend(i, {
        indentOffset: 40,
        indentUnit: 'px',
        indentClasses: null
    });
    (function () {
        var l = /(-moz-|-webkit-|start|auto)/i;

        function m(p, q) {
            var r = q.block || q.blockLimit;
            if (!r || r.getName() == 'body') return 2;
            var s = r.getComputedStyle('text-align').replace(l, '');
            if (!s && this.isDefaultAlign || s == this.value) return 1;
            return 2;
        };

        function n(p) {
            var q = p.editor.getCommand(this.name);
            q.state = m.call(this, p.editor, p.data.path);
            q.fire('state');
        };

        function o(p, q, r) {
            var u = this;
            u.name = q;
            u.value = r;
            var s = p.config.contentsLangDirection;
            u.isDefaultAlign = r == 'left' && s == 'ltr' || r == 'right' && s == 'rtl';
            var t = p.config.justifyClasses;
            if (t) {
                switch (r) {
                case 'left':
                    u.cssClassName = t[0];
                    break;
                case 'center':
                    u.cssClassName = t[1];
                    break;
                case 'right':
                    u.cssClassName = t[2];
                    break;
                case 'justify':
                    u.cssClassName = t[3];
                    break;
                }
                u.cssClassRegex = new RegExp('(?:^|\\s+)(?:' + t.join('|') + ')(?=$|\\s)');
            }
        };
        o.prototype = {
            exec: function (p) {
                var y = this;
                var q = p.getSelection();
                if (!q) return;
                var r = q.createBookmarks(),
                    s = q.getRanges(),
                    t = y.cssClassName,
                    u, v;
                for (var w = s.length - 1; w >= 0; w--) {
                    u = s[w].createIterator();
                    while (v = u.getNextParagraph()) {
                        v.removeAttribute('align');
                        if (t) {
                            var x = v.$.className = e.ltrim(v.$.className.replace(y.cssClassRegex, ''));
                            if (y.state == 2 && !y.isDefaultAlign) v.addClass(t);
                            else if (!x) v.removeAttribute('class');
                        } else if (y.state == 2 && !y.isDefaultAlign) v.setStyle('text-align', y.value);
                        else v.removeStyle('text-align');
                    }
                }
                p.focus();
                p.forceNextSelectionCheck();
                q.selectBookmarks(r);
            }
        };
        j.add('justify', {
            init: function (p) {
                var q = new o(p, 'justifyleft', 'left'),
                    r = new o(p, 'justifycenter', 'center'),
                    s = new o(p, 'justifyright', 'right'),
                    t = new o(p, 'justifyblock', 'justify');
                p.addCommand('justifyleft', q);
                p.addCommand('justifycenter', r);
                p.addCommand('justifyright', s);
                p.addCommand('justifyblock', t);
                p.ui.addButton('JustifyLeft', {
                    label: p.lang.justify.left,
                    command: 'justifyleft'
                });
                p.ui.addButton('JustifyCenter', {
                    label: p.lang.justify.center,
                    command: 'justifycenter'
                });
                p.ui.addButton('JustifyRight', {
                    label: p.lang.justify.right,
                    command: 'justifyright'
                });
                p.ui.addButton('JustifyBlock', {
                    label: p.lang.justify.block,
                    command: 'justifyblock'
                });
                p.on('selectionChange', e.bind(n, q));
                p.on('selectionChange', e.bind(n, s));
                p.on('selectionChange', e.bind(n, r));
                p.on('selectionChange', e.bind(n, t));
            },
            requires: ['domiterator']
        });
    })();
    e.extend(i, {
        justifyClasses: null
    });
    j.add('keystrokes', {
        beforeInit: function (l) {
            l.keystrokeHandler = new a.keystrokeHandler(l);
            l.specialKeys = {};
        },
        init: function (l) {
            var m = l.config.keystrokes,
                n = l.config.blockedKeystrokes,
                o = l.keystrokeHandler.keystrokes,
                p = l.keystrokeHandler.blockedKeystrokes;
            for (var q = 0; q < m.length; q++) o[m[q][0]] = m[q][1];
            for (q = 0; q < n.length; q++) p[n[q]] = 1;
        }
    });
    a.keystrokeHandler = function (l) {
        var m = this;
        if (l.keystrokeHandler) return l.keystrokeHandler;
        m.keystrokes = {};
        m.blockedKeystrokes = {};
        m._ = {
            editor: l
        };
        return m;
    };
    (function () {
        var l, m = function (o) {
            o = o.data;
            var p = o.getKeystroke(),
                q = this.keystrokes[p],
                r = this._.editor;
            l = r.fire('key', {
                keyCode: p
            }) === true;
            if (!l) {
                if (q) {
                    var s = {
                        from: 'keystrokeHandler'
                    };
                    l = r.execCommand(q, s) !== false;
                }
                if (!l) {
                    var t = r.specialKeys[p];
                    l = t && t(r) === true;
                    if (!l) l = !!this.blockedKeystrokes[p];
                }
            }
            if (l) o.preventDefault(true);
            return !l;
        },
            n = function (o) {
            if (l) {
                l = false;
                o.data.preventDefault(true);
            }
        };
        a.keystrokeHandler.prototype = {
            attach: function (o) {
                o.on('keydown', m, this);
                if (b.opera || b.gecko && b.mac) o.on('keypress', n, this);
            }
        };
    })();
    i.blockedKeystrokes = [1000 + 66, 1000 + 73, 1000 + 85];
    i.keystrokes = [
        [4000 + 121, 'toolbarFocus'],
        [4000 + 122, 'elementsPathFocus'],
        [2000 + 121, 'contextMenu'],
        [1000 + 90, 'undo'],
        [1000 + 89, 'redo'],
        [1000 + 2000 + 90, 'redo'],
        [1000 + 76, 'link'],
        [1000 + 66, 'bold'],
        [1000 + 73, 'italic'],
        [1000 + 85, 'underline'],
        [4000 + 109, 'toolbarCollapse']];
    j.add('link', {
        init: function (l) {
            l.addCommand('link', new a.dialogCommand('link'));
            l.addCommand('anchor', new a.dialogCommand('anchor'));
            l.addCommand('unlink', new a.unlinkCommand());
            l.ui.addButton('Link', {
                label: l.lang.link.toolbar,
                command: 'link'
            });
            l.ui.addButton('Unlink', {
                label: l.lang.unlink,
                command: 'unlink'
            });
            l.ui.addButton('Anchor', {
                label: l.lang.anchor.toolbar,
                command: 'anchor'
            });
            a.dialog.add('link', this.path + 'dialogs/link.js');
            a.dialog.add('anchor', this.path + 'dialogs/anchor.js');
            l.addCss('img.cke_anchor{background-image: url(' + a.getUrl(this.path + 'images/anchor.gif') + ');' + 'background-position: center center;' + 'background-repeat: no-repeat;' + 'border: 1px solid #a9a9a9;' + 'width: 18px;' + 'height: 18px;' + '}\n' + 'a.cke_anchor' + '{' + 'background-image: url(' + a.getUrl(this.path + 'images/anchor.gif') + ');' + 'background-position: 0 center;' + 'background-repeat: no-repeat;' + 'border: 1px solid #a9a9a9;' + 'padding-left: 18px;' + '}');
            l.on('selectionChange', function (m) {
                var n = l.getCommand('unlink'),
                    o = m.data.path.lastElement.getAscendant('a', true);
                if (o && o.getName() == 'a' && o.getAttribute('href')) n.setState(2);
                else n.setState(0);
            });
            if (l.addMenuItems) l.addMenuItems({
                anchor: {
                    label: l.lang.anchor.menu,
                    command: 'anchor',
                    group: 'anchor'
                },
                link: {
                    label: l.lang.link.menu,
                    command: 'link',
                    group: 'link',
                    order: 1
                },
                unlink: {
                    label: l.lang.unlink,
                    command: 'unlink',
                    group: 'link',
                    order: 5
                }
            });
            if (l.contextMenu) l.contextMenu.addListener(function (m, n) {
                if (!m) return null;
                var o = m.is('img') && m.getAttribute('_cke_real_element_type') == 'anchor';
                if (!o) {
                    if (! (m = m.getAscendant('a', true))) return null;
                    o = m.getAttribute('name') && !m.getAttribute('href');
                }
                return o ? {
                    anchor: 2
                } : {
                    link: 2,
                    unlink: 2
                };
            });
        },
        afterInit: function (l) {
            var m = l.dataProcessor,
                n = m && m.dataFilter;
            if (n) n.addRules({
                elements: {
                    a: function (o) {
                        var p = o.attributes;
                        if (p.name && !p.href) return l.createFakeParserElement(o, 'cke_anchor', 'anchor');
                    }
                }
            });
        },
        requires: ['fakeobjects']
    });
    a.unlinkCommand = function () {};
    a.unlinkCommand.prototype = {
        exec: function (l) {
            var m = l.getSelection(),
                n = m.createBookmarks(),
                o = m.getRanges(),
                p, q;
            for (var r = 0; r < o.length; r++) {
                p = o[r].getCommonAncestor(true);
                q = p.getAscendant('a', true);
                if (!q) continue;
                o[r].selectNodeContents(q);
            }
            m.selectRanges(o);
            l.document.$.execCommand('unlink', false, null);
            m.selectBookmarks(n);
        }
    };
    e.extend(i, {
        linkShowAdvancedTab: true,
        linkShowTargetTab: true
    });
    (function () {
        var l = {
            ol: 1,
            ul: 1
        },
            m = /^[\n\r\t ]*$/;
        j.list = {
            listToArray: function (t, u, v, w, x) {
                if (!l[t.getName()]) return [];
                if (!w) w = 0;
                if (!v) v = [];
                for (var y = 0, z = t.getChildCount(); y < z; y++) {
                    var A = t.getChild(y);
                    if (A.$.nodeName.toLowerCase() != 'li') continue;
                    var B = {
                        parent: t,
                        indent: w,
                        contents: []
                    };
                    if (!x) {
                        B.grandparent = t.getParent();
                        if (B.grandparent && B.grandparent.$.nodeName.toLowerCase() == 'li') B.grandparent = B.grandparent.getParent();
                    } else B.grandparent = x;
                    if (u) h.setMarker(u, A, 'listarray_index', v.length);
                    v.push(B);
                    for (var C = 0, D = A.getChildCount(); C < D; C++) {
                        var E = A.getChild(C);
                        if (E.type == 1 && l[E.getName()]) j.list.listToArray(E, u, v, w + 1, B.grandparent);
                        else B.contents.push(E);
                    }
                }
                return v;
            },
            arrayToList: function (t, u, v, w) {
                if (!v) v = 0;
                if (!t || t.length < v + 1) return null;
                var x = t[v].parent.getDocument(),
                    y = new d.documentFragment(x),
                    z = null,
                    A = v,
                    B = Math.max(t[v].indent, 0),
                    C = null,
                    D = w == 1 ? 'p' : 'div';
                for (;;) {
                    var E = t[A];
                    if (E.indent == B) {
                        if (!z || t[A].parent.getName() != z.getName()) {
                            z = t[A].parent.clone(false, true);
                            y.append(z);
                        }
                        C = z.append(x.createElement('li'));
                        for (var F = 0; F < E.contents.length; F++) C.append(E.contents[F].clone(true, true));
                        A++;
                    } else if (E.indent == Math.max(B, 0) + 1) {
                        var G = j.list.arrayToList(t, null, A, w);
                        C.append(G.listNode);
                        A = G.nextIndex;
                    } else if (E.indent == -1 && !v && E.grandparent) {
                        C;
                        if (l[E.grandparent.getName()]) C = x.createElement('li');
                        else if (w != 2 && E.grandparent.getName() != 'td') C = x.createElement(D);
                        else C = new d.documentFragment(x);
                        for (F = 0; F < E.contents.length; F++) C.append(E.contents[F].clone(true, true));
                        if (C.type == 11 && A != t.length - 1) {
                            if (C.getLast() && C.getLast().type == 1 && C.getLast().getAttribute('type') == '_moz') C.getLast().remove();
                            C.appendBogus();
                        }
                        if (C.type == 1 && C.getName() == D && C.$.firstChild) {
                            C.trim();
                            var H = C.getFirst();
                            if (H.type == 1 && H.isBlockBoundary()) {
                                var I = new d.documentFragment(x);
                                C.moveChildren(I);
                                C = I;
                            }
                        }
                        var J = C.$.nodeName.toLowerCase();
                        if (!c && (J == 'div' || J == 'p')) C.appendBogus();
                        y.append(C);
                        z = null;
                        A++;
                    } else return null;
                    if (t.length <= A || Math.max(t[A].indent, 0) < B) break;
                }
                if (u) {
                    var K = y.getFirst();
                    while (K) {
                        if (K.type == 1) h.clearMarkers(u, K);
                        K = K.getNextSourceNode();
                    }
                }
                return {
                    listNode: y,
                    nextIndex: A
                };
            }
        };

        function n(t, u) {
            t.getCommand(this.name).setState(u);
        };

        function o(t) {
            var u = t.data.path,
                v = u.blockLimit,
                w = u.elements,
                x;
            for (var y = 0; y < w.length && (x = w[y]) && (!x.equals(v)); y++) if (l[w[y].getName()]) return n.call(this, t.editor, this.type == w[y].getName() ? 1 : 2);
            return n.call(this, t.editor, 2);
        };

        function p(t, u, v, w) {
            var x = j.list.listToArray(u.root, v),
                y = [];
            for (var z = 0; z < u.contents.length; z++) {
                var A = u.contents[z];
                A = A.getAscendant('li', true);
                if (!A || A.getCustomData('list_item_processed')) continue;
                y.push(A);
                h.setMarker(v, A, 'list_item_processed', true);
            }
            var B = u.root.getDocument().createElement(this.type);
            for (z = 0; z < y.length; z++) {
                var C = y[z].getCustomData('listarray_index');
                x[C].parent = B;
            }
            var D = j.list.arrayToList(x, v, null, t.config.enterMode),
                E, F = D.listNode.getChildCount();
            for (z = 0; z < F && (E = D.listNode.getChild(z)); z++) if (E.getName() == this.type) w.push(E);
            D.listNode.replace(u.root);
        };

        function q(t, u, v) {
            var w = u.contents,
                x = u.root.getDocument(),
                y = [];
            if (w.length == 1 && w[0].equals(u.root)) {
                var z = x.createElement('div');
                w[0].moveChildren && w[0].moveChildren(z);
                w[0].append(z);
                w[0] = z;
            }
            var A = u.contents[0].getParent();
            for (var B = 0; B < w.length; B++) A = A.getCommonAncestor(w[B].getParent());
            for (B = 0; B < w.length; B++) {
                var C = w[B],
                    D;
                while (D = C.getParent()) {
                    if (D.equals(A)) {
                        y.push(C);
                        break;
                    }
                    C = D;
                }
            }
            if (y.length < 1) return;
            var E = y[y.length - 1].getNext(),
                F = x.createElement(this.type);
            v.push(F);
            while (y.length) {
                var G = y.shift(),
                    H = x.createElement('li');
                G.moveChildren(H);
                G.remove();
                H.appendTo(F);
                if (!c) H.appendBogus();
            }
            if (E) F.insertBefore(E);
            else F.appendTo(A);
        };

        function r(t, u, v) {
            var w = j.list.listToArray(u.root, v),
                x = [];
            for (var y = 0; y < u.contents.length; y++) {
                var z = u.contents[y];
                z = z.getAscendant('li', true);
                if (!z || z.getCustomData('list_item_processed')) continue;
                x.push(z);
                h.setMarker(v, z, 'list_item_processed', true);
            }
            var A = null;
            for (y = 0; y < x.length; y++) {
                var B = x[y].getCustomData('listarray_index');
                w[B].indent = -1;
                A = B;
            }
            for (y = A + 1; y < w.length; y++) if (w[y].indent > w[y - 1].indent + 1) {
                var C = w[y - 1].indent + 1 - w[y].indent,
                    D = w[y].indent;
                while (w[y] && w[y].indent >= D) {
                    w[y].indent += C;
                    y++;
                }
                y--;
            }
            var E = j.list.arrayToList(w, v, null, t.config.enterMode),
                F = E.listNode,
                G, H;

            function I(K) {
                if ((G = F[K ? 'getFirst' : 'getLast']()) && (!(G.is && G.isBlockBoundary()) && (H = u.root[K ? 'getPrevious' : 'getNext'](d.walker.whitespaces(true))) && (!(H.is && H.isBlockBoundary({
                    br: 1
                }))))) t.document.createElement('br')[K ? 'insertBefore' : 'insertAfter'](G);
            };
            I(true);
            I();
            var J = u.root.getParent();
            F.replace(u.root);
        };

        function s(t, u) {
            this.name = t;
            this.type = u;
        };
        s.prototype = {
            exec: function (t) {
                t.focus();
                var u = t.document,
                    v = t.getSelection(),
                    w = v && v.getRanges();
                if (!w || w.length < 1) return;
                if (this.state == 2) {
                    var x = u.getBody();
                    x.trim();
                    if (!x.getFirst()) {
                        var y = u.createElement(t.config.enterMode == 1 ? 'p' : t.config.enterMode == 3 ? 'div' : 'br');
                        y.appendTo(x);
                        w = [new d.range(u)];
                        if (y.is('br')) {
                            w[0].setStartBefore(y);
                            w[0].setEndAfter(y);
                        } else w[0].selectNodeContents(y);
                        v.selectRanges(w);
                    } else {
                        var z = w.length == 1 && w[0],
                            A = z && z.getEnclosedNode();
                        if (A && A.is && this.type == A.getName()) n.call(this, t, 1);
                    }
                }
                var B = v.createBookmarks(true),
                    C = [],
                    D = {};
                while (w.length > 0) {
                    z = w.shift();
                    var E = z.getBoundaryNodes(),
                        F = E.startNode,
                        G = E.endNode;
                    if (F.type == 1 && F.getName() == 'td') z.setStartAt(E.startNode, 1);
                    if (G.type == 1 && G.getName() == 'td') z.setEndAt(E.endNode, 2);
                    var H = z.createIterator(),
                        I;
                    H.forceBrBreak = this.state == 2;
                    while (I = H.getNextParagraph()) {
                        var J = new d.elementPath(I),
                            K = null,
                            L = false,
                            M = J.blockLimit,
                            N;
                        for (var O = 0; O < J.elements.length && (N = J.elements[O]) && (!N.equals(M)); O++) if (l[N.getName()]) {
                            M.removeCustomData('list_group_object');
                            var P = N.getCustomData('list_group_object');
                            if (P) P.contents.push(I);
                            else {
                                P = {
                                    root: N,
                                    contents: [I]
                                };
                                C.push(P);
                                h.setMarker(D, N, 'list_group_object', P);
                            }
                            L = true;
                            break;
                        }
                        if (L) continue;
                        var Q = M;
                        if (Q.getCustomData('list_group_object')) Q.getCustomData('list_group_object').contents.push(I);
                        else {
                            P = {
                                root: Q,
                                contents: [I]
                            };
                            h.setMarker(D, Q, 'list_group_object', P);
                            C.push(P);
                        }
                    }
                }
                var R = [];
                while (C.length > 0) {
                    P = C.shift();
                    if (this.state == 2) {
                        if (l[P.root.getName()]) p.call(this, t, P, D, R);
                        else q.call(this, t, P, R);
                    } else if (this.state == 1 && l[P.root.getName()]) r.call(this, t, P, D);
                }
                for (O = 0; O < R.length; O++) {
                    K = R[O];
                    var S, T = this;
                    (S = function (U) {
                        var V = K[U ? 'getPrevious' : 'getNext'](d.walker.whitespaces(true));
                        if (V && V.getName && V.getName() == T.type) {
                            V.remove();
                            V.moveChildren(K, U ? true : false);
                        }
                    })();
                    S(true);
                }
                h.clearAllMarkers(D);
                v.selectBookmarks(B);
                t.focus();
            }
        };
        j.add('list', {
            init: function (t) {
                var u = new s('numberedlist', 'ol'),
                    v = new s('bulletedlist', 'ul');
                t.addCommand('numberedlist', u);
                t.addCommand('bulletedlist', v);
                t.ui.addButton('NumberedList', {
                    label: t.lang.numberedlist,
                    command: 'numberedlist'
                });
                t.ui.addButton('BulletedList', {
                    label: t.lang.bulletedlist,
                    command: 'bulletedlist'
                });
                t.on('selectionChange', e.bind(o, u));
                t.on('selectionChange', e.bind(o, v));
            },
            requires: ['domiterator']
        });
    })();
    (function () {
        function l(q) {
            if (!q || q.type != 1 || q.getName() != 'form') return [];
            var r = [],
                s = ['style', 'className'];
            for (var t = 0; t < s.length; t++) {
                var u = s[t],
                    v = q.$.elements.namedItem(u);
                if (v) {
                    var w = new h(v);
                    r.push([w, w.nextSibling]);
                    w.remove();
                }
            }
            return r;
        };

        function m(q, r) {
            if (!q || q.type != 1 || q.getName() != 'form') return;
            if (r.length > 0) for (var s = r.length - 1; s >= 0; s--) {
                var t = r[s][0],
                    u = r[s][1];
                if (u) t.insertBefore(u);
                else t.appendTo(q);
            }
        };

        function n(q, r) {
            var s = l(q),
                t = {},
                u = q.$;
            if (!r) {
                t['class'] = u.className || '';
                u.className = '';
            }
            t.inline = u.style.cssText || '';
            if (!r) u.style.cssText = 'position: static; overflow: visible';
            m(s);
            return t;
        };

        function o(q, r) {
            var s = l(q),
                t = q.$;
            if ('class' in r) t.className = r['class'];
            if ('inline' in r) t.style.cssText = r.inline;
            m(s);
        };

        function p(q, r) {
            return function () {
                var s = q.getViewPaneSize();
                r.resize(s.width, s.height, null, true);
            };
        };
        j.add('maximize', {
            init: function (q) {
                var r = q.lang,
                    s = a.document,
                    t = s.getWindow(),
                    u, v, w, x = p(t, q),
                    y = 2;
                q.addCommand('maximize', {
                    modes: {
                        wysiwyg: 1,
                        source: 1
                    },
                    exec: function () {
                        var I = this;
                        var z = q.container.getChild([0, 0]),
                            A = q.getThemeSpace('contents');
                        if (q.mode == 'wysiwyg') {
                            u = q.getSelection().getRanges();
                            v = t.getScrollPosition();
                        } else {
                            var B = q.textarea.$;
                            u = !c && [B.selectionStart, B.selectionEnd];
                            v = [B.scrollLeft, B.scrollTop];
                        }
                        if (I.state == 2) {
                            t.on('resize', x);
                            w = t.getScrollPosition();
                            var C = q.container;
                            while (C = C.getParent()) {
                                C.setCustomData('maximize_saved_styles', n(C));
                                C.setStyle('z-index', q.config.baseFloatZIndex - 1);
                            }
                            A.setCustomData('maximize_saved_styles', n(A, true));
                            z.setCustomData('maximize_saved_styles', n(z, true));
                            if (c) s.$.documentElement.style.overflow = s.getBody().$.style.overflow = 'hidden';
                            else s.getBody().setStyles({
                                overflow: 'hidden',
                                width: '0px',
                                height: '0px'
                            });
                            t.$.scrollTo(0, 0);
                            var D = t.getViewPaneSize();
                            z.setStyle('position', 'absolute');
                            z.$.offsetLeft;
                            z.setStyles({
                                'z-index': q.config.baseFloatZIndex - 1,
                                left: '0px',
                                top: '0px'
                            });
                            q.resize(D.width, D.height, null, true);
                            var E = z.getDocumentPosition();
                            z.setStyles({
                                left: -1 * E.x + 'px',
                                top: -1 * E.y + 'px'
                            });
                            z.addClass('cke_maximized');
                        } else if (I.state == 1) {
                            t.removeListener('resize', x);
                            var F = [A, z];
                            for (var G = 0; G < F.length; G++) {
                                o(F[G], F[G].getCustomData('maximize_saved_styles'));
                                F[G].removeCustomData('maximize_saved_styles');
                            }
                            C = q.container;
                            while (C = C.getParent()) {
                                o(C, C.getCustomData('maximize_saved_styles'));
                                C.removeCustomData('maximize_saved_styles');
                            }
                            t.$.scrollTo(w.x, w.y);
                            z.removeClass('cke_maximized');
                            q.fire('resize');
                        }
                        I.toggleState();
                        if (q.mode == 'wysiwyg') {
                            q.getSelection().selectRanges(u);
                            var H = q.getSelection().getStartElement();
                            if (H) H.scrollIntoView(true);
                            else t.$.scrollTo(v.x, v.y);
                        } else {
                            if (u) {
                                B.selectionStart = u[0];
                                B.selectionEnd = u[1];
                            }
                            B.scrollLeft = v[0];
                            B.scrollTop = v[1];
                        }
                        u = v = null;
                        y = I.state;
                    },
                    canUndo: false
                });
                q.ui.addButton('Maximize', {
                    label: r.maximize,
                    command: 'maximize'
                });
                q.on('mode', function () {
                    q.getCommand('maximize').setState(y);
                },
                null, null, 100);
            }
        });
    })();
    j.add('newpage', {
        init: function (l) {
            l.addCommand('newpage', {
                modes: {
                    wysiwyg: 1,
                    source: 1
                },
                exec: function (m) {
                    var n = this;

                    function o() {
                        setTimeout(function () {
                            m.fire('afterCommandExec', {
                                name: n.name,
                                command: n
                            });
                        },
                        500);
                    };
                    if (m.mode == 'wysiwyg') m.on('contentDom', function (p) {
                        p.removeListener();
                        o();
                    });
                    m.setData(m.config.newpage_html);
                    m.focus();
                    if (m.mode == 'source') o();
                },
                async: true
            });
            l.ui.addButton('NewPage', {
                label: l.lang.newPage,
                command: 'newpage'
            });
        }
    });
    i.newpage_html = '';
    j.add('pagebreak', {
        init: function (l) {
            l.addCommand('pagebreak', j.pagebreakCmd);
            l.ui.addButton('PageBreak', {
                label: l.lang.pagebreak,
                command: 'pagebreak'
            });
            l.addCss('img.cke_pagebreak{background-image: url(' + a.getUrl(this.path + 'images/pagebreak.gif') + ');' + 'background-position: center center;' + 'background-repeat: no-repeat;' + 'clear: both;' + 'display: block;' + 'float: none;' + 'width: 100%;' + 'border-top: #999999 1px dotted;' + 'border-bottom: #999999 1px dotted;' + 'height: 5px;' + '}');
        },
        afterInit: function (l) {
            var m = l.dataProcessor,
                n = m && m.dataFilter;
            if (n) n.addRules({
                elements: {
                    div: function (o) {
                        var p = o.attributes.style,
                            q = p && o.children.length == 1 && o.children[0],
                            r = q && q.name == 'span' && q.attributes.style;
                        if (r && /page-break-after\s*:\s*always/i.test(p) && /display\s*:\s*none/i.test(r)) return l.createFakeParserElement(o, 'cke_pagebreak', 'div');
                    }
                }
            });
        },
        requires: ['fakeobjects']
    });
    j.pagebreakCmd = {
        exec: function (l) {
            var m = h.createFromHtml('<div style="page-break-after: always;"><span style="display: none;">&nbsp;</span></div>');
            m = l.createFakeElement(m, 'cke_pagebreak', 'div');
            var n = l.getSelection().getRanges();
            for (var o, p = 0; p < n.length; p++) {
                o = n[p];
                if (p > 0) m = m.clone(true);
                o.splitBlock('p');
                o.insertNode(m);
            }
        }
    };
    j.add('pastefromword', {
        init: function (l) {
            l.addCommand('pastefromword', new a.dialogCommand('pastefromword'));
            l.ui.addButton('PasteFromWord', {
                label: l.lang.pastefromword.toolbar,
                command: 'pastefromword'
            });
            a.dialog.add('pastefromword', this.path + 'dialogs/pastefromword.js');
        }
    });
    i.pasteFromWordIgnoreFontFace = true;
    i.pasteFromWordRemoveStyle = false;
    i.pasteFromWordKeepsStructure = false;
    (function () {
        var l = {
            exec: function (n) {
                if (a.getClipboardData() === false || !window.clipboardData) {
                    n.openDialog('pastetext');
                    return;
                }
                n.insertText(window.clipboardData.getData('Text'));
            }
        };
        j.add('pastetext', {
            init: function (n) {
                var o = 'pastetext',
                    p = n.addCommand(o, l);
                n.ui.addButton('PasteText', {
                    label: n.lang.pasteText.button,
                    command: o
                });
                a.dialog.add(o, a.getUrl(this.path + 'dialogs/pastetext.js'));
                if (n.config.forcePasteAsPlainText) n.on('beforePaste', function (q) {
                    if (n.mode == 'wysiwyg') {
                        setTimeout(function () {
                            p.exec();
                        },
                        0);
                        q.cancel();
                    }
                },
                null, null, 20);
            },
            requires: ['clipboard']
        });
        var m;
        a.getClipboardData = function () {
            if (!c) return false;
            var n = a.document,
                o = n.getBody();
            if (!m) {
                m = n.createElement('div', {
                    attributes: {
                        id: 'cke_hiddenDiv'
                    },
                    styles: {
                        position: 'absolute',
                        visibility: 'hidden',
                        overflow: 'hidden',
                        width: '1px',
                        height: '1px'
                    }
                });
                m.setHtml('');
                m.appendTo(o);
            }
            var p = false,
                q = function () {
                p = true;
            };
            o.on('paste', q);
            var r = o.$.createTextRange();
            r.moveToElementText(m.$);
            r.execCommand('Paste');
            var s = m.getHtml();
            m.setHtml('');
            o.removeListener('paste', q);
            return p && s;
        };
    })();
    a.editor.prototype.insertText = function (l) {
        l = e.htmlEncode(l);
        l = l.replace(/(?:\r\n)|\n|\r/g, '<br>');
        this.insertHtml(l);
    };
    i.forcePasteAsPlainText = false;
    j.add('popup');
    e.extend(a.editor.prototype, {
        popup: function (l, m, n) {
            m = m || '80%';
            n = n || '70%';
            if (typeof m == 'string' && m.length > 1 && m.substr(m.length - 1, 1) == '%') m = parseInt(window.screen.width * parseInt(m, 10) / 100, 10);
            if (typeof n == 'string' && n.length > 1 && n.substr(n.length - 1, 1) == '%') n = parseInt(window.screen.height * parseInt(n, 10) / 100, 10);
            if (m < 640) m = 640;
            if (n < 420) n = 420;
            var o = parseInt((window.screen.height - n) / (2), 10),
                p = parseInt((window.screen.width - m) / (2), 10),
                q = 'location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,resizable=yes,scrollbars=yes,width=' + m + ',height=' + n + ',top=' + o + ',left=' + p,
                r = window.open('', null, q, true);
            if (!r) return false;
            try {
                r.moveTo(p, o);
                r.resizeTo(m, n);
                r.focus();
                r.location.href = l;
            } catch(s) {
                r = window.open(l, null, q, true);
            }
            return true;
        }
    });
    (function () {
        var l = {
            modes: {
                wysiwyg: 1,
                source: 1
            },
            canUndo: false,
            exec: function (n) {
                var o, p = c && document.domain != window.location.hostname;
                if (n.config.fullPage) o = n.getData();
                else {
                    var q = '<body ',
                        r = a.document.getBody(),
                        s = n.config.baseHref.length > 0 ? '<base href="' + n.config.baseHref + '" _cktemp="true"></base>' : '';
                    if (r.getAttribute('id')) q += 'id="' + r.getAttribute('id') + '" ';
                    if (r.getAttribute('class')) q += 'class="' + r.getAttribute('class') + '" ';
                    q += '>';
                     o = n.config.docType + '<html dir="' + n.config.contentsLangDirection + '">' + '<head>' + s + '<title>' + n.lang.preview + '</title>' + '<link href="' + n.config.contentsCss + '" type="text/css" rel="stylesheet" _cktemp="true"/>' ;
                    
                     if (n.config.contentsCss.constructor.toString().indexOf("Array") == -1){
                        o+= '<link href="' + n.config.contentsCss + '" type="text/css" rel="stylesheet" _cktemp="true"/>';
                     }else{
                        for(i=0;i<n.config.contentsCss.length;i++){
                        	o+= '<link href="' + n.config.contentsCss[i] + '" type="text/css" rel="stylesheet" _cktemp="true"/>';
                        }
                     }
                     o+= '</head>' + q + n.getData() + '</body></html>';
                }
                var t = 640,
                    u = 420,
                    v = 80;
                try {
                    var w = window.screen;
                    t = Math.round(w.width * 0.8);
                    u = Math.round(w.height * 0.7);
                    v = Math.round(w.width * 0.1);
                } catch(z) {}
                var x = '';
                if (p) {
                    window._cke_htmlToLoad = o;
                    x = 'javascript:void( (function(){document.open();document.domain="' + document.domain + '";' + 'document.write( window.opener._cke_htmlToLoad );' + 'document.close();' + 'window.opener._cke_htmlToLoad = null;' + '})() )';
                }
                var y = window.open(x, null, 'toolbar=yes,location=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes,scrollbars=yes,width=' + t + ',height=' + u + ',left=' + v);
                if (!p) {
                    y.document.write(o);
                    y.document.close();
                }
            }
        },
            m = 'preview';
        j.add(m, {
            init: function (n) {
                n.addCommand(m, l);
                n.ui.addButton('Preview', {
                    label: n.lang.preview,
                    command: m
                });
            }
        });
    })();
    j.add('print', {
        init: function (l) {
            var m = 'print',
                n = l.addCommand(m, j.print);
            l.ui.addButton('Print', {
                label: l.lang.print,
                command: m
            });
        }
    });
    j.print = {
        exec: function (l) {
            if (b.opera) return;
            else if (b.gecko) l.window.$.print();
            else l.document.$.execCommand('Print');
        },
        canUndo: false,
        modes: {
            wysiwyg: !b.opera
        }
    };
    j.add('removeformat', {
        requires: ['selection'],
        init: function (l) {
            l.addCommand('removeFormat', j.removeformat.commands.removeformat);
            l.ui.addButton('RemoveFormat', {
                label: l.lang.removeFormat,
                command: 'removeFormat'
            });
        }
    });
    j.removeformat = {
        commands: {
            removeformat: {
                exec: function (l) {
                    var m = l._.removeFormatRegex || (l._.removeFormatRegex = new RegExp('^(?:' + l.config.removeFormatTags.replace(/,/g, '|') + ')$', 'i')),
                        n = l._.removeAttributes || (l._.removeAttributes = l.config.removeFormatAttributes.split(',')),
                        o = l.getSelection().getRanges();
                    for (var p = 0, q; q = o[p]; p++) {
                        if (q.collapsed) continue;
                        q.enlarge(1);
                        var r = q.createBookmark(),
                            s = r.startNode,
                            t = r.endNode,
                            u = function (x) {
                            var y = new d.elementPath(x),
                                z = y.elements;
                            for (var A = 1, B; B = z[A]; A++) {
                                if (B.equals(y.block) || B.equals(y.blockLimit)) break;
                                if (m.test(B.getName())) x.breakParent(B);
                            }
                        };
                        u(s);
                        u(t);
                        var v = s.getNextSourceNode(true, 1);
                        while (v) {
                            if (v.equals(t)) break;
                            var w = v.getNextSourceNode(false, 1);
                            if (v.getName() != 'img' || !v.getAttribute('_cke_protected_html')) if (m.test(v.getName())) v.remove(true);
                            else v.removeAttributes(n);
                            v = w;
                        }
                        q.moveToBookmark(r);
                    }
                    l.getSelection().selectRanges(o);
                }
            }
        }
    };
    i.removeFormatTags = 'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var';
    i.removeFormatAttributes = 'class,style,lang,width,height,align,hspace,valign';
    j.add('resize', {
        init: function (l) {
            var m = l.config;
            if (m.resize_enabled) {
                var n = null,
                    o, p;

                function q(t) {
                    var u = t.data.$.screenX - o.x,
                        v = t.data.$.screenY - o.y,
                        w = p.width + u * (l.lang.dir == 'rtl' ? -1 : 1),
                        x = p.height + v;
                    l.resize(Math.max(m.resize_minWidth, Math.min(w, m.resize_maxWidth)), Math.max(m.resize_minHeight, Math.min(x, m.resize_maxHeight)));
                };

                function r(t) {
                    a.document.removeListener('mousemove', q);
                    a.document.removeListener('mouseup', r);
                    if (l.document) {
                        l.document.removeListener('mousemove', q);
                        l.document.removeListener('mouseup', r);
                    }
                };
                var s = e.addFunction(function (t) {
                    if (!n) n = l.getResizable();
                    p = {
                        width: n.$.offsetWidth || 0,
                        height: n.$.offsetHeight || 0
                    };
                    o = {
                        x: t.screenX,
                        y: t.screenY
                    };
                    a.document.on('mousemove', q);
                    a.document.on('mouseup', r);
                    if (l.document) {
                        l.document.on('mousemove', q);
                        l.document.on('mouseup', r);
                    }
                });
                l.on('themeSpace', function (t) {
                    if (t.data.space == 'bottom') t.data.html += '<div class="cke_resizer" title="' + e.htmlEncode(l.lang.resize) + '"' + ' onmousedown="CKEDITOR.tools.callFunction(' + s + ', event)"' + '></div>';
                },
                l, null, 100);
            }
        }
    });
    i.resize_minWidth = 750;
    i.resize_minHeight = 250;
    i.resize_maxWidth = 3000;
    i.resize_maxHeight = 3000;
    i.resize_enabled = true;
    (function () {
        var l = {
            modes: {
                wysiwyg: 1,
                source: 1
            },
            exec: function (n) {
                var o = n.element.$.form;
                if (o) try {
                    o.submit();
                } catch(p) {
                    if (o.submit.click) o.submit.click();
                }
            }
        },
            m = 'save';
        j.add(m, {
            init: function (n) {
                var o = n.addCommand(m, l);
                o.modes = {
                    wysiwyg: !!n.element.$.form
                };
                n.ui.addButton('Save', {
                    label: n.lang.save,
                    command: m
                });
            }
        });
    })();
    (function () {
        var l = 'scaytcheck',
            m = '',
            n = function () {
            var r = this,
                s = function () {
                var v = {};
                v.srcNodeRef = r.document.getWindow().$.frameElement;
                v.assocApp = 'CKEDITOR.' + a.version + '@' + a.revision;
                v.customerid = r.config.scayt_customerid || '1:11111111111111111111111111111111111111';
                v.customDictionaryName = r.config.scayt_customDictionaryName;
                v.userDictionaryName = r.config.scayt_userDictionaryName;
                v.defLang = r.scayt_defLang;
                if (a._scaytParams) for (var w in a._scaytParams) v[w] = a._scaytParams[w];
                var x = new window.scayt(v),
                    y = o.instances[r.name];
                if (y) {
                    x.sLang = y.sLang;
                    x.option(y.option());
                    x.paused = y.paused;
                }
                o.instances[r.name] = x;
                try {
                    x.setDisabled(x.paused === false);
                } catch(z) {}
                r.fire('showScaytState');
            };
            r.on('contentDom', s);
            r.on('contentDomUnload', function () {
                var v = a.document.getElementsByTag('script'),
                    w = /^dojoIoScript(\d+)$/i,
                    x = /^https?:\/\/svc\.spellchecker\.net\/spellcheck\/script\/ssrv\.cgi/i;
                for (var y = 0; y < v.count(); y++) {
                    var z = v.getItem(y),
                        A = z.getId(),
                        B = z.getAttribute('src');
                    if (A && B && A.match(w) && B.match(x)) z.remove();
                }
            });
            r.on('beforeCommandExec', function (v) {
                if ((v.data.name == 'source' || v.data.name == 'newpage') && (r.mode == 'wysiwyg')) {
                    var w = o.getScayt(r);
                    if (w) {
                        w.paused = !w.disabled;
                        w.destroy();
                        delete o.instances[r.name];
                    }
                }
            });
            r.on('afterSetData', function () {
                if (o.isScaytEnabled(r)) o.getScayt(r).refresh();
            });
            r.on('insertElement', function () {
                var v = o.getScayt(r);
                if (o.isScaytEnabled(r)) {
                    if (c) r.getSelection().unlock(true);
                    try {
                        v.refresh();
                    } catch(w) {}
                }
            },
            this, null, 50);
            r.on('scaytDialog', function (v) {
                v.data.djConfig = window.djConfig;
                v.data.scayt_control = o.getScayt(r);
                v.data.tab = m;
                v.data.scayt = window.scayt;
            });
            var t = r.dataProcessor,
                u = t && t.htmlFilter;
            if (u) u.addRules({
                elements: {
                    span: function (v) {
                        if (v.attributes.scayt_word && v.attributes.scaytid) {
                            delete v.name;
                            return v;
                        }
                    }
                }
            });
            if (r.document) s();
        };
        j.scayt = {
            engineLoaded: false,
            instances: {},
            getScayt: function (r) {
                return this.instances[r.name];
            },
            isScaytReady: function (r) {
                return this.engineLoaded === true && 'undefined' !== typeof window.scayt && this.getScayt(r);
            },
            isScaytEnabled: function (r) {
                var s = this.getScayt(r);
                return s ? s.disabled === false : false;
            },
            loadEngine: function (r) {
                if (this.engineLoaded === true) return n.apply(r);
                else if (this.engineLoaded == -1) return a.on('scaytReady', function () {
                    n.apply(r);
                });
                a.on('scaytReady', n, r);
                a.on('scaytReady', function () {
                    this.engineLoaded = true;
                },
                this, null, 0);
                this.engineLoaded = -1;
                var s = document.location.protocol;
                s = s.search(/https?:/) != -1 ? s : 'http:';
                var t = 'svc.spellchecker.net/spellcheck/lf/scayt/scayt1.js',
                    u = r.config.scayt_srcUrl || s + '//' + t,
                    v = o.parseUrl(u).path + '/';
                a._djScaytConfig = {
                    baseUrl: v,
                    addOnLoad: [function () {
                        a.fireOnce('scaytReady');
                    }],
                    isDebug: false
                };
                a.document.getHead().append(a.document.createElement('script', {
                    attributes: {
                        type: 'text/javascript',
                        src: u
                    }
                }));
                return null;
            },
            parseUrl: function (r) {
                var s;
                if (r.match && (s = r.match(/(.*)[\/\\](.*?\.\w+)$/))) return {
                    path: s[1],
                    file: s[2]
                };
                else return r;
            }
        };
        var o = j.scayt,
            p = function (r, s, t, u, v, w, x) {
            r.addCommand(u, v);
            r.addMenuItem(u, {
                label: t,
                command: u,
                group: w,
                order: x
            });
        },
            q = {
            preserveState: true,
            exec: function (r) {
                if (o.isScaytReady(r)) {
                    var s = o.isScaytEnabled(r);
                    this.setState(s ? 2 : 1);
                    var t = o.getScayt(r);
                    t.setDisabled(s);
                } else if (!r.config.scayt_autoStartup && o.engineLoaded >= 0) {
                    this.setState(0);
                    r.on('showScaytState', function () {
                        this.removeListener();
                        this.setState(o.isScaytEnabled(r) ? 1 : 2);
                    },
                    this);
                    o.loadEngine(r);
                }
            }
        };
        j.add('scayt', {
            requires: ['menubutton'],
            beforeInit: function (r) {
                r.config.menu_groups = 'scayt_suggest,scayt_moresuggest,scayt_control,' + r.config.menu_groups;
            },
            init: function (r) {
                var s = {},
                    t = {},
                    u = r.addCommand(l, q);
                a.dialog.add(l, a.getUrl(this.path + 'dialogs/options.js'));
                var v = 'scaytButton';
                r.addMenuGroup(v);
                r.addMenuItems({
                    scaytToggle: {
                        label: r.lang.scayt.enable,
                        command: l,
                        group: v
                    },
                    scaytOptions: {
                        label: r.lang.scayt.options,
                        group: v,
                        onClick: function () {
                            m = 'options';
                            r.openDialog(l);
                        }
                    },
                    scaytLangs: {
                        label: r.lang.scayt.langs,
                        group: v,
                        onClick: function () {
                            m = 'langs';
                            r.openDialog(l);
                        }
                    },
                    scaytAbout: {
                        label: r.lang.scayt.about,
                        group: v,
                        onClick: function () {
                            m = 'about';
                            r.openDialog(l);
                        }
                    }
                });
                r.ui.add('Scayt', 5, {
                    label: r.lang.scayt.title,
                    title: r.lang.scayt.title,
                    className: 'cke_button_scayt',
                    onRender: function () {
                        u.on('state', function () {
                            this.setState(u.state);
                        },
                        this);
                    },
                    onMenu: function () {
                        var x = o.isScaytEnabled(r);
                        r.getMenuItem('scaytToggle').label = r.lang.scayt[x ? 'disable' : 'enable'];
                        return {
                            scaytToggle: 2,
                            scaytOptions: x ? 2 : 0,
                            scaytLangs: x ? 2 : 0,
                            scaytAbout: x ? 2 : 0
                        };
                    }
                });
                if (r.contextMenu && r.addMenuItems) r.contextMenu.addListener(function (x) {
                    if (! (o.isScaytEnabled(r) && x)) return null;
                    var y = o.getScayt(r),
                        z = y.getWord(x.$);
                    if (!z) return null;
                    var A = y.getLang(),
                        B = {},
                        C = window.scayt.getSuggestion(z, A);
                    if (!C || !C.length) return null;
                    for (i in s) {
                        delete r._.menuItems[i];
                        delete r._.commands[i];
                    }
                    for (i in t) {
                        delete r._.menuItems[i];
                        delete r._.commands[i];
                    }
                    s = {};
                    t = {};
                    var D = false;
                    for (var E = 0, F = C.length; E < F; E += 1) {
                        var G = 'scayt_suggestion_' + C[E].replace(' ', '_'),
                            H = (function (L, M) {
                            return {
                                exec: function () {
                                    y.replace(L, M);
                                }
                            };
                        })(x.$, C[E]);
                        if (E < r.config.scayt_maxSuggestions) {
                            p(r, 'button_' + G, C[E], G, H, 'scayt_suggest', E + 1);
                            B[G] = 2;
                            t[G] = 2;
                        } else {
                            p(r, 'button_' + G, C[E], G, H, 'scayt_moresuggest', E + 1);
                            s[G] = 2;
                            D = true;
                        }
                    }
                    if (D) r.addMenuItem('scayt_moresuggest', {
                        label: r.lang.scayt.moreSuggestions,
                        group: 'scayt_moresuggest',
                        order: 10,
                        getItems: function () {
                            return s;
                        }
                    });
                    var I = {
                        exec: function () {
                            y.ignore(x.$);
                        }
                    },
                        J = {
                        exec: function () {
                            y.ignoreAll(x.$);
                        }
                    },
                        K = {
                        exec: function () {
                            window.scayt.addWordToUserDictionary(x.$);
                        }
                    };
                    p(r, 'ignore', r.lang.scayt.ignore, 'scayt_ignore', I, 'scayt_control', 1);
                    p(r, 'ignore_all', r.lang.scayt.ignoreAll, 'scayt_ignore_all', J, 'scayt_control', 2);
                    p(r, 'add_word', r.lang.scayt.addWord, 'scayt_add_word', K, 'scayt_control', 3);
                    t.scayt_moresuggest = 2;
                    t.scayt_ignore = 2;
                    t.scayt_ignore_all = 2;
                    t.scayt_add_word = 2;
                    if (y.fireOnContextMenu) y.fireOnContextMenu(r);
                    return t;
                });
                if (r.config.scayt_autoStartup) {
                    var w = function () {
                        r.removeListener('showScaytState', w);
                        u.setState(o.isScaytEnabled(r) ? 1 : 2);
                    };
                    r.on('showScaytState', w);
                    o.loadEngine(r);
                }
            }
        });
    })();
    i.scayt_maxSuggestions = 5;
    i.scayt_autoStartup = false;
    j.add('smiley', {
        requires: ['dialog'],
        init: function (l) {
            l.addCommand('smiley', new a.dialogCommand('smiley'));
            l.ui.addButton('Smiley', {
                label: l.lang.smiley.toolbar,
                command: 'smiley'
            });
            a.dialog.add('smiley', this.path + 'dialogs/smiley.js');
        }
    });
    i.smiley_path = a.basePath + 'plugins/smiley/images/';
    i.smiley_images = ['regular_smile.gif', 'sad_smile.gif', 'wink_smile.gif', 'teeth_smile.gif', 'confused_smile.gif', 'tounge_smile.gif', 'embaressed_smile.gif', 'omg_smile.gif', 'whatchutalkingabout_smile.gif', 'angry_smile.gif', 'angel_smile.gif', 'shades_smile.gif', 'devil_smile.gif', 'cry_smile.gif', 'lightbulb.gif', 'thumbs_down.gif', 'thumbs_up.gif', 'heart.gif', 'broken_heart.gif', 'kiss.gif', 'envelope.gif'];
    i.smiley_descriptions = [':)', ':(', ';)', ':D', ':/', ':P', '', '', '', '', '', '', '', ';(', '', '', '', '', '', ':kiss', ''];
    (function () {
        var l = '.%2 p,.%2 div,.%2 pre,.%2 address,.%2 blockquote,.%2 h1,.%2 h2,.%2 h3,.%2 h4,.%2 h5,.%2 h6{background-repeat: no-repeat;border: 1px dotted gray;padding-top: 8px;padding-left: 8px;}.%2 p{%1p.png);}.%2 div{%1div.png);}.%2 pre{%1pre.png);}.%2 address{%1address.png);}.%2 blockquote{%1blockquote.png);}.%2 h1{%1h1.png);}.%2 h2{%1h2.png);}.%2 h3{%1h3.png);}.%2 h4{%1h4.png);}.%2 h5{%1h5.png);}.%2 h6{%1h6.png);}',
            m = /%1/g,
            n = /%2/g,
            o = {
            preserveState: true,
            exec: function (p) {
                this.toggleState();
                this.refresh(p);
            },
            refresh: function (p) {
                var q = this.state == 1 ? 'addClass' : 'removeClass';
                p.document.getBody()[q]('cke_show_blocks');
            }
        };
        j.add('showblocks', {
            requires: ['wysiwygarea'],
            init: function (p) {
                var q = p.addCommand('showblocks', o);
                q.canUndo = false;
                if (p.config.startupOutlineBlocks) q.setState(1);
                p.addCss(l.replace(m, 'background-image: url(' + a.getUrl(this.path) + 'images/block_').replace(n, 'cke_show_blocks '));
                p.ui.addButton('ShowBlocks', {
                    label: p.lang.showBlocks,
                    command: 'showblocks'
                });
                p.on('mode', function () {
                    if (q.state != 0) q.refresh(p);
                });
                p.on('contentDom', function () {
                    if (q.state != 0) q.refresh(p);
                });
            }
        });
    })();
    i.startupOutlineBlocks = false;
    j.add('sourcearea', {
        requires: ['editingblock'],
        init: function (l) {
            var m = j.sourcearea;
            l.on('editingBlockReady', function () {
                var n, o;
                l.addMode('source', {
                    load: function (p, q) {
                        if (c && b.version < 8) p.setStyle('position', 'relative');
                        l.textarea = n = new h('textarea');
                        n.setAttributes({
                            dir: 'ltr',
                            tabIndex: -1
                        });
                        n.addClass('cke_source');
                        var r = {
                            width: b.ie7Compat ? '99%' : '100%',
                            height: '100%',
                            resize: 'none',
                            outline: 'none',
                            'text-align': 'left'
                        };
                        if (c) {
                            if (!b.ie8Compat) {
                                o = function () {
                                    n.hide();
                                    n.setStyle('height', p.$.clientHeight + 'px');
                                    n.show();
                                };
                                l.on('resize', o);
                                r.height = p.$.clientHeight + 'px';
                            }
                        } else n.on('mousedown', function (t) {
                            t = t.data.$;
                            if (t.stopPropagation) t.stopPropagation();
                        });
                        p.setHtml('');
                        p.append(n);
                        n.setStyles(r);
                        l.mayBeDirty = true;
                        this.loadData(q);
                        var s = l.keystrokeHandler;
                        if (s) s.attach(n);
                        setTimeout(function () {
                            l.mode = 'source';
                            l.fire('mode');
                        },
                        b.gecko || b.webkit ? 100 : 0);
                    },
                    loadData: function (p) {
                        n.setValue(p);
                    },
                    getData: function () {
                        return n.getValue();
                    },
                    getSnapshotData: function () {
                        return n.getValue();
                    },
                    unload: function (p) {
                        l.textarea = n = null;
                        if (o) l.removeListener('resize', o);
                        if (c && b.version < 8) p.removeStyle('position');
                    },
                    focus: function () {
                        n.focus();
                    }
                });
            });
            l.addCommand('source', m.commands.source);
            if (l.ui.addButton) l.ui.addButton('Source', {
                label: l.lang.source,
                command: 'source'
            });
            l.on('mode', function () {
                l.getCommand('source').setState(l.mode == 'source' ? 1 : 2);
            });
        }
    });
    j.sourcearea = {
        commands: {
            source: {
                modes: {
                    wysiwyg: 1,
                    source: 1
                },
                exec: function (l) {
                    if (l.mode == 'wysiwyg') l.fire('saveSnapshot');
                    l.getCommand('source').setState(0);
                    l.setMode(l.mode == 'source' ? 'wysiwyg' : 'source');
                },
                canUndo: false
            }
        }
    };
    (function () {
        j.add('stylescombo', {
            requires: ['richcombo', 'styles'],
            init: function (o) {
                var p = o.config,
                    q = o.lang.stylesCombo,
                    r = this.path,
                    s;
                o.ui.addRichCombo('Styles', {
                    label: q.label,
                    title: q.panelTitle,
                    voiceLabel: q.voiceLabel,
                    className: 'cke_styles',
                    multiSelect: true,
                    panel: {
                        css: [p.contentsCss, a.getUrl(o.skinPath + 'editor.css')],
                        voiceLabel: q.panelVoiceLabel
                    },
                    init: function () {
                        var t = this,
                            u = p.stylesCombo_stylesSet.split(':', 2),
                            v = u[1] || a.getUrl(r + 'styles/' + u[0] + '.js');
                        u = u[0];
                        a.loadStylesSet(u, v, function (w) {
                            var x, y, z = [];
                            s = {};
                            for (var A = 0; A < w.length; A++) {
                                var B = w[A];
                                y = B.name;
                                x = s[y] = new a.style(B);
                                x._name = y;
                                z.push(x);
                            }
                            z.sort(n);
                            var C;
                            for (A = 0; A < z.length; A++) {
                                x = z[A];
                                y = x._name;
                                var D = x.type;
                                if (D != C) {
                                    t.startGroup(q['panelTitle' + String(D)]);
                                    C = D;
                                }
                                t.add(y, x.type == 3 ? y : m(x._.definition), y);
                            }
                            t.commit();
                            t.onOpen();
                        });
                    },
                    onClick: function (t) {
                        o.focus();
                        o.fire('saveSnapshot');
                        var u = s[t],
                            v = o.getSelection();
                        if (u.type == 3) {
                            var w = v.getSelectedElement();
                            if (w) u.applyToObject(w);
                            return;
                        }
                        var x = new d.elementPath(v.getStartElement());
                        if (u.type == 2 && u.checkActive(x)) u.remove(o.document);
                        else u.apply(o.document);
                        o.fire('saveSnapshot');
                    },
                    onRender: function () {
                        o.on('selectionChange', function (t) {
                            var u = this.getValue(),
                                v = t.data.path,
                                w = v.elements;
                            for (var x = 0, y; x < w.length; x++) {
                                y = w[x];
                                for (var z in s) if (s[z].checkElementRemovable(y, true)) {
                                    if (z != u) this.setValue(z);
                                    return;
                                }
                            }
                            this.setValue('');
                        },
                        this);
                    },
                    onOpen: function () {
                        var B = this;
                        if (c) o.focus();
                        var t = o.getSelection(),
                            u = t.getSelectedElement(),
                            v = u && u.getName(),
                            w = new d.elementPath(u || t.getStartElement()),
                            x = [0, 0, 0, 0];
                        B.showAll();
                        B.unmarkAll();
                        for (var y in s) {
                            var z = s[y],
                                A = z.type;
                            if (A == 3) {
                                if (u && z.element == v) {
                                    if (z.checkElementRemovable(u, true)) B.mark(y);
                                    x[A]++;
                                } else B.hideItem(y);
                            } else {
                                if (z.checkActive(w)) B.mark(y);
                                x[A]++;
                            }
                        }
                        if (!x[1]) B.hideGroup(q['panelTitle' + String(1)]);
                        if (!x[2]) B.hideGroup(q['panelTitle' + String(2)]);
                        if (!x[3]) B.hideGroup(q['panelTitle' + String(3)]);
                    }
                });
            }
        });
        var l = {};
        a.addStylesSet = function (o, p) {
            l[o] = p;
        };
        a.loadStylesSet = function (o, p, q) {
            var r = l[o];
            if (r) {
                q(r);
                return;
            }
            a.scriptLoader.load(p, function () {
                q(l[o]);
            });
        };

        function m(o) {
            var p = [],
                q = o.element;
            if (q == 'bdo') q = 'span';
            p = ['<', q];
            var r = o.attributes;
            if (r) for (var s in r) p.push(' ', s, '="', r[s], '"');
            var t = a.style.getStyleText(o);
            if (t) p.push(' style="', t, '"');
            p.push('>', o.name, '</', q, '>');
            return p.join('');
        };

        function n(o, p) {
            var q = o.type,
                r = p.type;
            return q == r ? 0 : q == 3 ? -1 : r == 3 ? 1 : r == 1 ? 1 : -1;
        };
    })();
    i.stylesCombo_stylesSet = 'default';
    j.add('table', {
        init: function (l) {
            var m = j.table,
                n = l.lang.table;
            l.addCommand('table', new a.dialogCommand('table'));
            l.addCommand('tableProperties', new a.dialogCommand('tableProperties'));
            l.ui.addButton('Table', {
                label: n.toolbar,
                command: 'table'
            });
            a.dialog.add('table', this.path + 'dialogs/table.js');
            a.dialog.add('tableProperties', this.path + 'dialogs/table.js');
            if (l.addMenuItems) l.addMenuItems({
                table: {
                    label: n.menu,
                    command: 'tableProperties',
                    group: 'table',
                    order: 5
                },
                tabledelete: {
                    label: n.deleteTable,
                    command: 'tableDelete',
                    group: 'table',
                    order: 1
                }
            });
            if (l.contextMenu) l.contextMenu.addListener(function (o, p) {
                if (!o) return null;
                var q = o.is('table') || o.hasAscendant('table');
                if (q) return {
                    tabledelete: 2,
                    table: 2
                };
                return null;
            });
        }
    });
    (function () {
        function l(y, z) {
            if (c) y.removeAttribute(z);
            else delete y[z];
        };
        var m = /^(?:td|th)$/;

        function n(y) {
            var z = y.createBookmarks(),
                A = y.getRanges(),
                B = [],
                C = {};

            function D(L) {
                if (B.length > 0) return;
                if (L.type == 1 && m.test(L.getName()) && !L.getCustomData('selected_cell')) {
                    h.setMarker(C, L, 'selected_cell', true);
                    B.push(L);
                }
            };
            for (var E = 0; E < A.length;
            E++) {
                var F = A[E];
                if (F.collapsed) {
                    var G = F.getCommonAncestor(),
                        H = G.getAscendant('td', true) || G.getAscendant('th', true);
                    if (H) B.push(H);
                } else {
                    var I = new d.walker(F),
                        J;
                    I.guard = D;
                    while (J = I.next()) {
                        var K = J.getParent();
                        if (K && m.test(K.getName()) && !K.getCustomData('selected_cell')) {
                            h.setMarker(C, K, 'selected_cell', true);
                            B.push(K);
                        }
                    }
                }
            }
            h.clearAllMarkers(C);
            y.selectBookmarks(z);
            return B;
        };

        function o(y) {
            var z = new h(y),
                A = (z.getName() == 'table' ? y : z.getAscendant('table')).$,
                B = A.rows,
                C = -1,
                D = [];
            for (var E = 0; E < B.length; E++) {
                C++;
                if (!D[C]) D[C] = [];
                var F = -1;
                for (var G = 0; G < B[E].cells.length; G++) {
                    var H = B[E].cells[G];
                    F++;
                    while (D[C][F]) F++;
                    var I = isNaN(H.colSpan) ? 1 : H.colSpan,
                    J = isNaN(H.rowSpan) ? 1 : H.rowSpan;
                    for (var K = 0; K < J; K++) {
                        if (!D[C + K]) D[C + K] = [];
                        for (var L = 0; L < I; L++) D[C + K][F + L] = B[E].cells[G];
                    }
                    F += I - 1;
                }
            }
            return D;
        };

        function p(y, z) {
            var A = c ? '_cke_rowspan' : 'rowSpan';
            for (var B = 0; B < y.length; B++) for (var C = 0; C < y[B].length; C++) {
                var D = y[B][C];
                if (D.parentNode) D.parentNode.removeChild(D);
                D.colSpan = D[A] = 1;
            }
            var E = 0;
            for (B = 0; B < y.length; B++) for (C = 0; C < y[B].length; C++) {
                D = y[B][C];
                if (!D) continue;
                if (C > E) E = C;
                if (D._cke_colScanned) continue;
                if (y[B][C - 1] == D) D.colSpan++;
                if (y[B][C + 1] != D) D._cke_colScanned = 1;
            }
            for (B = 0; B <= E; B++) for (C = 0; C < y.length; C++) {
                if (!y[C]) continue;
                D = y[C][B];
                if (!D || D._cke_rowScanned) continue;
                if (y[C - 1] && y[C - 1][B] == D) D[A]++;
                if (!y[C + 1] || y[C + 1][B] != D) D._cke_rowScanned = 1;
            }
            for (B = 0; B < y.length; B++) for (C = 0; C < y[B].length; C++) {
                D = y[B][C];
                l(D, '_cke_colScanned');
                l(D, '_cke_rowScanned');
            }
            for (B = 0; B < y.length; B++) {
                var F = z.ownerDocument.createElement('tr');
                for (C = 0; C < y[B].length;) {
                    D = y[B][C];
                    if (y[B - 1] && y[B - 1][C] == D) {
                        C += D.colSpan;
                        continue;
                    }
                    F.appendChild(D);
                    if (A != 'rowSpan') {
                        D.rowSpan = D[A];
                        D.removeAttribute(A);
                    }
                    C += D.colSpan;
                    if (D.colSpan == 1) D.removeAttribute('colSpan');
                    if (D.rowSpan == 1) D.removeAttribute('rowSpan');
                }
                if (c) z.rows[B].replaceNode(F);
                else {
                    var G = new h(z.rows[B]),
                        H = new h(F);
                    G.setHtml('');
                    H.moveChildren(G);
                }
            }
        };

        function q(y) {
            var z = y.cells;
            for (var A = 0; A < z.length; A++) {
                z[A].innerHTML = '';
                if (!c) new h(z[A]).appendBogus();
            }
        };

        function r(y, z) {
            var A = y.getStartElement().getAscendant('tr');
            if (!A) return;
            var B = A.clone(true);
            B.insertBefore(A);
            q(z ? B.$ : A.$);
        };

        function s(y) {
            if (y instanceof d.selection) {
                var z = n(y),
                    A = [];
                for (var B = 0; B < z.length; B++) {
                    var C = z[B].getParent();
                    A[C.$.rowIndex] = C;
                }
                for (B = A.length; B >= 0; B--) if (A[B]) s(A[B]);
            } else if (y instanceof h) {
                var D = y.getAscendant('table');
                if (D.$.rows.length == 1) D.remove();
                else y.remove();
            }
        };

        function t(y, z) {
            var A = y.getStartElement(),
                B = A.getAscendant('td', true) || A.getAscendant('th', true);
            if (!B) return;
            var C = B.getAscendant('table'),
                D = B.$.cellIndex;
            for (var E = 0; E < C.$.rows.length; E++) {
                var F = C.$.rows[E];
                if (F.cells.length < D + 1) continue;
                B = new h(F.cells[D].cloneNode(false));
                if (!c) B.appendBogus();
                var G = new h(F.cells[D]);
                if (z) B.insertBefore(G);
                else B.insertAfter(G);
            }
        };

        function u(y) {
            if (y instanceof d.selection) {
                var z = n(y);
                for (var A = z.length; A >= 0; A--) if (z[A]) u(z[A]);
            } else if (y instanceof h) {
                var B = y.getAscendant('table'),
                    C = y.$.cellIndex;
                for (A = B.$.rows.length - 1; A >= 0; A--) {
                    var D = new h(B.$.rows[A]);
                    if (!C && D.$.cells.length == 1) {
                        s(D);
                        continue;
                    }
                    if (D.$.cells[C]) D.$.removeChild(D.$.cells[C]);
                }
            }
        };

        function v(y, z) {
            var A = y.getStartElement(),
                B = A.getAscendant('td', true) || A.getAscendant('th', true);
            if (!B) return;
            var C = B.clone();
            if (!c) C.appendBogus();
            if (z) C.insertBefore(B);
            else C.insertAfter(B);
        };

        function w(y) {
            if (y instanceof d.selection) {
                var z = n(y);
                for (var A = z.length - 1; A >= 0; A--) w(z[A]);
            } else if (y instanceof h) if (y.getParent().getChildCount() == 1) y.getParent().remove();
            else y.remove();
        };
        var x = {
            thead: 1,
            tbody: 1,
            tfoot: 1,
            td: 1,
            tr: 1,
            th: 1
        };
        j.tabletools = {
            init: function (y) {
                var z = y.lang.table;
                y.addCommand('cellProperties', new a.dialogCommand('cellProperties'));
                a.dialog.add('cellProperties', this.path + 'dialogs/tableCell.js');
                y.addCommand('tableDelete', {
                    exec: function (A) {
                        var B = A.getSelection(),
                            C = B && B.getStartElement(),
                            D = C && C.getAscendant('table', true);
                        if (!D) return;
                        B.selectElement(D);
                        var E = B.getRanges()[0];
                        E.collapse();
                        B.selectRanges([E]);
                        if (D.getParent().getChildCount() == 1) D.getParent().remove();
                        else D.remove();
                    }
                });
                y.addCommand('rowDelete', {
                    exec: function (A) {
                        var B = A.getSelection();
                        s(B);
                    }
                });
                y.addCommand('rowInsertBefore', {
                    exec: function (A) {
                        var B = A.getSelection();
                        r(B, true);
                    }
                });
                y.addCommand('rowInsertAfter', {
                    exec: function (A) {
                        var B = A.getSelection();
                        r(B);
                    }
                });
                y.addCommand('columnDelete', {
                    exec: function (A) {
                        var B = A.getSelection();
                        u(B);
                    }
                });
                y.addCommand('columnInsertBefore', {
                    exec: function (A) {
                        var B = A.getSelection();
                        t(B, true);
                    }
                });
                y.addCommand('columnInsertAfter', {
                    exec: function (A) {
                        var B = A.getSelection();
                        t(B);
                    }
                });
                y.addCommand('cellDelete', {
                    exec: function (A) {
                        var B = A.getSelection();
                        w(B);
                    }
                });
                y.addCommand('cellInsertBefore', {
                    exec: function (A) {
                        var B = A.getSelection();
                        v(B, true);
                    }
                });
                y.addCommand('cellInsertAfter', {
                    exec: function (A) {
                        var B = A.getSelection();
                        v(B);
                    }
                });
                if (y.addMenuItems) y.addMenuItems({
                    tablecell: {
                        label: z.cell.menu,
                        group: 'tablecell',
                        order: 1,
                        getItems: function () {
                            var A = n(y.getSelection());
                            return {
                                tablecell_insertBefore: 2,
                                tablecell_insertAfter: 2,
                                tablecell_delete: 2,
                                tablecell_properties: A.length > 0 ? 2 : 0
                            };
                        }
                    },
                    tablecell_insertBefore: {
                        label: z.cell.insertBefore,
                        group: 'tablecell',
                        command: 'cellInsertBefore',
                        order: 5
                    },
                    tablecell_insertAfter: {
                        label: z.cell.insertAfter,
                        group: 'tablecell',
                        command: 'cellInsertAfter',
                        order: 10
                    },
                    tablecell_delete: {
                        label: z.cell.deleteCell,
                        group: 'tablecell',
                        command: 'cellDelete',
                        order: 15
                    },
                    tablecell_properties: {
                        label: z.cell.title,
                        group: 'tablecellproperties',
                        command: 'cellProperties',
                        order: 20
                    },
                    tablerow: {
                        label: z.row.menu,
                        group: 'tablerow',
                        order: 1,
                        getItems: function () {
                            return {
                                tablerow_insertBefore: 2,
                                tablerow_insertAfter: 2,
                                tablerow_delete: 2
                            };
                        }
                    },
                    tablerow_insertBefore: {
                        label: z.row.insertBefore,
                        group: 'tablerow',
                        command: 'rowInsertBefore',
                        order: 5
                    },
                    tablerow_insertAfter: {
                        label: z.row.insertAfter,
                        group: 'tablerow',
                        command: 'rowInsertAfter',
                        order: 10
                    },
                    tablerow_delete: {
                        label: z.row.deleteRow,
                        group: 'tablerow',
                        command: 'rowDelete',
                        order: 15
                    },
                    tablecolumn: {
                        label: z.column.menu,
                        group: 'tablecolumn',
                        order: 1,
                        getItems: function () {
                            return {
                                tablecolumn_insertBefore: 2,
                                tablecolumn_insertAfter: 2,
                                tablecolumn_delete: 2
                            };
                        }
                    },
                    tablecolumn_insertBefore: {
                        label: z.column.insertBefore,
                        group: 'tablecolumn',
                        command: 'columnInsertBefore',
                        order: 5
                    },
                    tablecolumn_insertAfter: {
                        label: z.column.insertAfter,
                        group: 'tablecolumn',
                        command: 'columnInsertAfter',
                        order: 10
                    },
                    tablecolumn_delete: {
                        label: z.column.deleteColumn,
                        group: 'tablecolumn',
                        command: 'columnDelete',
                        order: 15
                    }
                });
                if (y.contextMenu) y.contextMenu.addListener(function (A, B) {
                    if (!A) return null;
                    while (A) {
                        if (A.getName() in x) return {
                            tablecell: 2,
                            tablerow: 2,
                            tablecolumn: 2
                        };
                        A = A.getParent();
                    }
                    return null;
                });
            },
            getSelectedCells: n
        };
        j.add('tabletools', j.tabletools);
    })();
    j.add('specialchar', {
        init: function (l) {
            var m = 'specialchar';
            a.dialog.add(m, this.path + 'dialogs/specialchar.js');
            l.addCommand(m, new a.dialogCommand(m));
            l.ui.addButton('SpecialChar', {
                label: l.lang.specialChar.toolbar,
                command: m
            });
        }
    });
    (function () {
        var l = {
            exec: function (n) {
                n.container.focusNext(true);
            }
        },
            m = {
            exec: function (n) {
                n.container.focusPrevious(true);
            }
        };
        j.add('tab', {
            requires: ['keystrokes'],
            init: function (n) {
                var o = n.keystrokeHandler.keystrokes;
                o[9] = 'tab';
                o[2000 + 9] = 'shiftTab';
                var p = n.config.tabSpaces,
                    q = '';
                while (p--) q += '\xa0';
                n.addCommand('tab', {
                    exec: function (r) {
                        if (!r.fire('tab')) if (q.length > 0) r.insertHtml(q);
                        else return r.execCommand('blur');
                        return true;
                    }
                });
                n.addCommand('shiftTab', {
                    exec: function (r) {
                        if (!r.fire('shiftTab')) return r.execCommand('blurBack');
                        return true;
                    }
                });
                n.addCommand('blur', l);
                n.addCommand('blurBack', m);
            }
        });
    })();
    h.prototype.focusNext = function (l) {
        var u = this;
        var m = u.$,
            n = u.getTabIndex(),
            o, p, q, r, s, t;
        if (n <= 0) {
            s = u.getNextSourceNode(l, 1);
            while (s) {
                if (s.isVisible() && s.getTabIndex() === 0) {
                    q = s;
                    break;
                }
                s = s.getNextSourceNode(false, 1);
            }
        } else {
            s = u.getDocument().getBody().getFirst();
            while (s = s.getNextSourceNode(false, 1)) {
                if (!o) if (!p && s.equals(u)) {
                    p = true;
                    if (l) {
                        if (! (s = s.getNextSourceNode(true, 1))) break;
                        o = 1;
                    }
                } else if (p && !u.contains(s)) o = 1;
                if (!s.isVisible() || (t = s.getTabIndex()) < (0)) continue;
                if (o && t == n) {
                    q = s;
                    break;
                }
                if (t > n && (!q || !r || t < r)) {
                    q = s;
                    r = t;
                } else if (!q && t === 0) {
                    q = s;
                    r = t;
                }
            }
        }
        if (q) q.focus();
    };
    h.prototype.focusPrevious = function (l) {
        var u = this;
        var m = u.$,
            n = u.getTabIndex(),
            o, p, q, r = 0,
            s, t = u.getDocument().getBody().getLast();
        while (t = t.getPreviousSourceNode(false, 1)) {
            if (!o) if (!p && t.equals(u)) {
                p = true;
                if (l) {
                    if (! (t = t.getPreviousSourceNode(true, 1))) break;
                    o = 1;
                }
            } else if (p && !u.contains(t)) o = 1;
            if (!t.isVisible() || (s = t.getTabIndex()) < (0)) continue;
            if (n <= 0) {
                if (o && s === 0) {
                    q = t;
                    break;
                }
                if (s > r) {
                    q = t;
                    r = s;
                }
            } else {
                if (o && s == n) {
                    q = t;
                    break;
                }
                if (s < n && (!q || s > r)) {
                    q = t;
                    r = s;
                }
            }
        }
        if (q) q.focus();
    };
    i.tabSpaces = 0;
    (function () {
        j.add('templates', {
            requires: ['dialog'],
            init: function (n) {
                a.dialog.add('templates', a.getUrl(this.path + 'dialogs/templates.js'));
                n.addCommand('templates', new a.dialogCommand('templates'));
                n.ui.addButton('Templates', {
                    label: n.lang.templates.button,
                    command: 'templates'
                });
            }
        });
        var l = {},
            m = {};
        a.addTemplates = function (n, o) {
            l[n] = o;
        };
        a.getTemplates = function (n) {
            return l[n];
        };
        a.loadTemplates = function (n, o) {
            var p = [];
            for (var q = 0; q < n.length; q++) if (!m[n[q]]) {
                p.push(n[q]);
                m[n[q]] = 1;
            }
            if (p.length > 0) a.scriptLoader.load(p, o);
            else setTimeout(o, 0);
        };
    })();
    i.templates = 'default';
    i.templates_files = [a.getUrl('plugins/templates/templates/default.js')];
    i.templates_replaceContent = true;
    (function () {
        var l = function () {
            this.toolbars = [];
            this.focusCommandExecuted = false;
        };
        l.prototype.focus = function () {
            for (var n = 0, o; o = this.toolbars[n++];) for (var p = 0, q; q = o.items[p++];) if (q.focus) {
                q.focus();
                return;
            }
        };
        var m = {
            toolbarFocus: {
                modes: {
                    wysiwyg: 1,
                    source: 1
                },
                exec: function (n) {
                    if (n.toolbox) {
                        n.toolbox.focusCommandExecuted = true;
                        if (c) setTimeout(function () {
                            n.toolbox.focus();
                        },
                        100);
                        else n.toolbox.focus();
                    }
                }
            }
        };
        j.add('toolbar', {
            init: function (n) {
                var o = function (p, q) {
                    switch (q) {
                    case 39:
                    case 9:
                        while ((p = p.next || p.toolbar.next && p.toolbar.next.items[0]) && (!p.focus)) {}
                        if (p) p.focus();
                        else n.toolbox.focus();
                        return false;
                    case 37:
                    case 2000 + 9:
                        while ((p = p.previous || p.toolbar.previous && p.toolbar.previous.items[p.toolbar.previous.items.length - 1]) && (!p.focus)) {}
                        if (p) p.focus();
                        else {
                            var r = n.toolbox.toolbars[n.toolbox.toolbars.length - 1].items;
                            r[r.length - 1].focus();
                        }
                        return false;
                    case 27:
                        n.focus();
                        return false;
                    case 13:
                    case 32:
                        p.execute();
                        return false;
                    }
                    return true;
                };
                n.on('themeSpace', function (p) {
                    if (p.data.space == n.config.toolbarLocation) {
                        n.toolbox = new l();
                        var q = ['<div class="cke_toolbox"'],
                            r = n.config.toolbarStartupExpanded,
                            s;
                        q.push(r ? '>' : ' style="display:none">');
                        var t = n.toolbox.toolbars,
                            u = n.config.toolbar instanceof Array ? n.config.toolbar : n.config['toolbar_' + n.config.toolbar];
                        for (var v = 0; v < u.length; v++) {
                            var w = u[v];
                            if (!w) continue;
                            var x = 'cke_' + e.getNextNumber(),
                                y = {
                                id: x,
                                items: []
                            };
                            if (s) {
                                q.push('</div>');
                                s = 0;
                            }
                            if (w === '/') {
                                q.push('<div class="cke_break"></div>');
                                continue;
                            }
                            q.push('<span id="', x, '" class="cke_toolbar"><span class="cke_toolbar_start"></span>');
                            var z = t.push(y) - 1;
                            if (z > 0) {
                                y.previous = t[z - 1];
                                y.previous.next = y;
                            }
                            for (var A = 0; A < w.length; A++) {
                                var B, C = w[A];
                                if (C == '-') B = k.separator;
                                else B = n.ui.create(C);
                                if (B) {
                                    if (B.canGroup) {
                                        if (!s) {
                                            q.push('<span class="cke_toolgroup">');
                                            s = 1;
                                        }
                                    } else if (s) {
                                        q.push('</span>');
                                        s = 0;
                                    }
                                    var D = B.render(n, q);
                                    z = y.items.push(D) - 1;
                                    if (z > 0) {
                                        D.previous = y.items[z - 1];
                                        D.previous.next = D;
                                    }
                                    D.toolbar = y;
                                    D.onkey = o;
                                    D.onfocus = function () {
                                        if (!n.toolbox.focusCommandExecuted) n.focus();
                                    };
                                }
                            }
                            if (s) {
                                q.push('</span>');
                                s = 0;
                            }
                            q.push('<span class="cke_toolbar_end"></span></span>');
                        }
                        q.push('</div>');
                        if (n.config.toolbarCanCollapse) {
                            var E = e.addFunction(function () {
                                n.execCommand('toolbarCollapse');
                            }),
                                F = 'cke_' + e.getNextNumber();
                            n.addCommand('toolbarCollapse', {
                                exec: function (G) {
                                    var H = a.document.getById(F),
                                        I = H.getPrevious(),
                                        J = G.getThemeSpace('contents'),
                                        K = I.getParent(),
                                        L = parseInt(J.$.style.height, 10),
                                        M = K.$.offsetHeight;
                                    if (I.isVisible()) {
                                        I.hide();
                                        H.addClass('cke_toolbox_collapser_min');
                                    } else {
                                        I.show();
                                        H.removeClass('cke_toolbox_collapser_min');
                                    }
                                    var N = K.$.offsetHeight - M;
                                    J.setStyle('height', L - N + 'px');
                                },
                                modes: {
                                    wysiwyg: 1,
                                    source: 1
                                }
                            });
                            q.push('<a id="' + F + '" class="cke_toolbox_collapser');
                            if (!r) q.push(' cke_toolbox_collapser_min');
                            q.push('" onclick="CKEDITOR.tools.callFunction(' + E + ')"></a>');
                        }
                        p.data.html += q.join('');
                    }
                });
                n.addCommand('toolbarFocus', m.toolbarFocus);
            }
        });
    })();
    k.separator = {
        render: function (l, m) {
            m.push('<span class="cke_separator"></span>');
            return {};
        }
    };
    i.toolbarLocation = 'top';
    i.toolbar_Basic = [
        ['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink', '-', 'About']];
    i.toolbar_Full = [
        ['Source', '-', 'Save', 'NewPage', 'customPreview', '-', 'Templates'],
        ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print', 'SpellChecker', 'Scayt'],
        ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
        ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'], '/', ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript'],
        ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'],
        ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
        ['Link', 'Unlink', 'Anchor'],
        ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak'], '/', ['Styles', 'Format', 'Font', 'FontSize'],
        ['TextColor', 'BGColor'],
        ['Maximize', 'ShowBlocks', '-', 'About']];
    i.toolbar = 'Full';
    i.toolbarCanCollapse = true;
    i.toolbarStartupExpanded = true;
    (function () {
        j.add('undo', {
            requires: ['selection', 'wysiwygarea'],
            init: function (n) {
                var o = new m(n),
                    p = n.addCommand('undo', {
                    exec: function () {
                        if (o.undo()) {
                            n.selectionChange();
                            this.fire('afterUndo');
                        }
                    },
                    state: 0,
                    canUndo: false
                }),
                    q = n.addCommand('redo', {
                    exec: function () {
                        if (o.redo()) {
                            n.selectionChange();
                            this.fire('afterRedo');
                        }
                    },
                    state: 0,
                    canUndo: false
                });
                o.onChange = function () {
                    p.setState(o.undoable() ? 2 : 0);
                    q.setState(o.redoable() ? 2 : 0);
                };

                function r(s) {
                    if (o.enabled && s.data.command.canUndo !== false) o.save();
                };
                n.on('beforeCommandExec', r);
                n.on('afterCommandExec', r);
                n.on('saveSnapshot', function () {
                    o.save();
                });
                n.on('contentDom', function () {
                    n.document.on('keydown', function (s) {
                        if (!s.data.$.ctrlKey && !s.data.$.metaKey) o.type(s);
                    });
                });
                n.on('beforeModeUnload', function () {
                    n.mode == 'wysiwyg' && o.save(true);
                });
                n.on('mode', function () {
                    o.enabled = n.mode == 'wysiwyg';
                    o.onChange();
                });
                n.ui.addButton('Undo', {
                    label: n.lang.undo,
                    command: 'undo'
                });
                n.ui.addButton('Redo', {
                    label: n.lang.redo,
                    command: 'redo'
                });
                n.resetUndo = function () {
                    o.reset();
                    n.fire('saveSnapshot');
                };
            }
        });

        function l(n) {
            var p = this;
            var o = n.getSelection();
            p.contents = n.getSnapshot();
            p.bookmarks = o && o.createBookmarks2(true);
            if (c) p.contents = p.contents.replace(/\s+_cke_expando=".*?"/g, '');
        };
        l.prototype = {
            equals: function (n, o) {
                if (this.contents != n.contents) return false;
                if (o) return true;
                var p = this.bookmarks,
                    q = n.bookmarks;
                if (p || q) {
                    if (!p || !q || p.length != q.length) return false;
                    for (var r = 0; r < p.length; r++) {
                        var s = p[r],
                            t = q[r];
                        if (s.startOffset != t.startOffset || s.endOffset != t.endOffset || !e.arrayCompare(s.start, t.start) || !e.arrayCompare(s.end, t.end)) return false;
                    }
                }
                return true;
            }
        };

        function m(n) {
            this.editor = n;
            this.reset();
        };
        m.prototype = {
            type: function (n) {
                var o = n && n.data.getKeystroke(),
                    p = {
                    8: 1,
                    46: 1
                },
                    q = o in p,
                    r = this.lastKeystroke in p,
                    s = q && o == this.lastKeystroke,
                    t = {
                    37: 1,
                    38: 1,
                    39: 1,
                    40: 1
                },
                    u = o in t,
                    v = this.lastKeystroke in t,
                    w = !q && !u,
                    x = q && !s,
                    y = !this.typing || w && (r || v);
                if (y || x) {
                    var z = new l(this.editor);
                    e.setTimeout(function () {
                        var B = this;
                        var A = B.editor.getSnapshot();
                        if (c) A = A.replace(/\s+_cke_expando=".*?"/g, '');
                        if (z.contents != A) {
                            if (!B.save(false, z, false)) B.snapshots.splice(B.index + 1, B.snapshots.length - B.index - 1);
                            B.hasUndo = true;
                            B.hasRedo = false;
                            B.typesCount = 1;
                            B.modifiersCount = 1;
                            B.onChange();
                        }
                    },
                    0, this);
                }
                this.lastKeystroke = o;
                if (q) {
                    this.typesCount = 0;
                    this.modifiersCount++;
                    if (this.modifiersCount > 25) {
                        this.save();
                        this.modifiersCount = 1;
                    }
                } else if (!u) {
                    this.modifiersCount = 0;
                    this.typesCount++;
                    if (this.typesCount > 25) {
                        this.save();
                        this.typesCount = 1;
                    }
                }
                this.typing = true;
            },
            reset: function () {
                var n = this;
                n.lastKeystroke = 0;
                n.snapshots = [];
                n.index = -1;
                n.limit = n.editor.config.undoStackSize;
                n.currentImage = null;
                n.hasUndo = false;
                n.hasRedo = false;
                n.resetType();
            },
            resetType: function () {
                var n = this;
                n.typing = false;
                delete n.lastKeystroke;
                n.typesCount = 0;
                n.modifiersCount = 0;
            },
            fireChange: function () {
                var n = this;
                n.hasUndo = !!n.getNextImage(true);
                n.hasRedo = !!n.getNextImage(false);
                n.resetType();
                n.onChange();
            },
            save: function (n, o, p) {
                var r = this;
                var q = r.snapshots;
                if (!o) o = new l(r.editor);
                if (r.currentImage && o.equals(r.currentImage, n)) return false;
                q.splice(r.index + 1, q.length - r.index - 1);
                if (q.length == r.limit) q.shift();
                r.index = q.push(o) - 1;
                r.currentImage = o;
                if (p !== false) r.fireChange();
                return true;
            },
            restoreImage: function (n) {
                var p = this;
                p.editor.loadSnapshot(n.contents);
                if (n.bookmarks) p.editor.getSelection().selectBookmarks(n.bookmarks);
                else if (c) {
                    var o = p.editor.document.getBody().$.createTextRange();
                    o.collapse(true);
                    o.select();
                }
                p.index = n.index;
                p.currentImage = n;
                p.fireChange();
            },
            getNextImage: function (n) {
                var s = this;
                var o = s.snapshots,
                    p = s.currentImage,
                    q, r;
                if (p) if (n) for (r = s.index - 1; r >= 0; r--) {
                    q = o[r];
                    if (!p.equals(q, true)) {
                        q.index = r;
                        return q;
                    }
                } else for (r = s.index + 1; r < o.length; r++) {
                    q = o[r];
                    if (!p.equals(q, true)) {
                        q.index = r;
                        return q;
                    }
                }
                return null;
            },
            redoable: function () {
                return this.enabled && this.hasRedo;
            },
            undoable: function () {
                return this.enabled && this.hasUndo;
            },
            undo: function () {
                var o = this;
                if (o.undoable()) {
                    o.save(true);
                    var n = o.getNextImage(true);
                    if (n) return o.restoreImage(n),
                    true;
                }
                return false;
            },
            redo: function () {
                var o = this;
                if (o.redoable()) {
                    o.save(true);
                    if (o.redoable()) {
                        var n = o.getNextImage(false);
                        if (n) return o.restoreImage(n),
                        true;
                    }
                }
                return false;
            }
        };
    })();
    i.undoStackSize = 20;
    (function () {
        var l = {
            table: 1,
            pre: 1
        },
            m = /\s*<(p|div|address|h\d|center)[^>]*>\s*(?:<br[^>]*>|&nbsp;|&#160;)\s*(:?<\/\1>)?\s*$/gi;

        function n(q) {
            var v = this;
            if (v.mode == 'wysiwyg') {
                v.focus();
                var r = v.getSelection(),
                    s = q.data;
                if (v.dataProcessor) s = v.dataProcessor.toHtml(s);
                if (c) {
                    var t = r.isLocked;
                    if (t) r.unlock();
                    var u = r.getNative();
                    if (u.type == 'Control') u.clear();
                    u.createRange().pasteHTML(s);
                    if (t) v.getSelection().lock();
                } else v.document.$.execCommand('inserthtml', false, s);
            }
        };

        function o(q) {
            if (this.mode == 'wysiwyg') {
                this.focus();
                this.fire('saveSnapshot');
                var r = q.data,
                    s = r.getName(),
                    t = f.$block[s],
                    u = this.getSelection(),
                    v = u.getRanges(),
                    w = u.isLocked;
                if (w) u.unlock();
                var x, y, z, A;
                for (var B = v.length - 1; B >= 0; B--) {
                    x = v[B];
                    x.deleteContents();
                    y = !B && r || r.clone(true);
                    var C, D;
                    if (this.config.enterMode != 2 && t) while ((C = x.getCommonAncestor(false, true)) && ((D = f[C.getName()]) && (!(D && D[s])))) x.splitBlock();
                    x.insertNode(y);
                    if (!z) z = y;
                }
                x.moveToPosition(z, 4);
                var E = z.getNextSourceNode(true);
                if (E && E.type == 1) x.moveToElementEditStart(E);
                u.selectRanges([x]);
                if (w) this.getSelection().lock();
                e.setTimeout(function () {
                    this.fire('saveSnapshot');
                },
                0, this);
            }
        };

        function p(q) {
            var r = q.editor,
                s = q.data.path,
                t = s.blockLimit,
                u = q.data.selection,
                v = u.getRanges()[0],
                w = r.document.getBody(),
                x = r.config.enterMode;
            if (x != 2 && v.collapsed && t.getName() == 'body' && !s.block) {
                var y = u.createBookmarks(),
                    z = v.fixBlock(true, r.config.enterMode == 3 ? 'div' : 'p');
                if (c) {
                    var A = z.getElementsByTag('br'),
                        B;
                    for (var C = 0; C < A.count(); C++) if ((B = A.getItem(C)) && (B.hasAttribute('_cke_bogus'))) B.remove();
                }
                u.selectBookmarks(y);
                var D = z.getChildren(),
                    E = D.count(),
                    F, G = d.walker.whitespaces(true),
                    H = z.getPrevious(G),
                    I = z.getNext(G),
                    J;
                if (H && H.getName && !(H.getName() in l)) J = H;
                else if (I && I.getName && !(I.getName() in l)) J = I;
                if ((!E || (F = D.getItem(0)) && (F.is && F.is('br'))) && (J && v.moveToElementEditStart(J))) {
                    z.remove();
                    v.select();
                }
            }
            var K = w.getLast(d.walker.whitespaces(true));
            if (K && K.getName && K.getName() in l) {
                var L = r.document.createElement(c && x != 2 ? '<br _cke_bogus="true" />' : 'br');
                w.append(L);
            }
        };
        j.add('wysiwygarea', {
            requires: ['editingblock'],
            init: function (q) {
                var r = q.config.enterMode != 2 ? q.config.enterMode == 3 ? 'div' : 'p' : false;
                q.on('editingBlockReady', function () {
                    var s, t, u, v, w, x, y, z = b.isCustomDomain(),
                        A = function () {
                        if (u) u.remove();
                        if (t) t.remove();
                        x = 0;
                        var D = 'void( ' + (b.gecko ? 'setTimeout' : '') + '( function(){' + 'document.open();' + (c && z ? 'document.domain="' + document.domain + '";' : '') + 'document.write( window.parent[ "_cke_htmlToLoad_' + q.name + '" ] );' + 'document.close();' + 'window.parent[ "_cke_htmlToLoad_' + q.name + '" ] = null;' + '}' + (b.gecko ? ', 0 )' : ')()') + ' )';
                        if (b.opera) D = 'void(0);';
                        u = h.createFromHtml('<iframe style="width:100%;height:100%" frameBorder="0" tabIndex="-1" allowTransparency="true" src="javascript:' + encodeURIComponent(D) + '"' + '></iframe>');
                        var E = q.lang.editorTitle.replace('%1', q.name);
                        if (b.gecko) {
                            u.on('load', function (F) {
                                F.removeListener();
                                C(u.$.contentWindow);
                            });
                            s.setAttributes({
                                role: 'region',
                                title: E
                            });
                            u.setAttributes({
                                role: 'region',
                                title: ' '
                            });
                        } else if (b.webkit) {
                            u.setAttribute('title', E);
                            u.setAttribute('name', E);
                        } else if (c) {
                            t = h.createFromHtml('<fieldset style="height:100%' + (c && b.quirks ? ';position:relative' : '') + '">' + '<legend style="display:block;width:0;height:0;overflow:hidden;' + (c && b.quirks ? 'position:absolute' : '') + '">' + e.htmlEncode(E) + '</legend>' + '</fieldset>', a.document);
                            u.appendTo(t);
                            t.appendTo(s);
                        }
                        if (!c) s.append(u);
                    },
                        B = '<script id="cke_actscrpt" type="text/javascript">window.onload = function(){window.parent.CKEDITOR._["contentDomReady' + q.name + '"]( window );' + '}' + '</script>',
                        C = function (D) {
                        if (x) return;
                        x = 1;
                        var E = D.document,
                            F = E.body,
                            G = E.getElementById('cke_actscrpt');
                        G.parentNode.removeChild(G);
                        delete a._['contentDomReady' + q.name];
                        F.spellcheck = !q.config.disableNativeSpellChecker;
                        if (c) {
                            F.hideFocus = true;
                            F.disabled = true;
                            F.contentEditable = true;
                            F.removeAttribute('disabled');
                        } else E.designMode = 'on';
                        try {
                            E.execCommand('enableObjectResizing', false, !q.config.disableObjectResizing);
                        } catch(M) {}
                        try {
                            E.execCommand('enableInlineTableEditing', false, !q.config.disableNativeTableHandles);
                        } catch(N) {}
                        D = q.window = new d.window(D);
                        E = q.document = new g(E);
                        var H = E.getBody().getFirst();
                        if (b.gecko && H && H.is && H.is('br') && H.hasAttribute('_moz_editor_bogus_node')) {
                            var I = E.$.createEvent('KeyEvents');
                            I.initKeyEvent('keypress', true, true, D.$, false, false, false, false, 0, 32);
                            E.$.dispatchEvent(I);
                            var J = E.getBody().getFirst();
                            if (q.config.enterMode == 2) E.createElement('br', {
                                attributes: {
                                    _moz_dirty: ''
                                }
                            }).replace(J);
                            else J.remove();
                        }
                        if (! (c || b.opera)) E.on('mousedown', function (O) {
                            var P = O.data.getTarget();
                            if (P.is('img', 'hr', 'input', 'textarea', 'select')) q.getSelection().selectElement(P);
                        });
                        if (b.webkit) {
                            E.on('click', function (O) {
                                if (O.data.getTarget().is('input', 'select')) O.data.preventDefault();
                            });
                            E.on('mouseup', function (O) {
                                if (O.data.getTarget().is('input', 'textarea')) O.data.preventDefault();
                            });
                        }
                        var K = c || b.safari ? D : E;
                        K.on('blur', function () {
                            q.focusManager.blur();
                        });
                        K.on('focus', function () {
                            q.focusManager.focus();
                        });
                        var L = q.keystrokeHandler;
                        if (L) L.attach(E);
                        if (q.contextMenu) q.contextMenu.addTarget(E);
                        setTimeout(function () {
                            q.fire('contentDom');
                            if (y) {
                                q.mode = 'wysiwyg';
                                q.fire('mode');
                                y = false;
                            }
                            v = false;
                            if (w) {
                                q.focus();
                                w = false;
                            }
                            if (c) setTimeout(function () {
                                if (q.document) {
                                    var O = q.document.$.body;
                                    O.runtimeStyle.marginBottom = '0px';
                                    O.runtimeStyle.marginBottom = '';
                                }
                            },
                            1000);
                        },
                        0);
                    };
                    q.addMode('wysiwyg', {
                        load: function (D, E, F) {
                            s = D;
                            if (c && b.quirks) D.setStyle('position', 'relative');
                            q.mayBeDirty = true;
                            y = true;
                            if (F) this.loadSnapshotData(E);
                            else this.loadData(E);
                        },
                        loadData: function (D) {
                            v = true;
                            if (q.dataProcessor) D = q.dataProcessor.toHtml(D, r);
                            //D = q.config.docType + '<html dir="' + q.config.contentsLangDirection + '">' + '<head>' + '<link href="' + q.config.contentsCss + '" type="text/css" rel="stylesheet" _fcktemp="true"/>' + '<style type="text/css" _fcktemp="true">' + q._.styles.join('\n') + '</style>' + '</head>' + '<body>' + D + '</body>' + '</html>' + B;

							var tmp = D;
							
							if(!q.config.scriptAgregado){
								var patt1=/"toggle\([^\"]+\)/g;
								var results = tmp.match(patt1);
								
								if(results!=null){
									tmp += "<script type=\"text/javascript\">";
									
									for(i=0;i<results.length;i++){
										tmp += "document.getElementById("+results[i].substring(8,results[i].length-1)+").style.display='block';";
									}
									tmp += "</script>";
								}
							}
							q.config.scriptAgregado = true;
							
							D = q.config.docType + '<html dir="' + q.config.contentsLangDirection + '">' + '<head>' ;
							
                    
							 if (q.config.contentsCss.constructor.toString().indexOf("Array") == -1){
								D+= '<link href="' + q.config.contentsCss + '" type="text/css" rel="stylesheet" _fcktemp="true"/>' ;
							 }else{
								for(i=0;i<q.config.contentsCss.length;i++){
									D+= '<link href="' + q.config.contentsCss[i] + '" type="text/css" rel="stylesheet" _fcktemp="true"/>' ;
								}
							 }
							 D+=  '<style type="text/css" _fcktemp="true">' + q._.styles.join('\n') + '</style>' + '</head>' + '<body>' + tmp + '</body>' + '</html>' + B;
							
                            window['_cke_htmlToLoad_' + q.name] = D;
                            a._['contentDomReady' + q.name] = C;
                            A();
                            if (b.opera) {
                                var E = u.$.contentWindow.document;
                                E.open();
                                E.write(D);
                                E.close();
                            }
                        },
                        getData: function () {
                            var D = u.getFrameDocument().getBody().getHtml();
                            if (q.dataProcessor) D = q.dataProcessor.toDataFormat(D, r);
                            if (q.config.ignoreEmptyParagraph) D = D.replace(m, '');
                            return D;
                        },
                        getSnapshotData: function () {
                            return u.getFrameDocument().getBody().getHtml();
                        },
                        loadSnapshotData: function (D) {
                            u.getFrameDocument().getBody().setHtml(D);
                        },
                        unload: function (D) {
                            q.window = q.document = u = s = w = null;
                            q.fire('contentDomUnload');
                        },
                        focus: function () {
                            if (v) w = true;
                            else if (q.window) {
                                q.window.focus();
                                q.selectionChange();
                            }
                        }
                    });
                    q.on('insertHtml', n, null, null, 20);
                    q.on('insertElement', o, null, null, 20);
                    q.on('selectionChange', p, null, null, 1);
                });
            }
        });
    })();
    i.disableObjectResizing = false;
    i.disableNativeTableHandles = true;
    i.disableNativeSpellChecker = true;
    i.ignoreEmptyParagraph = true;
    j.add('wsc', {
        init: function (l) {
            var m = 'checkspell',
                n = l.addCommand(m, new a.dialogCommand(m));
            n.modes = {
                wysiwyg: !b.opera && document.domain == window.location.hostname
            };
            l.ui.addButton('SpellChecker', {
                label: l.lang.spellCheck.toolbar,
                command: m
            });
            a.dialog.add(m, this.path + 'dialogs/wsc.js');
        }
    });
    i.wsc_customerId = i.wsc_customerId || '1:ua3xw1-2XyGJ3-GWruD3-6OFNT1-oXcuB1-nR6Bp4-hgQHc-EcYng3-sdRXG3-NOfFk';
    i.wsc_customLoaderScript = i.wsc_customLoaderScript || null;
    j.add('styles', {
        requires: ['selection']
    });
    a.editor.prototype.attachStyleStateChange = function (l, m) {
        var n = this._.styleStateChangeCallbacks;
        if (!n) {
            n = this._.styleStateChangeCallbacks = [];
            this.on('selectionChange', function (o) {
                for (var p = 0; p < n.length; p++) {
                    var q = n[p],
                        r = q.style.checkActive(o.data.path) ? 1 : 2;
                    if (q.state !== r) {
                        q.fn.call(this, r);
                        q.state !== r;
                    }
                }
            });
        }
        n.push({
            style: l,
            fn: m
        });
    };
    a.STYLE_BLOCK = 1;
    a.STYLE_INLINE = 2;
    a.STYLE_OBJECT = 3;
    (function () {
        var l = {
            address: 1,
            div: 1,
            h1: 1,
            h2: 1,
            h3: 1,
            h4: 1,
            h5: 1,
            h6: 1,
            p: 1,
            pre: 1
        },
            m = {
            a: 1,
            embed: 1,
            hr: 1,
            img: 1,
            li: 1,
            object: 1,
            ol: 1,
            table: 1,
            td: 1,
            tr: 1,
            ul: 1
        },
            n = /\s*(?:;\s*|$)/;
        a.style = function (L, M) {
            if (M) {
                L = e.clone(L);
                G(L.attributes, M);
                G(L.styles, M);
            }
            var N = this.element = (L.element || '*').toLowerCase();
            this.type = N == '#' || l[N] ? 1 : m[N] ? 3 : 2;
            this._ = {
                definition: L
            };
        };
        a.style.prototype = {
            apply: function (L) {
                K.call(this, L, false);
            },
            remove: function (L) {
                K.call(this, L, true);
            },
            applyToRange: function (L) {
                var M = this;
                return (M.applyToRange = M.type == 2 ? o : M.type == 1 ? q : null).call(M, L);
            },
            removeFromRange: function (L) {
                return (this.removeFromRange = this.type == 2 ? p : null).call(this, L);
            },
            applyToObject: function (L) {
                E(L, this);
            },
            checkActive: function (L) {
                switch (this.type) {
                case 1:
                    return this.checkElementRemovable(L.block || L.blockLimit, true);
                case 2:
                    var M = L.elements;
                    for (var N = 0, O; N < M.length; N++) {
                        O = M[N];
                        if (O == L.block || O == L.blockLimit) continue;
                        if (this.checkElementRemovable(O, true)) return true;
                    }
                }
                return false;
            },
            checkElementRemovable: function (L, M) {
                if (!L) return false;
                var N = this._.definition,
                    O;
                if (L.getName() == this.element) {
                    if (!M && !L.hasAttributes()) return true;
                    O = H(N);
                    if (O._length) {
                        for (var P in O) {
                            if (P == '_length') continue;
                            var Q = L.getAttribute(P);
                            if (O[P] == (P == 'style' ? J(Q, false) : Q)) {
                                if (!M) return true;
                            } else if (M) return false;
                        }
                        if (M) return true;
                    } else return true;
                }
                var R = I(this)[L.getName()];
                if (R) {
                    if (! (O = R.attributes)) return true;
                    for (var S = 0; S < O.length; S++) {
                        P = O[S][0];
                        var T = L.getAttribute(P);
                        if (T) {
                            var U = O[S][1];
                            if (U === null || typeof U == 'string' && T == U || U.test(T)) return true;
                        }
                    }
                }
                return false;
            }
        };
        a.style.getStyleText = function (L) {
            var M = L._ST;
            if (M) return M;
            M = L.styles;
            var N = L.attributes && L.attributes.style || '';
            if (N.length) N = N.replace(n, ';');
            for (var O in M) N += O + ':' + M[O] + ';';
            if (N.length) N = J(N);
            return L._ST = N;
        };

        function o(L) {
            var al = this;
            var M = L.document;
            if (L.collapsed) {
                var N = D(al, M);
                L.insertNode(N);
                L.moveToPosition(N, 2);
                return;
            }
            var O = al.element,
                P = al._.definition,
                Q, R = f[O] || (Q = true, f.span),
                S = L.createBookmark();
            L.enlarge(1);
            L.trim();
            var T = L.getBoundaryNodes(),
                U = T.startNode,
                V = T.endNode.getNextSourceNode(true);
            if (!V) {
                var W;
                V = W = M.createText('');
                V.insertAfter(L.endContainer);
            }
            var X = V.getParent();
            if (X && X.getAttribute('_fck_bookmark')) V = X;
            if (V.equals(U)) {
                V = V.getNextSourceNode(true);
                if (!V) {
                    V = W = M.createText('');
                    V.insertAfter(U);
                }
            }
            var Y = U,
                Z, aa;
            while (Y) {
                var ab = false;
                if (Y.equals(V)) {
                    Y = null;
                    ab = true;
                } else {
                    var ac = Y.type,
                        ad = ac == 1 ? Y.getName() : null;
                    if (ad && Y.getAttribute('_fck_bookmark')) {
                        Y = Y.getNextSourceNode(true);
                        continue;
                    }
                    if (!ad || R[ad] && (Y.getPosition(V) | 4 | 0 | 8) == (4 + 0 + 8)) {
                        var ae = Y.getParent();
                        if (ae && ((ae.getDtd() || f.span)[O] || Q)) {
                            if (!Z && (!ad || !f.$removeEmpty[ad] || (Y.getPosition(V) | 4 | 0 | 8) == (4 + 0 + 8))) {
                                Z = new d.range(M);
                                Z.setStartBefore(Y);
                            }
                            if (ac == 3 || ac == 1 && !Y.getChildCount()) {
                                var af = Y,
                                    ag;
                                while (!af.$.nextSibling && (ag = af.getParent(), R[ag.getName()]) && ((ag.getPosition(U) | 2 | 0 | 8) == (2 + 0 + 8))) af = ag;
                                Z.setEndAfter(af);
                                if (!af.$.nextSibling) ab = true;
                                if (!aa) aa = ac != 3 || /[^\s\ufeff]/.test(Y.getText());
                            }
                        } else ab = true;
                    } else ab = true;
                    Y = Y.getNextSourceNode();
                }
                if (ab && aa && Z && !Z.collapsed) {
                    var ah = D(al, M),
                        ai = Z.getCommonAncestor();
                    while (ah && ai) {
                        if (ai.getName() == O) {
                            for (var aj in P.attributes) if (ah.getAttribute(aj) == ai.getAttribute(aj)) ah.removeAttribute(aj);
                            for (var ak in P.styles) if (ah.getStyle(ak) == ai.getStyle(ak)) ah.removeStyle(ak);
                            if (!ah.hasAttributes()) {
                                ah = null;
                                break;
                            }
                        }
                        ai = ai.getParent();
                    }
                    if (ah) {
                        Z.extractContents().appendTo(ah);
                        y(al, ah);
                        Z.insertNode(ah);
                        B(ah);
                        if (!c) ah.$.normalize();
                    }
                    Z = null;
                }
            }
            W && W.remove();
            L.moveToBookmark(S);
        };

        function p(L) {
            L.enlarge(1);
            var M = L.createBookmark(),
                N = M.startNode;
            if (L.collapsed) {
                var O = new d.elementPath(N.getParent()),
                    P;
                for (var Q = 0, R; Q < O.elements.length && (R = O.elements[Q]);
                Q++) {
                    if (R == O.block || R == O.blockLimit) break;
                    if (this.checkElementRemovable(R)) {
                        var S = L.checkBoundaryOfElement(R, 2),
                            T = !S && L.checkBoundaryOfElement(R, 1);
                        if (T || S) {
                            P = R;
                            P.match = T ? 'start' : 'end';
                        } else {
                            B(R);
                            x(this, R);
                        }
                    }
                }
                if (P) {
                    var U = N;
                    for (Q = 0; true; Q++) {
                        var V = O.elements[Q];
                        if (V.equals(P)) break;
                        else if (V.match) continue;
                        else V = V.clone();
                        V.append(U);
                        U = V;
                    }
                    U[P.match == 'start' ? 'insertBefore' : 'insertAfter'](P);
                }
            } else {
                var W = M.endNode,
                    X = this;

                function Y() {
                    var ab = new d.elementPath(N.getParent()),
                        ac = new d.elementPath(W.getParent()),
                        ad = null,
                        ae = null;
                    for (var af = 0; af < ab.elements.length; af++) {
                        var ag = ab.elements[af];
                        if (ag == ab.block || ag == ab.blockLimit) break;
                        if (X.checkElementRemovable(ag)) ad = ag;
                    }
                    for (af = 0; af < ac.elements.length; af++) {
                        ag = ac.elements[af];
                        if (ag == ac.block || ag == ac.blockLimit) break;
                        if (X.checkElementRemovable(ag)) ae = ag;
                    }
                    if (ae) W.breakParent(ae);
                    if (ad) N.breakParent(ad);
                };
                Y();
                var Z = N.getNext();
                while (!Z.equals(W)) {
                    var aa = Z.getNextSourceNode();
                    if (Z.type == 1 && this.checkElementRemovable(Z)) {
                        if (Z.getName() == this.element) x(this, Z);
                        else z(Z, I(this)[Z.getName()]);
                        if (aa.type == 1 && aa.contains(N)) {
                            Y();
                            aa = N.getNext();
                        }
                    }
                    Z = aa;
                }
            }
            L.moveToBookmark(M);
        };

        function q(L) {
            var M = L.createBookmark(true),
                N = L.createIterator();
            N.enforceRealBlocks = true;
            var O, P = L.document,
                Q;
            while (O = N.getNextParagraph()) {
                var R = D(this, P);
                r(O, R);
            }
            L.moveToBookmark(M);
        };

        function r(L, M) {
            var N = M.is('pre'),
                O = L.is('pre'),
                P = N && !O,
                Q = !N && O;
            if (P) M = w(L, M);
            else if (Q) M = v(t(L), M);
            else L.moveChildren(M);
            M.replace(L);
            if (N) s(M);
        };

        function s(L) {
            var M;
            if (! ((M = L.getPreviousSourceNode(true, 1)) && (M.is && M.is('pre')))) return;
            var N = u(M.getHtml(), /\n$/, '') + '\n\n' + u(L.getHtml(), /^\n/, '');
            if (c) L.$.outerHTML = '<pre>' + N + '</pre>';
            else L.setHtml(N);
            M.remove();
        };

        function t(L) {
            var M = /(\S\s*)\n(?:\s|(<span[^>]+_fck_bookmark.*?\/span>))*\n(?!$)/gi,
                N = L.getName(),
                O = u(L.getOuterHtml(), M, function (Q, R, S) {
                return R + '</pre>' + S + '<pre>';
            }),
                P = [];
            O.replace(/<pre>([\s\S]*?)<\/pre>/gi, function (Q, R) {
                P.push(R);
            });
            return P;
        };

        function u(L, M, N) {
            var O = '',
                P = '';
            L = L.replace(/(^<span[^>]+_fck_bookmark.*?\/span>)|(<span[^>]+_fck_bookmark.*?\/span>$)/gi, function (Q, R, S) {
                R && (O = R);
                S && (P = S);
                return '';
            });
            return O + L.replace(M, N) + P;
        };

        function v(L, M) {
            var N = new d.documentFragment(M.getDocument());
            for (var O = 0; O < L.length; O++) {
                var P = L[O];
                P = P.replace(/(\r\n|\r)/g, '\n');
                P = u(P, /^[ \t]*\n/, '');
                P = u(P, /\n$/, '');
                P = u(P, /^[ \t]+|[ \t]+$/g, function (R, S, T) {
                    if (R.length == 1) return '&nbsp;';
                    else if (!S) return e.repeat('&nbsp;', R.length - 1) + ' ';
                    else return ' ' + e.repeat('&nbsp;', R.length - 1);
                });
                P = P.replace(/\n/g, '<br>');
                P = P.replace(/[ \t]{2,}/g, function (R) {
                    return e.repeat('&nbsp;', R.length - 1) + ' ';
                });
                var Q = M.clone();
                Q.setHtml(P);
                N.append(Q);
            }
            return N;
        };

        function w(L, M) {
            var N = L.getHtml();
            N = u(N, /(?:^[ \t\n\r]+)|(?:[ \t\n\r]+$)/g, '');
            N = N.replace(/[ \t\r\n]*(<br[^>]*>)[ \t\r\n]*/gi, '$1');
            N = N.replace(/([ \t\n\r]+|&nbsp;)/g, ' ');
            N = N.replace(/<br\b[^>]*>/gi, '\n');
            if (c) {
                var O = L.getDocument().createElement('div');
                O.append(M);
                M.$.outerHTML = '<pre>' + N + '</pre>';
                M = O.getFirst().remove();
            } else M.setHtml(N);
            return M;
        };

        function x(L, M) {
            var N = L._.definition,
                O = N.attributes,
                P = N.styles,
                Q = I(L);

            function R() {
                for (var T in O) {
                    if (T == 'class' && M.getAttribute(T) != O[T]) continue;
                    M.removeAttribute(T);
                }
            };
            R();
            for (var S in P) M.removeStyle(S);
            O = Q[M.getName()];
            if (O) R();
            A(M);
        };

        function y(L, M) {
            var N = L._.definition,
                O = N.attributes,
                P = N.styles,
                Q = I(L),
                R = M.getElementsByTag(L.element);
            for (var S = R.count(); --S >= 0;) x(L, R.getItem(S));
            for (var T in Q) if (T != L.element) {
                R = M.getElementsByTag(T);
                for (S = R.count() - 1; S >= 0; S--) {
                    var U = R.getItem(S);
                    z(U, Q[T]);
                }
            }
        };

        function z(L, M) {
            var N = M && M.attributes;
            if (N) for (var O = 0; O < N.length; O++) {
                var P = N[O][0],
                    Q;
                if (Q = L.getAttribute(P)) {
                    var R = N[O][1];
                    if (R === null || R.test && R.test(Q) || typeof R == 'string' && Q == R) L.removeAttribute(P);
                }
            }
            A(L);
        };

        function A(L) {
            if (!L.hasAttributes()) {
                var M = L.getFirst(),
                    N = L.getLast();
                L.remove(true);
                if (M) {
                    B(M);
                    if (N && !M.equals(N)) B(N);
                }
            }
        };

        function B(L) {
            if (!L || L.type != 1 || !f.$removeEmpty[L.getName()]) return;
            C(L, L.getNext(), true);
            C(L, L.getPrevious());
        };

        function C(L, M, N) {
            if (M && M.type == 1) {
                var O = M.getAttribute('_fck_bookmark');
                if (O) M = N ? M.getNext() : M.getPrevious();
                if (M && M.type == 1 && L.isIdentical(M)) {
                    var P = N ? L.getLast() : L.getFirst();
                    if (O)(N ? M.getPrevious() : M.getNext()).move(L, !N);
                    M.moveChildren(L, !N);
                    M.remove();
                    if (P) B(P);
                }
            }
        };

        function D(L, M) {
            var N, O = L._.definition,
                P = L.element;
            if (P == '*') P = 'span';
            N = new h(P, M);
            return E(N, L);
        };

        function E(L, M) {
            var N = M._.definition,
                O = N.attributes,
                P = a.style.getStyleText(N);
            if (O) for (var Q in O) L.setAttribute(Q, O[Q]);
            if (P) L.setAttribute('style', P);
            return L;
        };
        var F = /#\((.+?)\)/g;

        function G(L, M) {
            for (var N in L) L[N] = L[N].replace(F, function (O, P) {
                return M[P];
            });
        };

        function H(L) {
            var M = L._AC;
            if (M) return M;
            M = {};
            var N = 0,
                O = L.attributes;
            if (O) for (var P in O) {
                N++;
                M[P] = O[P];
            }
            var Q = a.style.getStyleText(L);
            if (Q) {
                if (!M.style) N++;
                M.style = Q;
            }
            M._length = N;
            return L._AC = M;
        };

        function I(L) {
            if (L._.overrides) return L._.overrides;
            var M = L._.overrides = {},
                N = L._.definition.overrides;
            if (N) {
                if (!e.isArray(N)) N = [N];
                for (var O = 0; O < N.length; O++) {
                    var P = N[O],
                        Q, R, S;
                    if (typeof P == 'string') Q = P.toLowerCase();
                    else {
                        Q = P.element ? P.element.toLowerCase() : L.element;
                        S = P.attributes;
                    }
                    R = M[Q] || (M[Q] = {});
                    if (S) {
                        var T = R.attributes = R.attributes || [];
                        for (var U in S) T.push([U.toLowerCase(), S[U]]);
                    }
                }
            }
            return M;
        };

        function J(L, M) {
            var N;
            if (M !== false) {
                var O = new h('span');
                O.setAttribute('style', L);
                N = O.getAttribute('style');
            } else N = L;
            return N.replace(/\s*([;:])\s*/, '$1').replace(/([^\s;])$/, '$1;').toLowerCase();
        };

        function K(L, M) {
            var N = L.getSelection(),
                O = N.getRanges(),
                P = M ? this.removeFromRange : this.applyToRange;
            for (var Q = 0; Q < O.length; Q++) P.call(this, O[Q]);
            N.selectRanges(O);
        };
    })();
    a.styleCommand = function (l) {
        this.style = l;
    };
    a.styleCommand.prototype.exec = function (l) {
        var n = this;
        l.focus();
        var m = l.document;
        if (m) if (n.state == 2) n.style.apply(m);
        else if (n.state == 1) n.style.remove(m);
        return !!m;
    };
    j.add('domiterator');
    (function () {
        var l = function (n) {
            var o = this;
            if (arguments.length < 1) return;
            o.range = n;
            o.forceBrBreak = false;
            o.enlargeBr = true;
            o.enforceRealBlocks = false;
            o._ || (o._ = {});
        },
            m = /^[\r\n\t ]+$/;
        l.prototype = {
            getNextParagraph: function (n) {
                var O = this;
                var o, p, q, r, s;
                if (!O._.lastNode) {
                    p = O.range.clone();
                    p.enlarge(O.forceBrBreak || !O.enlargeBr ? 3 : 2);
                    var t = new d.walker(p),
                        u = d.walker.bookmark(true, true);
                    t.evaluator = u;
                    O._.nextNode = t.next();
                    t = new d.walker(p);
                    t.evaluator = u;
                    var v = t.previous();
                    O._.lastNode = v.getNextSourceNode(true);
                    if (O._.lastNode && O._.lastNode.type == 3 && !e.trim(O._.lastNode.getText()) && O._.lastNode.getParent().isBlockBoundary()) {
                        var w = new d.range(p.document);
                        w.moveToPosition(O._.lastNode, 4);
                        if (w.checkEndOfBlock()) {
                            var x = new d.elementPath(w.endContainer),
                                y = x.block || x.blockLimit;
                            O._.lastNode = y.getNextSourceNode(true);
                        }
                    }
                    if (!O._.lastNode) {
                        O._.lastNode = O._.docEndMarker = p.document.createText('');
                        O._.lastNode.insertAfter(v);
                    }
                    p = null;
                }
                var z = O._.nextNode;
                v = O._.lastNode;
                O._.nextNode = null;
                while (z) {
                    var A = false,
                        B = z.type != 1,
                        C = false;
                    if (!B) {
                        var D = z.getName();
                        if (z.isBlockBoundary(O.forceBrBreak && {
                            br: 1
                        })) {
                            if (D == 'br') B = true;
                            else if (!p && !z.getChildCount() && D != 'hr') {
                                o = z;
                                q = z.equals(v);
                                break;
                            }
                            if (p) {
                                p.setEndAt(z, 3);
                                if (D != 'br') O._.nextNode = z;
                            }
                            A = true;
                        } else {
                            if (z.getFirst()) {
                                if (!p) {
                                    p = new d.range(O.range.document);
                                    p.setStartAt(z, 3);
                                }
                                z = z.getFirst();
                                continue;
                            }
                            B = true;
                        }
                    } else if (z.type == 3) if (m.test(z.getText())) B = false;
                    if (B && !p) {
                        p = new d.range(O.range.document);
                        p.setStartAt(z, 3);
                    }
                    q = (!A || B) && (z.equals(v));
                    if (p && !A) while (!z.getNext() && !q) {
                        var E = z.getParent();
                        if (E.isBlockBoundary(O.forceBrBreak && {
                            br: 1
                        })) {
                            A = true;
                            q = q || E.equals(v);
                            break;
                        }
                        z = E;
                        B = true;
                        q = z.equals(v);
                        C = true;
                    }
                    if (B) p.setEndAt(z, 4);
                    z = z.getNextSourceNode(C, null, v);
                    q = !z;
                    if ((A || q) && (p)) {
                        var F = p.getBoundaryNodes(),
                            G = new d.elementPath(p.startContainer),
                            H = new d.elementPath(p.endContainer);
                        if (F.startNode.equals(F.endNode) && F.startNode.getParent().equals(G.blockLimit) && F.startNode.type == 1 && F.startNode.getAttribute('_fck_bookmark')) {
                            p = null;
                            O._.nextNode = null;
                        } else break;
                    }
                    if (q) break;
                }
                if (!o) {
                    if (!p) {
                        O._.docEndMarker && O._.docEndMarker.remove();
                        O._.nextNode = null;
                        return null;
                    }
                    G = new d.elementPath(p.startContainer);
                    var I = G.blockLimit,
                        J = {
                        div: 1,
                        th: 1,
                        td: 1
                    };
                    o = G.block;
                    if (!o && !O.enforceRealBlocks && J[I.getName()] && p.checkStartOfBlock() && p.checkEndOfBlock()) o = I;
                    else if (!o || O.enforceRealBlocks && o.getName() == 'li') {
                        o = O.range.document.createElement(n || 'p');
                        p.extractContents().appendTo(o);
                        o.trim();
                        p.insertNode(o);
                        r = s = true;
                    } else if (o.getName() != 'li') {
                        if (!p.checkStartOfBlock() || !p.checkEndOfBlock()) {
                            o = o.clone(false);
                            p.extractContents().appendTo(o);
                            o.trim();
                            var K = p.splitBlock();
                            r = !K.wasStartOfBlock;
                            s = !K.wasEndOfBlock;
                            p.insertNode(o);
                        }
                    } else if (!q) O._.nextNode = o.equals(v) ? null : p.getBoundaryNodes().endNode.getNextSourceNode(true, null, v);
                }
                if (r) {
                    var L = o.getPrevious();
                    if (L && L.type == 1) if (L.getName() == 'br') L.remove();
                    else if (L.getLast() && L.getLast().$.nodeName.toLowerCase() == 'br') L.getLast().remove();
                }
                if (s) {
                    var M = d.walker.bookmark(false, true),
                        N = o.getLast();
                    if (N && N.type == 1 && N.getName() == 'br') if (c || N.getPrevious(M) || N.getNext(M)) N.remove();
                }
                if (!O._.nextNode) O._.nextNode = q || o.equals(v) ? null : o.getNextSourceNode(true, null, v);
                return o;
            }
        };
        d.range.prototype.createIterator = function () {
            return new l(this);
        };
    })();
    j.add('panelbutton', {
        requires: ['button'],
        beforeInit: function (l) {
            l.ui.addHandler(4, k.panelButton.handler);
        }
    });
    a.UI_PANELBUTTON = 4;
    (function () {
        var l = function (m) {
            var o = this;
            var n = o._;
            if (n.state == 0) return;
            o.createPanel(m);
            if (n.on) {
                n.panel.hide();
                return;
            }
            n.panel.showBlock(o._.id, o.document.getById(o._.id), 4);
        };
        k.panelButton = e.createClass({
            base: k.button,
            $: function (m) {
                var o = this;
                var n = m.panel;
                delete m.panel;
                o.base(m);
                o.document = n && n.parent && n.parent.getDocument() || a.document;
                o.hasArrow = true;
                o.click = l;
                o._ = {
                    panelDefinition: n
                };
            },
            statics: {
                handler: {
                    create: function (m) {
                        return new k.panelButton(m);
                    }
                }
            },
            proto: {
                createPanel: function (m) {
                    var n = this._;
                    if (n.panel) return;
                    var o = this._.panelDefinition || {},
                        p = o.parent || a.document.getBody(),
                        q = this._.panel = new k.floatPanel(m, p, o),
                        r = this;
                    q.onShow = function () {
                        if (r.className) this.element.getFirst().addClass(r.className + '_panel');
                        n.oldState = r._.state;
                        r.setState(1);
                        n.on = 1;
                        if (r.onOpen) r.onOpen();
                    };
                    q.onHide = function () {
                        if (r.className) this.element.getFirst().removeClass(r.className + '_panel');
                        r.setState(n.oldState);
                        n.on = 0;
                        if (r.onClose) r.onClose();
                    };
                    q.onEscape = function () {
                        q.hide();
                        r.document.getById(n.id).focus();
                    };
                    if (this.onBlock) this.onBlock(q, n.id);
                    q.getBlock(n.id).onHide = function () {
                        n.on = 0;
                        r.setState(2);
                    };
                }
            }
        });
    })();
    j.add('floatpanel', {
        requires: ['panel']
    });
    (function () {
        var l = {},
            m = false;

        function n(o, p, q, r, s) {
            var t = p.getUniqueId() + '-' + q.getUniqueId() + '-' + o.skinName + '-' + o.lang.dir + (o.uiColor && '-' + o.uiColor || '') + (r.css && '-' + r.css || '') + (s && '-' + s || ''),
                u = l[t];
            if (!u) {
                u = l[t] = new k.panel(p, r);
                u.element = q.append(h.createFromHtml(u.renderHtml(o), p));
                u.element.setStyles({
                    display: 'none',
                    position: 'absolute'
                });
            }
            return u;
        };
        k.floatPanel = e.createClass({
            $: function (o, p, q, r) {
                q.forceIFrame = true;
                var s = p.getDocument(),
                    t = n(o, s, p, q, r || 0),
                    u = t.element,
                    v = u.getFirst().getFirst();
                this.element = u;
                this._ = {
                    panel: t,
                    parentElement: p,
                    definition: q,
                    document: s,
                    iframe: v,
                    children: [],
                    dir: o.lang.dir
                };
            },
            proto: {
                addBlock: function (o, p) {
                    return this._.panel.addBlock(o, p);
                },
                addListBlock: function (o, p) {
                    return this._.panel.addListBlock(o, p);
                },
                getBlock: function (o) {
                    return this._.panel.getBlock(o);
                },
                showBlock: function (o, p, q, r, s) {
                    var t = this._.panel,
                        u = t.showBlock(o);
                    this.allowBlur(false);
                    m = true;
                    var v = this.element,
                        w = this._.iframe,
                        x = this._.definition,
                        y = p.getDocumentPosition(v.getDocument()),
                        z = this._.dir == 'rtl',
                        A = y.x + (r || 0),
                        B = y.y + (s || 0);
                    if (z && (q == 1 || q == 4) || !z && (q == 2 || q == 3)) A += p.$.offsetWidth - 1;
                    if (q == 3 || q == 4) B += p.$.offsetHeight - 1;
                    this._.panel._.offsetParentId = p.getId();
                    v.setStyles({
                        top: B + 'px',
                        left: '-3000px',
                        visibility: 'hidden',
                        opacity: '0',
                        display: ''
                    });
                    if (!this._.blurSet) {
                        var C = c ? w : new d.window(w.$.contentWindow);
                        a.event.useCapture = true;
                        C.on('blur', function (D) {
                            var G = this;
                            if (c && !G.allowBlur()) return;
                            var E = D.data.getTarget(),
                                F = E.getWindow && E.getWindow();
                            if (F && F.equals(C)) return;
                            if (G.visible && !G._.activeChild && !m) G.hide();
                        },
                        this);
                        C.on('focus', function () {
                            this._.focused = true;
                            this.hideChild();
                            this.allowBlur(true);
                        },
                        this);
                        a.event.useCapture = false;
                        this._.blurSet = 1;
                    }
                    t.onEscape = e.bind(function () {
                        this.onEscape && this.onEscape();
                    },
                    this);
                    e.setTimeout(function () {
                        if (z) A -= v.$.offsetWidth;
                        v.setStyles({
                            left: A + 'px',
                            visibility: '',
                            opacity: '1'
                        });
                        if (u.autoSize) {
                            function D() {
                                var E = v.getFirst(),
                                    F = u.element.$.scrollHeight;
                                if (c && b.quirks && F > 0) F += (E.$.offsetHeight || 0) - (E.$.clientHeight || 0);
                                E.setStyle('height', F + 'px');
                                t._.currentBlock.element.setStyle('display', 'none').removeStyle('display');
                            };
                            if (t.isLoaded) D();
                            else t.onLoad = D;
                        } else v.getFirst().removeStyle('height');
                        e.setTimeout(function () {
                            if (x.voiceLabel) if (b.gecko) {
                                var E = w.getParent();
                                E.setAttribute('role', 'region');
                                E.setAttribute('title', x.voiceLabel);
                                w.setAttribute('role', 'region');
                                w.setAttribute('title', ' ');
                            }
                            if (c && b.quirks) w.focus();
                            else w.$.contentWindow.focus();
                            if (c && !b.quirks) this.allowBlur(true);
                        },
                        0, this);
                    },
                    0, this);
                    this.visible = 1;
                    if (this.onShow) this.onShow.call(this);
                    m = false;
                },
                hide: function () {
                    var o = this;
                    if (o.visible && (!o.onHide || o.onHide.call(o) !== true)) {
                        o.hideChild();
                        o.element.setStyle('display', 'none');
                        o.visible = 0;
                    }
                },
                allowBlur: function (o) {
                    var p = this._.panel;
                    if (o != undefined) p.allowBlur = o;
                    return p.allowBlur;
                },
                showAsChild: function (o, p, q, r, s, t) {
                    if (this._.activeChild == o && o._.panel._.offsetParentId == q.getId()) return;
                    this.hideChild();
                    o.onHide = e.bind(function () {
                        e.setTimeout(function () {
                            if (!this._.focused) this.hide();
                        },
                        0, this);
                    },
                    this);
                    this._.activeChild = o;
                    this._.focused = false;
                    o.showBlock(p, q, r, s, t);
                    if (b.ie7Compat || b.ie8 && b.ie6Compat) setTimeout(function () {
                        o.element.getChild(0).$.style.cssText += '';
                    },
                    100);
                },
                hideChild: function () {
                    var o = this._.activeChild;
                    if (o) {
                        delete o.onHide;
                        delete this._.activeChild;
                        o.hide();
                    }
                }
            }
        });
    })();
    j.add('menu', {
        beforeInit: function (l) {
            var m = l.config.menu_groups.split(','),
                n = {};
            for (var o = 0; o < m.length; o++) n[m[o]] = o + 1;
            l._.menuGroups = n;
            l._.menuItems = {};
        },
        requires: ['floatpanel']
    });
    e.extend(a.editor.prototype, {
        addMenuGroup: function (l, m) {
            this._.menuGroups[l] = m || 100;
        },
        addMenuItem: function (l, m) {
            if (this._.menuGroups[m.group]) this._.menuItems[l] = new a.menuItem(this, l, m);
        },
        addMenuItems: function (l) {
            for (var m in l) this.addMenuItem(m, l[m]);
        },
        getMenuItem: function (l) {
            return this._.menuItems[l];
        }
    });
    (function () {
        a.menu = e.createClass({
            $: function (m, n) {
                var o = this;
                o.id = 'cke_' + e.getNextNumber();
                o.editor = m;
                o.items = [];
                o._.level = n || 1;
            },
            _: {
                showSubMenu: function (m) {
                    var s = this;
                    var n = s._.subMenu,
                        o = s.items[m],
                        p = o.getItems && o.getItems();
                    if (!p) {
                        s._.panel.hideChild();
                        return;
                    }
                    if (n) n.removeAll();
                    else {
                        n = s._.subMenu = new a.menu(s.editor, s._.level + 1);
                        n.parent = s;
                        n.onClick = e.bind(s.onClick, s);
                    }
                    for (var q in p) n.add(s.editor.getMenuItem(q));
                    var r = s._.panel.getBlock(s.id).element.getDocument().getById(s.id + String(m));
                    n.show(r, 2);
                }
            },
            proto: {
                add: function (m) {
                    if (!m.order) m.order = this.items.length;
                    this.items.push(m);
                },
                removeAll: function () {
                    this.items = [];
                },
                show: function (m, n, o, p) {
                    var q = this.items,
                        r = this.editor,
                        s = this._.panel,
                        t = this._.element;
                    if (!s) {
                        s = this._.panel = new k.floatPanel(this.editor, a.document.getBody(), {
                            css: [a.getUrl(r.skinPath + 'editor.css')],
                            level: this._.level - 1,
                            className: r.skinClass + ' cke_contextmenu'
                        },
                        this._.level);
                        s.onEscape = e.bind(function () {
                            this.onEscape && this.onEscape();
                            this.hide();
                        },
                        this);
                        s.onHide = e.bind(function () {
                            this.onHide && this.onHide();
                        },
                        this);
                        var u = s.addBlock(this.id);
                        u.autoSize = true;
                        var v = u.keys;
                        v[40] = 'next';
                        v[9] = 'next';
                        v[38] = 'prev';
                        v[2000 + 9] = 'prev';
                        v[32] = 'click';
                        v[39] = 'click';
                        t = this._.element = u.element;
                        t.addClass(r.skinClass);
                        var w = t.getDocument();
                        w.getBody().setStyle('overflow', 'hidden');
                        w.getElementsByTag('html').getItem(0).setStyle('overflow', 'hidden');
                        this._.itemOverFn = e.addFunction(function (C) {
                            var D = this;
                            clearTimeout(D._.showSubTimeout);
                            D._.showSubTimeout = e.setTimeout(D._.showSubMenu, r.config.menu_subMenuDelay, D, [C]);
                        },
                        this);
                        this._.itemOutFn = e.addFunction(function (C) {
                            clearTimeout(this._.showSubTimeout);
                        },
                        this);
                        this._.itemClickFn = e.addFunction(function (C) {
                            var E = this;
                            var D = E.items[C];
                            if (D.state == 0) {
                                E.hide();
                                return;
                            }
                            if (D.getItems) E._.showSubMenu(C);
                            else E.onClick && E.onClick(D);
                        },
                        this);
                    }
                    l(q);
                    var x = ['<div class="cke_menu">'],
                        y = q.length,
                        z = y && q[0].group;
                    for (var A = 0; A < y; A++) {
                        var B = q[A];
                        if (z != B.group) {
                            x.push('<div class="cke_menuseparator"></div>');
                            z = B.group;
                        }
                        B.render(this, A, x);
                    }
                    x.push('</div>');
                    t.setHtml(x.join(''));
                    if (this.parent) this.parent._.panel.showAsChild(s, this.id, m, n, o, p);
                    else s.showBlock(this.id, m, n, o, p);
                },
                hide: function () {
                    this._.panel && this._.panel.hide();
                }
            }
        });

        function l(m) {
            m.sort(function (n, o) {
                if (n.group < o.group) return -1;
                else if (n.group > o.group) return 1;
                return n.order < o.order ? -1 : n.order > o.order ? 1 : 0;
            });
        };
    })();
    a.menuItem = e.createClass({
        $: function (l, m, n) {
            var o = this;
            e.extend(o, n, {
                order: 0,
                className: 'cke_button_' + m
            });
            o.group = l._.menuGroups[o.group];
            o.editor = l;
            o.name = m;
        },
        proto: {
            render: function (l, m, n) {
                var t = this;
                var o = l.id + String(m),
                    p = typeof t.state == 'undefined' ? 2 : t.state,
                q = ' cke_' + (p == 1 ? 'on' : p == 0 ? 'disabled' : 'off'),
                r = t.label;
                if (p == 0) r = t.editor.lang.common.unavailable.replace('%1', r);
                if (t.className) q += ' ' + t.className;
                n.push('<span class="cke_menuitem"><a id="', o, '" class="', q, '" href="javascript:void(\'', (t.label || '').replace("'", ''), '\')" title="', t.label, '" tabindex="-1"_cke_focus=1 hidefocus="true"');
                if (b.opera || b.gecko && b.mac) n.push(' onkeypress="return false;"');
                if (b.gecko) n.push(' onblur="this.style.cssText = this.style.cssText;"');
                var s = (t.iconOffset || 0) * (-16);
                n.push(' onmouseover="CKEDITOR.tools.callFunction(', l._.itemOverFn, ',', m, ');" onmouseout="CKEDITOR.tools.callFunction(', l._.itemOutFn, ',', m, ');" onclick="CKEDITOR.tools.callFunction(', l._.itemClickFn, ',', m, '); return false;"><span class="cke_icon_wrapper"><span class="cke_icon"' + (t.icon ? ' style="background-image:url(' + a.getUrl(t.icon) + ');background-position:0 ' + s + 'px;"></span>' : '') + '></span></span>' + '<span class="cke_label">');
                if (t.getItems) n.push('<span class="cke_menuarrow"></span>');
                n.push(r, '</span></a></span>');
            }
        }
    });
    i.menu_subMenuDelay = 400;
    i.menu_groups = 'clipboard,form,tablecell,tablecellproperties,tablerow,tablecolumn,table,anchor,link,image,flash,checkbox,radio,textfield,hiddenfield,imagebutton,button,select,textarea';
    (function () {
        function l() {
            var v = this;
            try {
                var s = v.getSelection();
                if (!s) return;
                var t = s.getStartElement(),
                    u = new d.elementPath(t);
                if (!u.compare(v._.selectionPreviousPath)) {
                    v._.selectionPreviousPath = u;
                    v.fire('selectionChange', {
                        selection: s,
                        path: u,
                        element: t
                    });
                }
            } catch(w) {}
        };
        var m, n;

        function o() {
            n = true;
            if (m) return;
            p.call(this);
            m = e.setTimeout(p, 200, this);
        };

        function p() {
            m = null;
            if (n) {
                e.setTimeout(l, 0, this);
                n = false;
            }
        };
        var q = {
            exec: function (s) {
                switch (s.mode) {
                case 'wysiwyg':
                    s.document.$.execCommand('SelectAll', false, null);
                    break;
                case 'source':
                }
            },
            canUndo: false
        };
        j.add('selection', {
            init: function (s) {
                s.on('contentDom', function () {
                    var t = s.document;
                    if (c) {
                        var u, v;
                        t.on('focusin', function () {
                            if (u) {
                                try {
                                    u.select();
                                } catch(y) {}
                                u = null;
                            }
                        });
                        s.window.on('focus', function () {
                            v = true;
                            x();
                        });
                        s.document.on('beforedeactivate', function () {
                            v = false;
                            s.document.$.execCommand('Unselect');
                        });
                        t.on('mousedown', w);
                        t.on('mouseup', function () {
                            v = true;
                            setTimeout(function () {
                                x(true);
                            },
                            0);
                        });
                        t.on('keydown', w);
                        t.on('keyup', function () {
                            v = true;
                            x();
                        });
                        t.on('selectionchange', x);

                        function w() {
                            v = false;
                        };

                        function x(y) {
                            if (v) {
                                var z = s.document,
                                    A = z && z.$.selection;
                                if (y && A && A.type == 'None') if (!z.$.queryCommandEnabled('InsertImage')) {
                                    e.setTimeout(x, 50, this, true);
                                    return;
                                }
                                u = A && A.createRange();
                                o.call(s);
                            }
                        };
                    } else {
                        t.on('mouseup', o, s);
                        t.on('keyup', o, s);
                    }
                });
                s.addCommand('selectAll', q);
                s.ui.addButton('SelectAll', {
                    label: s.lang.selectAll,
                    command: 'selectAll'
                });
                s.selectionChange = o;
            }
        });
        a.editor.prototype.getSelection = function () {
            return this.document && this.document.getSelection();
        };
        a.editor.prototype.forceNextSelectionCheck = function () {
            delete this._.selectionPreviousPath;
        };
        g.prototype.getSelection = function () {
            var s = new d.selection(this);
            return !s || s.isInvalid ? null : s;
        };
        a.SELECTION_NONE = 1;
        a.SELECTION_TEXT = 2;
        a.SELECTION_ELEMENT = 3;
        d.selection = function (s) {
            var v = this;
            var t = s.getCustomData('cke_locked_selection');
            if (t) return t;
            v.document = s;
            v.isLocked = false;
            v._ = {
                cache: {}
            };
            if (c) {
                var u = v.getNative().createRange();
                if (!u || u.item && u.item(0).ownerDocument != v.document.$ || u.parentElement && u.parentElement().ownerDocument != v.document.$) v.isInvalid = true;
            }
            return v;
        };
        var r = {
            img: 1,
            hr: 1,
            li: 1,
            table: 1,
            tr: 1,
            td: 1,
            embed: 1,
            object: 1,
            ol: 1,
            ul: 1,
            a: 1,
            input: 1,
            form: 1,
            select: 1,
            textarea: 1,
            button: 1,
            fieldset: 1,
            th: 1,
            thead: 1,
            tfoot: 1
        };
        d.selection.prototype = {
            getNative: c ?
            function () {
                return this._.cache.nativeSel || (this._.cache.nativeSel = this.document.$.selection);
            } : function () {
                return this._.cache.nativeSel || (this._.cache.nativeSel = this.document.getWindow().$.getSelection());
            },
            getType: c ?
            function () {
                var s = this._.cache;
                if (s.type) return s.type;
                var t = 1;
                try {
                    var u = this.getNative(),
                        v = u.type;
                    if (v == 'Text') t = 2;
                    if (v == 'Control') t = 3;
                    if (u.createRange().parentElement) t = 2;
                } catch(w) {}
                return s.type = t;
            } : function () {
                var s = this._.cache;
                if (s.type) return s.type;
                var t = 2,
                    u = this.getNative();
                if (!u) t = 1;
                else if (u.rangeCount == 1) {
                    var v = u.getRangeAt(0),
                        w = v.startContainer;
                    if (w == v.endContainer && w.nodeType == 1 && v.endOffset - v.startOffset == 1 && r[w.childNodes[v.startOffset].nodeName.toLowerCase()]) t = 3;
                }
                return s.type = t;
            },
            getRanges: c ? (function () {
                var s = function (t, u) {
                    t = t.duplicate();
                    t.collapse(u);
                    var v = t.parentElement(),
                        w = v.childNodes,
                        x;
                    for (var y = 0; y < w.length; y++) {
                        var z = w[y];
                        if (z.nodeType == 1) {
                            x = t.duplicate();
                            x.moveToElementText(z);
                            x.collapse();
                            var A = x.compareEndPoints('StartToStart', t);
                            if (A > 0) break;
                            else if (A === 0) return {
                                container: v,
                                offset: y
                            };
                            x = null;
                        }
                    }
                    if (!x) {
                        x = t.duplicate();
                        x.moveToElementText(v);
                        x.collapse(false);
                    }
                    x.setEndPoint('StartToStart', t);
                    var B = x.text.replace(/(\r\n|\r)/g, '\n').length;
                    while (B > 0) B -= w[--y].nodeValue.length;
                    if (B === 0) return {
                        container: v,
                        offset: y
                    };
                    else return {
                        container: w[y],
                        offset: -B
                    };
                };
                return function () {
                    var E = this;
                    var t = E._.cache;
                    if (t.ranges) return t.ranges;
                    var u = E.getNative(),
                        v = u && u.createRange(),
                        w = E.getType(),
                        x;
                    if (!u) return [];
                    if (w == 2) {
                        x = new d.range(E.document);
                        var y = s(v, true);
                        x.setStart(new d.node(y.container), y.offset);
                        y = s(v);
                        x.setEnd(new d.node(y.container), y.offset);
                        return t.ranges = [x];
                    } else if (w == 3) {
                        var z = E._.cache.ranges = [];
                        for (var A = 0; A < v.length; A++) {
                            var B = v.item(A),
                                C = B.parentNode,
                                D = 0;
                            x = new d.range(E.document);
                            for (; D < C.childNodes.length && C.childNodes[D] != B; D++) {}
                            x.setStart(new d.node(C), D);
                            x.setEnd(new d.node(C), D + 1);
                            z.push(x);
                        }
                        return z;
                    }
                    return t.ranges = [];
                };
            })() : function () {
                var s = this._.cache;
                if (s.ranges) return s.ranges;
                var t = [],
                    u = this.getNative();
                if (!u) return [];
                for (var v = 0; v < u.rangeCount; v++) {
                    var w = u.getRangeAt(v),
                        x = new d.range(this.document);
                    x.setStart(new d.node(w.startContainer), w.startOffset);
                    x.setEnd(new d.node(w.endContainer), w.endOffset);
                    t.push(x);
                }
                return s.ranges = t;
            },
            getStartElement: function () {
                var z = this;
                var s = z._.cache;
                if (s.startElement !== undefined) return s.startElement;
                var t, u = z.getNative();
                switch (z.getType()) {
                case 3:
                    return z.getSelectedElement();
                case 2:
                    var v = z.getRanges()[0];
                    if (v) if (!v.collapsed) {
                        v.optimize();
                        for (;;) {
                            var w = v.startContainer,
                                x = v.startOffset;
                            if (x == (w.getChildCount ? w.getChildCount() : w.getLength())) v.setStartAfter(w);
                            else break;
                        }
                        t = v.startContainer;
                        if (t.type != 1) return t.getParent();
                        t = t.getChild(v.startOffset);
                        if (!t || t.type != 1) return v.startContainer;
                        var y = t.getFirst();
                        while (y && y.type == 1) {
                            t = y;
                            y = y.getFirst();
                        }
                        return t;
                    }
                    if (c) {
                        v = u.createRange();
                        v.collapse(true);
                        t = v.parentElement();
                    } else {
                        t = u.anchorNode;
                        if (t.nodeType != 1) t = t.parentNode;
                    }
                }
                return s.startElement = t ? new h(t) : null;
            },
            getSelectedElement: function () {
                var s = this._.cache;
                if (s.selectedElement !== undefined) return s.selectedElement;
                var t;
                if (this.getType() == 3) {
                    var u = this.getNative();
                    if (c) try {
                        t = u.createRange().item(0);
                    } catch(w) {} else {
                        var v = u.getRangeAt(0);
                        t = v.startContainer.childNodes[v.startOffset];
                    }
                }
                return s.selectedElement = t ? new h(t) : null;
            },
            lock: function () {
                var s = this;
                s.getRanges();
                s.getStartElement();
                s.getSelectedElement();
                s._.cache.nativeSel = {};
                s.isLocked = true;
                s.document.setCustomData('cke_locked_selection', s);
            },
            unlock: function (s) {
                var x = this;
                var t = x.document,
                    u = t.getCustomData('cke_locked_selection');
                if (u) {
                    t.setCustomData('cke_locked_selection', null);
                    if (s) {
                        var v = u.getSelectedElement(),
                            w = !v && u.getRanges();
                        x.isLocked = false;
                        x.reset();
                        t.getBody().focus();
                        if (v) x.selectElement(v);
                        else x.selectRanges(w);
                    }
                }
                if (!u || !s) {
                    x.isLocked = false;
                    x.reset();
                }
            },
            reset: function () {
                this._.cache = {};
            },
            selectElement: function (s) {
                var v = this;
                if (v.isLocked) {
                    var t = new d.range(v.document);
                    t.setStartBefore(s);
                    t.setEndAfter(s);
                    v._.cache.selectedElement = s;
                    v._.cache.startElement = s;
                    v._.cache.ranges = [t];
                    v._.cache.type = 3;
                    return;
                }
                if (c) {
                    v.getNative().empty();
                    try {
                        t = v.document.$.body.createControlRange();
                        t.addElement(s.$);
                        t.select();
                    } catch(w) {
                        t = v.document.$.body.createTextRange();
                        t.moveToElementText(s.$);
                        t.select();
                    }
                    v.reset();
                } else {
                    t = v.document.$.createRange();
                    t.selectNode(s.$);
                    var u = v.getNative();
                    u.removeAllRanges();
                    u.addRange(t);
                    v.reset();
                }
            },
            selectRanges: function (s) {
                var y = this;
                if (y.isLocked) {
                    y._.cache.selectedElement = null;
                    y._.cache.startElement = s[0].getTouchedStartNode();
                    y._.cache.ranges = s;
                    y._.cache.type = 2;
                    return;
                }
                if (c) {
                    if (s[0]) s[0].select();
                    y.reset();
                } else {
                    var t = y.getNative();
                    t.removeAllRanges();
                    for (var u = 0; u < s.length; u++) {
                        var v = s[u],
                            w = y.document.$.createRange(),
                            x = v.startContainer;
                        if (v.collapsed && b.gecko && b.version < 10900 && x.type == 1 && !x.getChildCount()) x.appendText('');
                        w.setStart(x.$, v.startOffset);
                        w.setEnd(v.endContainer.$, v.endOffset);
                        t.addRange(w);
                    }
                    y.reset();
                }
            },
            createBookmarks: function (s) {
                var t = [],
                    u = this.getRanges(),
                    v = u.length,
                    w;
                for (var x = 0; x < v; x++) {
                    t.push(w = u[x].createBookmark(s, true));
                    s = w.serializable;
                    var y = s ? this.document.getById(w.startNode) : w.startNode,
                    z = s ? this.document.getById(w.endNode) : w.endNode;
                    for (var A = x + 1; A < v; A++) {
                        var B = u[A],
                            C = B.startContainer,
                            D = B.endContainer;
                        C.equals(y.getParent()) && B.startOffset++;
                        C.equals(z.getParent()) && B.startOffset++;
                        D.equals(y.getParent()) && B.endOffset++;
                        D.equals(z.getParent()) && B.endOffset++;
                    }
                }
                return t;
            },
            createBookmarks2: function (s) {
                var t = [],
                    u = this.getRanges();
                for (var v = 0; v < u.length; v++) t.push(u[v].createBookmark2(s));
                return t;
            },
            selectBookmarks: function (s) {
                var t = [];
                for (var u = 0; u < s.length; u++) {
                    var v = new d.range(this.document);
                    v.moveToBookmark(s[u]);
                    t.push(v);
                }
                this.selectRanges(t);
                return this;
            }
        };
    })();
    d.range.prototype.select = c ?
    function (l) {
        var u = this;
        var m = u.collapsed,
            n, o, p = u.createBookmark(),
            q = p.startNode,
            r;
        if (!m) r = p.endNode;
        var s = u.document.$.body.createTextRange();
        s.moveToElementText(q.$);
        s.moveStart('character', 1);
        if (r) {
            var t = u.document.$.body.createTextRange();
            t.moveToElementText(r.$);
            s.setEndPoint('EndToEnd', t);
            s.moveEnd('character', -1);
        } else {
            n = l || !q.hasPrevious() || q.getPrevious().is && q.getPrevious().is('br');
            o = u.document.createElement('span');
            o.setHtml('&#65279;');
            o.insertBefore(q);
            if (n) u.document.createText('﻿').insertBefore(q);
        }
        u.setStartBefore(q);
        q.remove();
        if (m) {
            if (n) {
                s.moveStart('character', -1);
                s.select();
                u.document.$.selection.clear();
            } else s.select();
            o.remove();
        } else {
            u.setEndBefore(r);
            r.remove();
            s.select();
        }
    } : function () {
        var o = this;
        var l = o.startContainer;
        if (o.collapsed && l.type == 1 && !l.getChildCount()) l.append(new d.text(''));
        var m = o.document.$.createRange();
        m.setStart(l.$, o.startOffset);
        try {
            m.setEnd(o.endContainer.$, o.endOffset);
        } catch(p) {
            if (p.toString().indexOf('NS_ERROR_ILLEGAL_VALUE') >= 0) {
                o.collapse(true);
                m.setEnd(o.endContainer.$, o.endOffset);
            } else throw p;
        }
        var n = o.document.getSelection().getNative();
        n.removeAllRanges();
        n.addRange(m);
    };
    (function () {
        var l = {
            elements: {
                $: function (m) {
                    var n = m.attributes._cke_realelement,
                        o = n && new a.htmlParser.fragment.fromHtml(decodeURIComponent(n)),
                        p = o && o.children[0];
                    if (p) {
                        var q = m.attributes.style;
                        if (q) {
                            var r = /(?:^|\s)width\s*:\s*(\d+)/.exec(q),
                                s = r && r[1];
                            r = /(?:^|\s)height\s*:\s*(\d+)/.exec(q);
                            var t = r && r[1];
                            if (s) p.attributes.width = s;
                            if (t) p.attributes.height = t;
                        }
                    }
                    return p;
                }
            }
        };
        j.add('fakeobjects', {
            requires: ['htmlwriter'],
            afterInit: function (m) {
                var n = m.dataProcessor,
                    o = n && n.htmlFilter;
                if (o) o.addRules(l);
            }
        });
    })();
    a.editor.prototype.createFakeElement = function (l, m, n, o) {
        var p = this.lang.fakeobjects,
            q = {
            'class': m,
            src: a.getUrl('images/spacer.gif'),
            _cke_realelement: encodeURIComponent(l.getOuterHtml()),
            alt: p[n] || p.unknown
        };
        if (n) q._cke_real_element_type = n;
        if (o) q._cke_resizable = o;
        return this.document.createElement('img', {
            attributes: q
        });
    };
    a.editor.prototype.createFakeParserElement = function (l, m, n, o) {
        var p = new a.htmlParser.basicWriter();
        l.writeHtml(p);
        var q = p.getHtml(),
            r = this.lang.fakeobjects,
            s = {
            'class': m,
            src: a.getUrl('images/spacer.gif'),
            _cke_realelement: encodeURIComponent(q),
            alt: r[n] || r.unknown
        };
        if (n) s._cke_real_element_type = n;
        if (o) s._cke_resizable = o;
        return new a.htmlParser.element('img', s);
    };
    a.editor.prototype.restoreRealElement = function (l) {
        var m = decodeURIComponent(l.getAttribute('_cke_realelement'));
        return h.createFromHtml(m, this.document);
    };
    j.add('richcombo', {
        requires: ['floatpanel', 'listblock', 'button'],
        beforeInit: function (l) {
            l.ui.addHandler(3, k.richCombo.handler);
        }
    });
    a.UI_RICHCOMBO = 3;
    k.richCombo = e.createClass({
        $: function (l) {
            var n = this;
            e.extend(n, l, {
                title: l.label,
                modes: {
                    wysiwyg: 1
                }
            });
            var m = n.panel || {};
            delete n.panel;
            n.id = e.getNextNumber();
            n.document = m && m.parent && m.parent.getDocument() || a.document;
            m.className = (m.className || '') + (' cke_rcombopanel');
            n._ = {
                panelDefinition: m,
                items: {},
                state: 2
            };
        },
        statics: {
            handler: {
                create: function (l) {
                    return new k.richCombo(l);
                }
            }
        },
        proto: {
            renderHtml: function (l) {
                var m = [];
                this.render(l, m);
                return m.join('');
            },
            render: function (l, m) {
                var n = 'cke_' + this.id,
                    o = e.addFunction(function (r) {
                    var u = this;
                    var s = u._;
                    if (s.state == 0) return;
                    u.createPanel(l);
                    if (s.on) {
                        s.panel.hide();
                        return;
                    }
                    if (!s.committed) {
                        s.list.commit();
                        s.committed = 1;
                    }
                    var t = u.getValue();
                    if (t) s.list.mark(t);
                    else s.list.unmarkAll();
                    s.panel.showBlock(u.id, new h(r), 4);
                },
                this),
                    p = {
                    id: n,
                    combo: this,
                    focus: function () {
                        var r = a.document.getById(n).getChild(1);
                        r.focus();
                    },
                    execute: o
                };
                l.on('mode', function () {
                    this.setState(this.modes[l.mode] ? 2 : 0);
                },
                this);
                var q = e.addFunction(function (r, s) {
                    r = new d.event(r);
                    var t = r.getKeystroke();
                    switch (t) {
                    case 13:
                    case 32:
                    case 40:
                        e.callFunction(o, s);
                        break;
                    default:
                        p.onkey(p, t);
                    }
                    r.preventDefault();
                });
                m.push('<span class="cke_rcombo">', '<span id=', n);
                if (this.className) m.push(' class="', this.className, ' cke_off"');
                m.push('><span class=cke_label>', this.label, '</span><a hidefocus=true title="', this.title, '" tabindex="-1" href="javascript:void(\'', this.label, "')\"");
                if (b.opera || b.gecko && b.mac) m.push(' onkeypress="return false;"');
                if (b.gecko) m.push(' onblur="this.style.cssText = this.style.cssText;"');
                m.push(' onkeydown="CKEDITOR.tools.callFunction( ', q, ', event, this );" onclick="CKEDITOR.tools.callFunction(', o, ', this); return false;"><span><span class="cke_accessibility">' + (this.voiceLabel ? this.voiceLabel + ' ' : '') + '</span>' + '<span id="' + n + '_text" class="cke_text cke_inline_label">' + this.label + '</span>' + '</span>' + '<span class=cke_openbutton></span>' + '</a>' + '</span>' + '</span>');
                if (this.onRender) this.onRender();
                return p;
            },
            createPanel: function (l) {
                if (this._.panel) return;
                var m = this._.panelDefinition,
                    n = m.parent || a.document.getBody(),
                    o = new k.floatPanel(l, n, m),
                    p = o.addListBlock(this.id, this.multiSelect),
                    q = this;
                o.onShow = function () {
                    if (q.className) this.element.getFirst().addClass(q.className + '_panel');
                    q.setState(1);
                    p.focus(!q.multiSelect && q.getValue());
                    q._.on = 1;
                    if (q.onOpen) q.onOpen();
                };
                o.onHide = function () {
                    if (q.className) this.element.getFirst().removeClass(q.className + '_panel');
                    q.setState(2);
                    q._.on = 0;
                    if (q.onClose) q.onClose();
                };
                o.onEscape = function () {
                    o.hide();
                    q.document.getById('cke_' + q.id).getFirst().getNext().focus();
                };
                p.onClick = function (r, s) {
                    q.document.getWindow().focus();
                    if (q.onClick) q.onClick.call(q, r, s);
                    if (s) q.setValue(r, q._.items[r]);
                    else q.setValue('');
                    o.hide();
                };
                this._.panel = o;
                this._.list = p;
                o.getBlock(this.id).onHide = function () {
                    q._.on = 0;
                    q.setState(2);
                };
                if (this.init) this.init();
            },
            setValue: function (l, m) {
                var o = this;
                o._.value = l;
                var n = o.document.getById('cke_' + o.id + '_text');
                if (!l) {
                    m = o.label;
                    n.addClass('cke_inline_label');
                } else n.removeClass('cke_inline_label');
                n.setHtml(typeof m != 'undefined' ? m : l);
            },
            getValue: function () {
                return this._.value || '';
            },
            unmarkAll: function () {
                this._.list.unmarkAll();
            },
            mark: function (l) {
                this._.list.mark(l);
            },
            hideItem: function (l) {
                this._.list.hideItem(l);
            },
            hideGroup: function (l) {
                this._.list.hideGroup(l);
            },
            showAll: function () {
                this._.list.showAll();
            },
            add: function (l, m, n) {
                this._.items[l] = n || l;
                this._.list.add(l, m, n);
            },
            startGroup: function (l) {
                this._.list.startGroup(l);
            },
            commit: function () {
                this._.list.commit();
            },
            setState: function (l) {
                var m = this;
                if (m._.state == l) return;
                m.document.getById('cke_' + m.id).setState(l);
                m._.state = l;
            }
        }
    });
    k.prototype.addRichCombo = function (l, m) {
        this.add(l, 3, m);
    };
    j.add('htmlwriter');
    a.htmlWriter = e.createClass({
        base: a.htmlParser.basicWriter,
        $: function () {
            var n = this;
            n.base();
            n.indentationChars = '\t';
            n.selfClosingEnd = ' />';
            n.lineBreakChars = '\n';
            n.forceSimpleAmpersand = false;
            n.sortAttributes = true;
            n._.indent = false;
            n._.indentation = '';
            n._.rules = {};
            var l = f;
            for (var m in e.extend({},
            l.$block, l.$listItem, l.$tableContent)) n.setRules(m, {
                indent: true,
                breakBeforeOpen: true,
                breakAfterOpen: true,
                breakBeforeClose: !l[m]['#'],
                breakAfterClose: true
            });
            n.setRules('br', {
                breakAfterOpen: true
            });
            n.setRules('pre', {
                indent: false
            });
        },
        proto: {
            openTag: function (l, m) {
                var o = this;
                var n = o._.rules[l];
                if (o._.indent) o.indentation();
                else if (n && n.breakBeforeOpen) {
                    o.lineBreak();
                    o.indentation();
                }
                o._.output.push('<', l);
            },
            openTagClose: function (l, m) {
                var o = this;
                var n = o._.rules[l];
                if (m) o._.output.push(o.selfClosingEnd);
                else {
                    o._.output.push('>');
                    if (n && n.indent) o._.indentation += o.indentationChars;
                }
                if (n && n.breakAfterOpen) o.lineBreak();
            },
            attribute: function (l, m) {
                if (this.forceSimpleAmpersand) m = m.replace(/&amp;/, '&');
                this._.output.push(' ', l, '="', m, '"');
            },
            closeTag: function (l) {
                var n = this;
                var m = n._.rules[l];
                if (m && m.indent) n._.indentation = n._.indentation.substr(n.indentationChars.length);
                if (n._.indent) n.indentation();
                else if (m && m.breakBeforeClose) {
                    n.lineBreak();
                    n.indentation();
                }
                n._.output.push('</', l, '>');
                if (m && m.breakAfterClose) n.lineBreak();
            },
            text: function (l) {
                if (this._.indent) {
                    this.indentation();
                    l = e.ltrim(l);
                }
                this._.output.push(l);
            },
            comment: function (l) {
                if (this._.indent) this.indentation();
                this._.output.push('<!--', l, '-->');
            },
            lineBreak: function () {
                var l = this;
                if (l._.output.length > 0) l._.output.push(l.lineBreakChars);
                l._.indent = true;
            },
            indentation: function () {
                this._.output.push(this._.indentation);
                this._.indent = false;
            },
            setRules: function (l, m) {
                this._.rules[l] = m;
            }
        }
    });
    j.add('menubutton', {
        requires: ['button', 'contextmenu'],
        beforeInit: function (l) {
            l.ui.addHandler(5, k.menuButton.handler);
        }
    });
    a.UI_MENUBUTTON = 5;
    (function () {
        var l = function (m) {
            var n = this._;
            if (n.state === 0) return;
            n.previousState = n.state;
            var o = n.menu;
            if (!o) {
                o = n.menu = new j.contextMenu(m);
                o.onHide = e.bind(function () {
                    this.setState(n.previousState);
                },
                this);
                if (this.onMenu) o.addListener(this.onMenu);
            }
            if (n.on) {
                o.hide();
                return;
            }
            this.setState(1);
            o.show(a.document.getById(this._.id), 4);
        };
        k.menuButton = e.createClass({
            base: k.button,
            $: function (m) {
                var n = m.panel;
                delete m.panel;
                this.base(m);
                this.hasArrow = true;
                this.click = l;
            },
            statics: {
                handler: {
                    create: function (m) {
                        return new k.menuButton(m);
                    }
                }
            }
        });
    })();
    j.add('dialog', {
        requires: ['dialogui']
    });
    a.DIALOG_RESIZE_NONE = 0;
    a.DIALOG_RESIZE_WIDTH = 1;
    a.DIALOG_RESIZE_HEIGHT = 2;
    a.DIALOG_RESIZE_BOTH = 3;
    (function () {
        function l(J) {
            return !!this._.tabs[J][0].$.offsetHeight;
        };

        function m() {
            var N = this;
            var J = N._.currentTabId,
                K = N._.tabIdList.length,
                L = e.indexOf(N._.tabIdList, J) + K;
            for (var M = L - 1; M > L - K; M--) if (l.call(N, N._.tabIdList[M % K])) return N._.tabIdList[M % K];
            return null;
        };

        function n() {
            var N = this;
            var J = N._.currentTabId,
                K = N._.tabIdList.length,
                L = e.indexOf(N._.tabIdList, J);
            for (var M = L + 1; M < L + K; M++) if (l.call(N, N._.tabIdList[M % K])) return N._.tabIdList[M % K];
            return null;
        };
        var o = {};
        a.dialog = function (J, K) {
            var L = a.dialog._.dialogDefinitions[K];
            if (!L) {
                console.log('Error: The dialog "' + K + '" is not defined.');
                return;
            }
            L = e.extend(L(J), q);
            L = e.clone(L);
            L = new u(this, L);
            this.definition = L = a.fire('dialogDefinition', {
                name: K,
                definition: L
            },
            J).definition;
            var M = a.document,
                N = J.theme.buildDialog(J);
            this._ = {
                editor: J,
                element: N.element,
                name: K,
                contentSize: {
                    width: 0,
                    height: 0
                },
                size: {
                    width: 0,
                    height: 0
                },
                updateSize: false,
                contents: {},
                buttons: {},
                accessKeyMap: {},
                tabs: {},
                tabIdList: [],
                currentTabId: null,
                currentTabIndex: null,
                pageCount: 0,
                lastTab: null,
                tabBarMode: false,
                focusList: [],
                currentFocusIndex: 0,
                hasFocus: false
            };
            this.parts = N.parts;
            this.parts.dialog.setStyles({
                position: b.ie6Compat ? 'absolute' : 'fixed',
                top: 0,
                left: 0,
                visibility: 'hidden'
            });
            a.event.call(this);
            if (L.onLoad) this.on('load', L.onLoad);
            if (L.onShow) this.on('show', L.onShow);
            if (L.onHide) this.on('hide', L.onHide);
            if (L.onOk) this.on('ok', function (X) {
                if (L.onOk.call(this, X) === false) X.data.hide = false;
            });
            if (L.onCancel) this.on('cancel', function (X) {
                if (L.onCancel.call(this, X) === false) X.data.hide = false;
            });
            var O = this,
                P = function (X) {
                var Y = O._.contents,
                    Z = false;
                for (var aa in Y) for (var ab in Y[aa]) {
                    Z = X.call(this, Y[aa][ab]);
                    if (Z) return;
                }
            };
            this.on('ok', function (X) {
                P(function (Y) {
                    if (Y.validate) {
                        var Z = Y.validate(this);
                        if (typeof Z == 'string') {
                            alert(Z);
                            Z = false;
                        }
                        if (Z === false) {
                            if (Y.select) Y.select();
                            else Y.focus();
                            X.data.hide = false;
                            X.stop();
                            return true;
                        }
                    }
                });
            },
            this, null, 0);
            this.on('cancel', function (X) {
                P(function (Y) {
                    if (Y.isChanged()) {
                        if (!confirm(J.lang.common.confirmCancel)) X.data.hide = false;
                        return true;
                    }
                });
            },
            this, null, 0);
            this.parts.close.on('click', function (X) {
                if (this.fire('cancel', {
                    hide: true
                }).hide !== false) this.hide();
            },
            this);

            function Q(X) {
                var Y = O._.focusList,
                    Z = X ? 1 : -1;
                if (Y.length < 1) return;
                var aa = (O._.currentFocusIndex + Z + Y.length) % (Y.length);
                while (!Y[aa].isFocusable()) {
                    aa = (aa + Z + Y.length) % (Y.length);
                    if (aa == O._.currentFocusIndex) break;
                }
                Y[aa].focus();
            };

            function R(X) {
                if (O != a.dialog._.currentTop) return;
                var Y = X.data.getKeystroke(),
                    Z = false;
                if (Y == 9 || Y == 2000 + 9) {
                    var aa = Y == 2000 + 9;
                    if (O._.tabBarMode) {
                        var ab = aa ? m.call(O) : n.call(O);
                        O.selectPage(ab);
                        O._.tabs[ab][0].focus();
                    } else Q(!aa);
                    Z = true;
                } else if (Y == 4000 + 121 && !O._.tabBarMode) {
                    O._.tabBarMode = true;
                    O._.tabs[O._.currentTabId][0].focus();
                    Z = true;
                } else if ((Y == 37 || Y == 39) && (O._.tabBarMode)) {
                    ab = Y == 37 ? m.call(O) : n.call(O);
                    O.selectPage(ab);
                    O._.tabs[ab][0].focus();
                    Z = true;
                }
                if (Z) {
                    X.stop();
                    X.data.preventDefault();
                }
            };
            this.on('show', function () {
                a.document.on('keydown', R, this, null, 0);
                if (b.ie6Compat) {
                    var X = z.getChild(0).getFrameDocument();
                    X.on('keydown', R, this, null, 0);
                }
            });
            this.on('hide', function () {
                a.document.removeListener('keydown', R);
            });
            this.on('iframeAdded', function (X) {
                var Y = new g(X.data.iframe.$.contentWindow.document);
                Y.on('keydown', R, this, null, 0);
            });
            this.on('show', function () {
                var aa = this;
                if (!aa._.hasFocus) {
                    aa._.currentFocusIndex = -1;
                    Q(true);
                    if (aa._.editor.mode == 'wysiwyg' && c) {
                        var X = J.document.$.selection,
                            Y = X.createRange();
                        if (Y) if (Y.parentElement && Y.parentElement().ownerDocument == J.document.$ || Y.item && Y.item(0).ownerDocument == J.document.$) {
                            var Z = document.body.createTextRange();
                            Z.moveToElementText(aa.getElement().getFirst().$);
                            Z.collapse(true);
                            Z.select();
                        }
                    }
                }
            },
            this, null, 4294967295);
            if (b.ie6Compat) this.on('load', function (X) {
                var Y = this.getElement(),
                    Z = Y.getFirst();
                Z.remove();
                Z.appendTo(Y);
            },
            this);
            w(this);
            x(this);
            new d.text(L.title, a.document).appendTo(this.parts.title);
            for (var S = 0; S < L.contents.length; S++) this.addPage(L.contents[S]);
            var T = /cke_dialog_tab(\s|$|_)/,
                U = /cke_dialog_tab(\s|$)/;
            this.parts.tabs.on('click', function (X) {
                var ac = this;
                var Y = X.data.getTarget(),
                    Z = Y,
                    aa, ab;
                if (! (T.test(Y.$.className) || Y.getName() == 'a')) return;
                aa = Y.$.id.substr(0, Y.$.id.lastIndexOf('_'));
                ac.selectPage(aa);
                if (ac._.tabBarMode) {
                    ac._.tabBarMode = false;
                    ac._.currentFocusIndex = -1;
                    Q(true);
                }
                X.data.preventDefault();
            },
            this);
            var V = [],
                W = a.dialog._.uiElementBuilders.hbox.build(this, {
                type: 'hbox',
                className: 'cke_dialog_footer_buttons',
                widths: [],
                children: L.buttons
            },
            V).getChild();
            this.parts.footer.setHtml(V.join(''));
            for (S = 0; S < W.length; S++) this._.buttons[W[S].id] = W[S];
            a.skins.load(J, 'dialog');
        };

        function p(J, K, L) {
            this.element = K;
            this.focusIndex = L;
            this.isFocusable = function () {
                return true;
            };
            this.focus = function () {
                J._.currentFocusIndex = this.focusIndex;
                this.element.focus();
            };
            K.on('keydown', function (M) {
                if (M.data.getKeystroke() in {
                    32: 1,
                    13: 1
                }) this.fire('click');
            });
            K.on('focus', function () {
                this.fire('mouseover');
            });
            K.on('blur', function () {
                this.fire('mouseout');
            });
        };
        a.dialog.prototype = {
            resize: (function () {
                return function (J, K) {
                    var L = this;
                    if (L._.contentSize && L._.contentSize.width == J && L._.contentSize.height == K) return;
                    a.dialog.fire('resize', {
                        dialog: L,
                        skin: L._.editor.skinName,
                        width: J,
                        height: K
                    },
                    L._.editor);
                    L._.contentSize = {
                        width: J,
                        height: K
                    };
                    L._.updateSize = true;
                };
            })(),
            getSize: function () {
                var L = this;
                if (!L._.updateSize) return L._.size;
                var J = L._.element.getFirst(),
                    K = L._.size = {
                    width: J.$.offsetWidth || 0,
                    height: J.$.offsetHeight || 0
                };
                L._.updateSize = !K.width || !K.height;
                return K;
            },
            move: (function () {
                var J;
                return function (K, L) {
                    var O = this;
                    var M = O._.element.getFirst();
                    if (J === undefined) J = M.getComputedStyle('position') == 'fixed';
                    if (J && O._.position && O._.position.x == K && O._.position.y == L) return;
                    O._.position = {
                        x: K,
                        y: L
                    };
                    if (!J) {
                        var N = a.document.getWindow().getScrollPosition();
                        K += N.x;
                        L += N.y;
                    }
                    M.setStyles({
                        left: (K > 0 ? K : 0) + ('px'),
                        top: (L > 0 ? L : 0) + ('px')
                    });
                };
            })(),
            getPosition: function () {
                return e.extend({},
                this._.position);
            },
            show: function () {
                if (this._.editor.mode == 'wysiwyg' && c) this._.editor.getSelection().lock();
                var J = this._.element,
                    K = this.definition;
                if (! (J.getParent() && J.getParent().equals(a.document.getBody()))) J.appendTo(a.document.getBody());
                else return;
                if (b.gecko && b.version < 10900) {
                    var L = this.parts.dialog;
                    L.setStyle('position', 'absolute');
                    setTimeout(function () {
                        L.setStyle('position', 'fixed');
                    },
                    0);
                }
                this.resize(K.minWidth, K.minHeight);
                this.selectPage(this.definition.contents[0].id);
                this.reset();
                if (a.dialog._.currentZIndex === null) a.dialog._.currentZIndex = this._.editor.config.baseFloatZIndex;
                this._.element.getFirst().setStyle('z-index', a.dialog._.currentZIndex += 10);
                if (a.dialog._.currentTop === null) {
                    a.dialog._.currentTop = this;
                    this._.parentDialog = null;
                    A(this._.editor);
                    a.document.on('keydown', D);
                    a.document.on('keyup', E);
                } else {
                    this._.parentDialog = a.dialog._.currentTop;
                    var M = this._.parentDialog.getElement().getFirst();
                    M.$.style.zIndex -= Math.floor(this._.editor.config.baseFloatZIndex / 2);
                    a.dialog._.currentTop = this;
                }
                F(this, this, '\x1b', null, function () {
                    this.getButton('cancel') && this.getButton('cancel').click();
                });
                this._.hasFocus = false;
                e.setTimeout(function () {
                    var N = a.document.getWindow().getViewPaneSize(),
                        O = this.getSize();
                    this.move((N.width - K.minWidth) / (2), (N.height - O.height) / (2));
                    this.parts.dialog.setStyle('visibility', '');
                    this.fireOnce('load', {});
                    this.fire('show', {});
                    this.foreach(function (P) {
                        P.setInitValue && P.setInitValue();
                    });
                },
                100, this);
            },
            foreach: function (J) {
                var M = this;
                for (var K in M._.contents) for (var L in M._.contents[K]) J(M._.contents[K][L]);
                return M;
            },
            reset: (function () {
                var J = function (K) {
                    if (K.reset) K.reset();
                };
                return function () {
                    this.foreach(J);
                    return this;
                };
            })(),
            setupContent: function () {
                var J = arguments;
                this.foreach(function (K) {
                    if (K.setup) K.setup.apply(K, J);
                });
            },
            commitContent: function () {
                var J = arguments;
                this.foreach(function (K) {
                    if (K.commit) K.commit.apply(K, J);
                });
            },
            hide: function () {
                this.fire('hide', {});
                var J = this._.element;
                if (!J.getParent()) return;
                J.remove();
                this.parts.dialog.setStyle('visibility', 'hidden');
                G(this);
                if (!this._.parentDialog) B();
                else {
                    var K = this._.parentDialog.getElement().getFirst();
                    K.setStyle('z-index', parseInt(K.$.style.zIndex, 10) + Math.floor(this._.editor.config.baseFloatZIndex / 2));
                }
                a.dialog._.currentTop = this._.parentDialog;
                if (!this._.parentDialog) {
                    a.dialog._.currentZIndex = null;
                    a.document.removeListener('keydown', D);
                    a.document.removeListener('keyup', E);
                    var L = this._.editor;
                    L.focus();
                    if (L.mode == 'wysiwyg' && c) L.getSelection().unlock(true);
                } else a.dialog._.currentZIndex -= 10;
                this.foreach(function (M) {
                    M.resetInitValue && M.resetInitValue();
                });
            },
            addPage: function (J) {
                var T = this;
                var K = [],
                    L = J.label ? ' title="' + e.htmlEncode(J.label) + '"' : '',
                M = J.elements,
                N = a.dialog._.uiElementBuilders.vbox.build(T, {
                    type: 'vbox',
                    className: 'cke_dialog_page_contents',
                    children: J.elements,
                    expand: !!J.expand,
                    padding: J.padding,
                    style: J.style || 'width: 100%; height: 100%;'
                },
                K),
                O = h.createFromHtml(K.join('')),
                P = h.createFromHtml(['<a class="cke_dialog_tab"', T._.pageCount > 0 ? ' cke_last' : 'cke_first', L, !!J.hidden ? ' style="display:none"' : '', ' id="', J.id + '_', e.getNextNumber(), '" href="javascript:void(0)"', ' hidefocus="true">', J.label, '</a>'].join(''));
                if (T._.pageCount === 0) T.parts.dialog.addClass('cke_single_page');
                else T.parts.dialog.removeClass('cke_single_page');
                T._.tabs[J.id] = [P, O];
                T._.tabIdList.push(J.id);
                T._.pageCount++;
                T._.lastTab = P;
                var Q = T._.contents[J.id] = {},
                    R, S = N.getChild();
                while (R = S.shift()) {
                    Q[R.id] = R;
                    if (typeof R.getChild == 'function') S.push.apply(S, R.getChild());
                }
                O.setAttribute('name', J.id);
                O.appendTo(T.parts.contents);
                P.unselectable();
                T.parts.tabs.append(P);
                if (J.accessKey) {
                    F(T, T, 'CTRL+' + J.accessKey, I, H);
                    T._.accessKeyMap['CTRL+' + J.accessKey] = J.id;
                }
            },
            selectPage: function (J) {
                var O = this;
                for (var K in O._.tabs) {
                    var L = O._.tabs[K][0],
                        M = O._.tabs[K][1];
                    if (K != J) {
                        L.removeClass('cke_dialog_tab_selected');
                        M.hide();
                    }
                }
                var N = O._.tabs[J];
                N[0].addClass('cke_dialog_tab_selected');
                N[1].show();
                O._.currentTabId = J;
                O._.currentTabIndex = e.indexOf(O._.tabIdList, J);
            },
            hidePage: function (J) {
                var K = this._.tabs[J] && this._.tabs[J][0];
                if (!K) return;
                K.hide();
            },
            showPage: function (J) {
                var K = this._.tabs[J] && this._.tabs[J][0];
                if (!K) return;
                K.show();
            },
            getElement: function () {
                return this._.element;
            },
            getName: function () {
                return this._.name;
            },
            getContentElement: function (J, K) {
                return this._.contents[J][K];
            },
            getValueOf: function (J, K) {
                return this.getContentElement(J, K).getValue();
            },
            setValueOf: function (J, K, L) {
                return this.getContentElement(J, K).setValue(L);
            },
            getButton: function (J) {
                return this._.buttons[J];
            },
            click: function (J) {
                return this._.buttons[J].click();
            },
            disableButton: function (J) {
                return this._.buttons[J].disable();
            },
            enableButton: function (J) {
                return this._.buttons[J].enable();
            },
            getPageCount: function () {
                return this._.pageCount;
            },
            getParentEditor: function () {
                return this._.editor;
            },
            getSelectedElement: function () {
                return this.getParentEditor().getSelection().getSelectedElement();
            },
            addFocusable: function (J, K) {
                var M = this;
                if (typeof K == 'undefined') {
                    K = M._.focusList.length;
                    M._.focusList.push(new p(M, J, K));
                } else {
                    M._.focusList.splice(K, 0, new p(M, J, K));
                    for (var L = K + 1; L < M._.focusList.length; L++) M._.focusList[L].focusIndex++;
                }
            }
        };
        e.extend(a.dialog, {
            add: function (J, K) {
                if (!this._.dialogDefinitions[J] || typeof K == 'function') this._.dialogDefinitions[J] = K;
            },
            exists: function (J) {
                return !!this._.dialogDefinitions[J];
            },
            getCurrent: function () {
                return a.dialog._.currentTop;
            },
            okButton: (function () {
                var J = function (K, L) {
                    L = L || {};
                    return e.extend({
                        id: 'ok',
                        type: 'button',
                        label: K.lang.common.ok,
                        'class': 'cke_dialog_ui_button_ok',
                        onClick: function (M) {
                            var N = M.data.dialog;
                            if (N.fire('ok', {
                                hide: true
                            }).hide !== false) N.hide();
                        }
                    },
                    L, true);
                };
                J.type = 'button';
                J.override = function (K) {
                    return e.extend(function (L) {
                        return J(L, K);
                    },
                    {
                        type: 'button'
                    },
                    true);
                };
                return J;
            })(),
            cancelButton: (function () {
                var J = function (K, L) {
                    L = L || {};
                    return e.extend({
                        id: 'cancel',
                        type: 'button',
                        label: K.lang.common.cancel,
                        'class': 'cke_dialog_ui_button_cancel',
                        onClick: function (M) {
                            var N = M.data.dialog;
                            if (N.fire('cancel', {
                                hide: true
                            }).hide !== false) N.hide();
                        }
                    },
                    L, true);
                };
                J.type = 'button';
                J.override = function (K) {
                    return e.extend(function (L) {
                        return J(L, K);
                    },
                    {
                        type: 'button'
                    },
                    true);
                };
                return J;
            })(),
            addUIElement: function (J, K) {
                this._.uiElementBuilders[J] = K;
            }
        });
        a.dialog._ = {
            uiElementBuilders: {},
            dialogDefinitions: {},
            currentTop: null,
            currentZIndex: null
        };
        a.event.implementOn(a.dialog);
        a.event.implementOn(a.dialog.prototype, true);
        var q = {
            resizable: 0,
            minWidth: 600,
            minHeight: 400,
            buttons: [a.dialog.okButton, a.dialog.cancelButton]
        },
            r = function (J, K, L) {
            for (var M = 0, N; N = J[M]; M++) {
                if (N.id == K) return N;
                if (L && N[L]) {
                    var O = r(N[L], K, L);
                    if (O) return O;
                }
            }
            return null;
        },
            s = function (J, K, L, M, N) {
            if (L) {
                for (var O = 0, P; P = J[O]; O++) {
                    if (P.id == L) {
                        J.splice(O, 0, K);
                        return K;
                    }
                    if (M && P[M]) {
                        var Q = s(P[M], K, L, M, true);
                        if (Q) return Q;
                    }
                }
                if (N) return null;
            }
            J.push(K);
            return K;
        },
            t = function (J, K, L) {
            for (var M = 0, N; N = J[M]; M++) {
                if (N.id == K) return J.splice(M, 1);
                if (L && N[L]) {
                    var O = t(N[L], K, L);
                    if (O) return O;
                }
            }
            return null;
        },
            u = function (J, K) {
            this.dialog = J;
            var L = K.contents;
            for (var M = 0, N; N = L[M]; M++) L[M] = new v(J, N);
            e.extend(this, K);
        };
        u.prototype = {
            getContents: function (J) {
                return r(this.contents, J);
            },
            getButton: function (J) {
                return r(this.buttons, J);
            },
            addContents: function (J, K) {
                return s(this.contents, J, K);
            },
            addButton: function (J, K) {
                return s(this.buttons, J, K);
            },
            removeContents: function (J) {
                t(this.contents, J);
            },
            removeButton: function (J) {
                t(this.buttons, J);
            }
        };

        function v(J, K) {
            this._ = {
                dialog: J
            };
            e.extend(this, K);
        };
        v.prototype = {
            get: function (J) {
                return r(this.elements, J, 'children');
            },
            add: function (J, K) {
                return s(this.elements, J, K, 'children');
            },
            remove: function (J) {
                t(this.elements, J, 'children');
            }
        };

        function w(J) {
            var K = null,
                L = null,
                M = J.getElement().getFirst(),
                N = J.getParentEditor(),
                O = N.config.dialog_magnetDistance,
                P = o[N.skinName].margins || [0, 0, 0, 0];

            function Q(S) {
                var T = J.getSize(),
                    U = a.document.getWindow().getViewPaneSize(),
                    V = S.data.$.screenX,
                    W = S.data.$.screenY,
                    X = V - K.x,
                    Y = W - K.y,
                    Z, aa;
                K = {
                    x: V,
                    y: W
                };
                L.x += X;
                L.y += Y;
                if (L.x + P[3] < O) Z = -P[3];
                else if (L.x - P[1] > U.width - T.width - O) Z = U.width - T.width + P[1];
                else Z = L.x;
                if (L.y + P[0] < O) aa = -P[0];
                else if (L.y - P[2] > U.height - T.height - O) aa = U.height - T.height + P[2];
                else aa = L.y;
                J.move(Z, aa);
                S.data.preventDefault();
            };

            function R(S) {
                a.document.removeListener('mousemove', Q);
                a.document.removeListener('mouseup', R);
                if (b.ie6Compat) {
                    var T = z.getChild(0).getFrameDocument();
                    T.removeListener('mousemove', Q);
                    T.removeListener('mouseup', R);
                }
            };
            J.parts.title.on('mousedown', function (S) {
                J._.updateSize = true;
                K = {
                    x: S.data.$.screenX,
                    y: S.data.$.screenY
                };
                a.document.on('mousemove', Q);
                a.document.on('mouseup', R);
                L = J.getPosition();
                if (b.ie6Compat) {
                    var T = z.getChild(0).getFrameDocument();
                    T.on('mousemove', Q);
                    T.on('mouseup', R);
                }
                S.data.preventDefault();
            },
            J);
        };

        function x(J) {
            var K = J.definition,
                L = K.minWidth || 0,
                M = K.minHeight || 0,
                N = K.resizable,
                O = o[J.getParentEditor().skinName].margins || [0, 0, 0, 0];

            function P(aa, ab) {
                aa.y += ab;
            };

            function Q(aa, ab) {
                aa.x2 += ab;
            };

            function R(aa, ab) {
                aa.y2 += ab;
            };

            function S(aa, ab) {
                aa.x += ab;
            };
            var T = null,
                U = null,
                V = J._.editor.config.magnetDistance,
                W = ['tl', 't', 'tr', 'l', 'r', 'bl', 'b', 'br'];

            function X(aa) {
                var ab = aa.listenerData.part,
                    ac = J.getSize();
                U = J.getPosition();
                e.extend(U, {
                    x2: U.x + ac.width,
                    y2: U.y + ac.height
                });
                T = {
                    x: aa.data.$.screenX,
                    y: aa.data.$.screenY
                };
                a.document.on('mousemove', Y, J, {
                    part: ab
                });
                a.document.on('mouseup', Z, J, {
                    part: ab
                });
                if (b.ie6Compat) {
                    var ad = z.getChild(0).getFrameDocument();
                    ad.on('mousemove', Y, J, {
                        part: ab
                    });
                    ad.on('mouseup', Z, J, {
                        part: ab
                    });
                }
                aa.data.preventDefault();
            };

            function Y(aa) {
                var ab = aa.data.$.screenX,
                    ac = aa.data.$.screenY,
                    ad = ab - T.x,
                    ae = ac - T.y,
                    af = a.document.getWindow().getViewPaneSize(),
                    ag = aa.listenerData.part;
                if (ag.search('t') != -1) P(U, ae);
                if (ag.search('l') != -1) S(U, ad);
                if (ag.search('b') != -1) R(U, ae);
                if (ag.search('r') != -1) Q(U, ad);
                T = {
                    x: ab,
                    y: ac
                };
                var ah, ai, aj, ak;
                if (U.x + O[3] < V) ah = -O[3];
                else if (ag.search('l') != -1 && U.x2 - U.x < L + V) ah = U.x2 - L;
                else ah = U.x;
                if (U.y + O[0] < V) ai = -O[0];
                else if (ag.search('t') != -1 && U.y2 - U.y < M + V) ai = U.y2 - M;
                else ai = U.y;
                if (U.x2 - O[1] > af.width - V) aj = af.width + O[1];
                else if (ag.search('r') != -1 && U.x2 - U.x < L + V) aj = U.x + L;
                else aj = U.x2;
                if (U.y2 - O[2] > af.height - V) ak = af.height + O[2];
                else if (ag.search('b') != -1 && U.y2 - U.y < M + V) ak = U.y + M;
                else ak = U.y2;
                J.move(ah, ai);
                J.resize(aj - ah, ak - ai);
                aa.data.preventDefault();
            };

            function Z(aa) {
                a.document.removeListener('mouseup', Z);
                a.document.removeListener('mousemove', Y);
                if (b.ie6Compat) {
                    var ab = z.getChild(0).getFrameDocument();
                    ab.removeListener('mouseup', Z);
                    ab.removeListener('mousemove', Y);
                }
            };
        };
        var y, z, A = function (J) {
            var K = a.document.getWindow();
            if (!z) {
                var L = ['<div style="position: ', b.ie6Compat ? 'absolute' : 'fixed', '; z-index: ', J.config.baseFloatZIndex, '; top: 0px; left: 0px; ', 'background-color: ', J.config.dialog_backgroundCoverColor, '" id="cke_dialog_background_cover">'];
                if (b.ie6Compat) {
                    var M = b.isCustomDomain();
                    L.push('<iframe hidefocus="true" frameborder="0" id="cke_dialog_background_iframe" src="javascript:');
                    L.push(M ? "void((function(){document.open();document.domain='" + document.domain + "';" + 'document.close();' + '})())' : "''");
                    L.push('" style="position:absolute;left:0;top:0;width:100%;height: 100%;progid:DXImageTransform.Microsoft.Alpha(opacity=0)"></iframe>');
                }
                L.push('</div>');
                z = h.createFromHtml(L.join(''));
            }
            var N = z,
                O = function () {
                var R = K.getViewPaneSize();
                N.setStyles({
                    width: R.width + 'px',
                    height: R.height + 'px'
                });
            },
                P = function () {
                var R = K.getScrollPosition(),
                    S = a.dialog._.currentTop;
                N.setStyles({
                    left: R.x + 'px',
                    top: R.y + 'px'
                });
                do {
                    var T = S.getPosition();
                    S.move(T.x, T.y);
                } while (S = S._.parentDialog)
            };
            y = O;
            K.on('resize', O);
            O();
            if (b.ie6Compat) {
                var Q = function () {
                    P();
                    arguments.callee.prevScrollHandler.apply(this, arguments);
                };
                K.$.setTimeout(function () {
                    Q.prevScrollHandler = window.onscroll || (function () {});
                    window.onscroll = Q;
                },
                0);
                P();
            }
            N.setOpacity(J.config.dialog_backgroundCoverOpacity);
            N.appendTo(a.document.getBody());
        },
            B = function () {
            if (!z) return;
            var J = a.document.getWindow();
            z.remove();
            J.removeListener('resize', y);
            if (b.ie6Compat) J.$.setTimeout(function () {
                var K = window.onscroll && window.onscroll.prevScrollHandler;
                window.onscroll = K || null;
            },
            0);
            y = null;
        },
            C = {},
            D = function (J) {
            var K = J.data.$.ctrlKey || J.data.$.metaKey,
                L = J.data.$.altKey,
                M = J.data.$.shiftKey,
                N = String.fromCharCode(J.data.$.keyCode),
                O = C[(K ? 'CTRL+' : '') + (L ? 'ALT+' : '') + (M ? 'SHIFT+' : '') + N];
            if (!O || !O.length) return;
            O = O[O.length - 1];
            O.keydown && O.keydown.call(O.uiElement, O.dialog, O.key);
            J.data.preventDefault();
        },
            E = function (J) {
            var K = J.data.$.ctrlKey || J.data.$.metaKey,
                L = J.data.$.altKey,
                M = J.data.$.shiftKey,
                N = String.fromCharCode(J.data.$.keyCode),
                O = C[(K ? 'CTRL+' : '') + (L ? 'ALT+' : '') + (M ? 'SHIFT+' : '') + N];
            if (!O || !O.length) return;
            O = O[O.length - 1];
            O.keyup && O.keyup.call(O.uiElement, O.dialog, O.key);
            J.data.preventDefault();
        },
            F = function (J, K, L, M, N) {
            var O = C[L] || (C[L] = []);
            O.push({
                uiElement: J,
                dialog: K,
                key: L,
                keyup: N || J.accessKeyUp,
                keydown: M || J.accessKeyDown
            });
        },
            G = function (J) {
            for (var K in C) {
                var L = C[K];
                for (var M = L.length - 1; M >= 0; M--) if (L[M].dialog == J || L[M].uiElement == J) L.splice(M, 1);
                if (L.length === 0) delete C[K];
            }
        },
            H = function (J, K) {
            if (J._.accessKeyMap[K]) J.selectPage(J._.accessKeyMap[K]);
        },
            I = function (J, K) {};
        (function () {
            k.dialog = {
                uiElement: function (J, K, L, M, N, O, P) {
                    if (arguments.length < 4) return;
                    var Q = (M.call ? M(K) : M) || ('div'),
                        R = ['<', Q, ' '],
                        S = (N && N.call ? N(K) : N) || ({}),
                        T = (O && O.call ? O(K) : O) || ({}),
                        U = (P && P.call ? P(J, K) : P) || (''),
                        V = this.domId = T.id || e.getNextNumber() + '_uiElement',
                        W = this.id = K.id,
                        X;
                    T.id = V;
                    var Y = {};
                    if (K.type) Y['cke_dialog_ui_' + K.type] = 1;
                    if (K.className) Y[K.className] = 1;
                    var Z = T['class'] && T['class'].split ? T['class'].split(' ') : [];
                    for (X = 0; X < Z.length; X++) if (Z[X]) Y[Z[X]] = 1;
                    var aa = [];
                    for (X in Y) aa.push(X);
                    T['class'] = aa.join(' ');
                    if (K.title) T.title = K.title;
                    var ab = (K.style || '').split(';');
                    for (X in S) ab.push(X + ':' + S[X]);
                    if (K.hidden) ab.push('display:none');
                    for (X = ab.length - 1; X >= 0; X--) if (ab[X] === '') ab.splice(X, 1);
                    if (ab.length > 0) T.style = (T.style ? T.style + '; ' : '') + (ab.join('; '));
                    for (X in T) R.push(X + '="' + e.htmlEncode(T[X]) + '" ');
                    R.push('>', U, '</', Q, '>');
                    L.push(R.join(''));
                    (this._ || (this._ = {})).dialog = J;
                    if (typeof K.isChanged == 'boolean') this.isChanged = function () {
                        return K.isChanged;
                    };
                    if (typeof K.isChanged == 'function') this.isChanged = K.isChanged;
                    a.event.implementOn(this);
                    this.registerEvents(K);
                    if (this.accessKeyUp && this.accessKeyDown && K.accessKey) F(this, J, 'CTRL+' + K.accessKey);
                    var ac = this;
                    J.on('load', function () {
                        if (ac.getInputElement()) ac.getInputElement().on('focus', function () {
                            J._.tabBarMode = false;
                            J._.hasFocus = true;
                            ac.fire('focus');
                        },
                        ac);
                    });
                    if (this.keyboardFocusable) {
                        this.focusIndex = J._.focusList.push(this) - 1;
                        this.on('focus', function () {
                            J._.currentFocusIndex = ac.focusIndex;
                        });
                    }
                    e.extend(this, K);
                },
                hbox: function (J, K, L, M, N) {
                    if (arguments.length < 4) return;
                    this._ || (this._ = {});
                    var O = this._.children = K,
                        P = N && N.widths || null,
                        Q = N && N.height || null,
                        R = {},
                        S, T = function () {
                        var U = ['<tbody><tr class="cke_dialog_ui_hbox">'];
                        for (S = 0; S < L.length; S++) {
                            var V = 'cke_dialog_ui_hbox_child',
                                W = [];
                            if (S === 0) V = 'cke_dialog_ui_hbox_first';
                            if (S == L.length - 1) V = 'cke_dialog_ui_hbox_last';
                            U.push('<td class="', V, '" ');
                            if (P) {
                                if (P[S]) W.push('width:' + e.cssLength(P[S]));
                            } else W.push('width:' + Math.floor(100 / L.length) + '%');
                            if (Q) W.push('height:' + e.cssLength(Q));
                            if (N && N.padding != undefined) W.push('padding:' + e.cssLength(N.padding));
                            if (W.length > 0) U.push('style="' + W.join('; ') + '" ');
                            U.push('>', L[S], '</td>');
                        }
                        U.push('</tr></tbody>');
                        return U.join('');
                    };
                    k.dialog.uiElement.call(this, J, N || {
                        type: 'hbox'
                    },
                    M, 'table', R, N && N.align && {
                        align: N.align
                    } || null, T);
                },
                vbox: function (J, K, L, M, N) {
                    if (arguments.length < 3) return;
                    this._ || (this._ = {});
                    var O = this._.children = K,
                        P = N && N.width || null,
                        Q = N && N.heights || null,
                        R = function () {
                        var S = ['<table cellspacing="0" border="0" '];
                        S.push('style="');
                        if (N && N.expand) S.push('height:100%;');
                        S.push('width:' + e.cssLength(P || '100%'), ';');
                        S.push('"');
                        S.push('align="', e.htmlEncode(N && N.align || (J.getParentEditor().lang.dir == 'ltr' ? 'left' : 'right')), '" ');
                        S.push('><tbody>');
                        for (var T = 0; T < L.length; T++) {
                            var U = [];
                            S.push('<tr><td ');
                            if (P) U.push('width:' + e.cssLength(P || '100%'));
                            if (Q) U.push('height:' + e.cssLength(Q[T]));
                            else if (N && N.expand) U.push('height:' + Math.floor(100 / L.length) + '%');
                            if (N && N.padding != undefined) U.push('padding:' + e.cssLength(N.padding));
                            if (U.length > 0) S.push('style="', U.join('; '), '" ');
                            S.push(' class="cke_dialog_ui_vbox_child">', L[T], '</td></tr>');
                        }
                        S.push('</tbody></table>');
                        return S.join('');
                    };
                    k.dialog.uiElement.call(this, J, N || {
                        type: 'vbox'
                    },
                    M, 'div', null, null, R);
                }
            };
        })();
        k.dialog.uiElement.prototype = {
            getElement: function () {
                return a.document.getById(this.domId);
            },
            getInputElement: function () {
                return this.getElement();
            },
            getDialog: function () {
                return this._.dialog;
            },
            setValue: function (J) {
                this.getInputElement().setValue(J);
                this.fire('change', {
                    value: J
                });
                return this;
            },
            getValue: function () {
                return this.getInputElement().getValue();
            },
            isChanged: function () {
                return false;
            },
            selectParentTab: function () {
                var M = this;
                var J = M.getInputElement(),
                    K = J,
                    L;
                while ((K = K.getParent()) && (K.$.className.search('cke_dialog_page_contents') == -1)) {}
                if (!K) return M;
                L = K.getAttribute('name');
                if (M._.dialog._.currentTabId != L) M._.dialog.selectPage(L);
                return M;
            },
            focus: function () {
                this.selectParentTab().getInputElement().focus();
                return this;
            },
            registerEvents: function (J) {
                var K = /^on([A-Z]\w+)/,
                    L, M = function (O, P, Q, R) {
                    P.on('load', function () {
                        O.getInputElement().on(Q, R, O);
                    });
                };
                for (var N in J) {
                    if (! (L = N.match(K))) continue;
                    if (this.eventProcessors[N]) this.eventProcessors[N].call(this, this._.dialog, J[N]);
                    else M(this, this._.dialog, L[1].toLowerCase(), J[N]);
                }
                return this;
            },
            eventProcessors: {
                onLoad: function (J, K) {
                    J.on('load', K, this);
                },
                onShow: function (J, K) {
                    J.on('show', K, this);
                },
                onHide: function (J, K) {
                    J.on('hide', K, this);
                }
            },
            accessKeyDown: function (J, K) {
                this.focus();
            },
            accessKeyUp: function (J, K) {},
            disable: function () {
                var J = this.getInputElement();
                J.setAttribute('disabled', 'true');
                J.addClass('cke_disabled');
            },
            enable: function () {
                var J = this.getInputElement();
                J.removeAttribute('disabled');
                J.removeClass('cke_disabled');
            },
            isEnabled: function () {
                return !this.getInputElement().getAttribute('disabled');
            },
            isVisible: function () {
                return !!this.getInputElement().$.offsetHeight;
            },
            isFocusable: function () {
                if (!this.isEnabled() || !this.isVisible()) return false;
                return true;
            }
        };
        k.dialog.hbox.prototype = e.extend(new k.dialog.uiElement(), {
            getChild: function (J) {
                var K = this;
                if (arguments.length < 1) return K._.children.concat();
                if (!J.splice) J = [J];
                if (J.length < 2) return K._.children[J[0]];
                else return K._.children[J[0]] && K._.children[J[0]].getChild ? K._.children[J[0]].getChild(J.slice(1, J.length)) : null;
            }
        },
        true);
        k.dialog.vbox.prototype = new k.dialog.hbox();
        (function () {
            var J = {
                build: function (K, L, M) {
                    var N = L.children,
                        O, P = [],
                        Q = [];
                    for (var R = 0; R < N.length && (O = N[R]); R++) {
                        var S = [];
                        P.push(S);
                        Q.push(a.dialog._.uiElementBuilders[O.type].build(K, O, S));
                    }
                    return new k.dialog[L.type](K, Q, P, M, L);
                }
            };
            a.dialog.addUIElement('hbox', J);
            a.dialog.addUIElement('vbox', J);
        })();
        a.dialogCommand = function (J) {
            this.dialogName = J;
        };
        a.dialogCommand.prototype = {
            exec: function (J) {
                J.openDialog(this.dialogName);
            },
            canUndo: false
        };
        (function () {
            var J = /^([a]|[^a])+$/,
                K = /^\d*$/,
                L = /^\d*(?:\.\d+)?$/;
            a.VALIDATE_OR = 1;
            a.VALIDATE_AND = 2;
            a.dialog.validate = {
                functions: function () {
                    return function () {
                        var S = this;
                        var M = S && S.getValue ? S.getValue() : arguments[0],
                        N = undefined,
                        O = 2,
                        P = [],
                        Q;
                        for (Q = 0; Q < arguments.length; Q++) if (typeof arguments[Q] == 'function') P.push(arguments[Q]);
                        else break;
                        if (Q < arguments.length && typeof arguments[Q] == 'string') {
                            N = arguments[Q];
                            Q++;
                        }
                        if (Q < arguments.length && typeof arguments[Q] == 'number') O = arguments[Q];
                        var R = O == 2 ? true : false;
                        for (Q = 0; Q < P.length; Q++) if (O == 2) R = R && P[Q](M);
                        else R = R || P[Q](M);
                        if (!R) {
                            if (N !== undefined) alert(N);
                            if (S && (S.select || S.focus)) S.select || S.focus();
                            return false;
                        }
                        return true;
                    };
                },
                regex: function (M, N) {
                    return function () {
                        var P = this;
                        var O = P && P.getValue ? P.getValue() : arguments[0];
                        if (!M.test(O)) {
                            if (N !== undefined) alert(N);
                            if (P && (P.select || P.focus)) if (P.select) P.select();
                            else P.focus();
                            return false;
                        }
                        return true;
                    };
                },
                notEmpty: function (M) {
                    return this.regex(J, M);
                },
                integer: function (M) {
                    return this.regex(K, M);
                },
                number: function (M) {
                    return this.regex(L, M);
                },
                equals: function (M, N) {
                    return this.functions(function (O) {
                        return O == M;
                    },
                    N);
                },
                notEqual: function (M, N) {
                    return this.functions(function (O) {
                        return O != M;
                    },
                    N);
                }
            };
        })();
        a.skins.add = (function () {
            var J = a.skins.add;
            return function (K, L) {
                o[K] = {
                    margins: L.margins
                };
                return J.apply(this, arguments);
            };
        })();
    })();
    e.extend(a.editor.prototype, {
        openDialog: function (l) {
            var m = a.dialog._.dialogDefinitions[l];
            if (typeof m == 'function') {
                var n = this._.storedDialogs || (this._.storedDialogs = {}),
                    o = n[l] || (n[l] = new a.dialog(this, l));
                o.show();
                return o;
            } else if (m == 'failed') throw new Error('[CKEDITOR.dialog.openDialog] Dialog "' + l + '" failed when loading definition.');
            var p = a.document.getBody(),
                q = p.$.style.cursor,
                r = this;
            p.setStyle('cursor', 'wait');
            a.scriptLoader.load(a.getUrl(m), function () {
                if (typeof a.dialog._.dialogDefinitions[l] != 'function') a.dialog._.dialogDefinitions[l] = 'failed';
                r.openDialog(l);
                p.setStyle('cursor', q);
            });
            return null;
        }
    });
    i.dialog_backgroundCoverColor = 'white';
    i.dialog_backgroundCoverOpacity = 0.5;
    i.dialog_magnetDistance = 20;
    (function () {
        var l = function (n, o) {
            return n._.modes && n._.modes[o || n.mode];
        },
            m;
        j.add('editingblock', {
            init: function (n) {
                if (!n.config.editingBlock) return;
                n.on('themeSpace', function (o) {
                    if (o.data.space == 'contents') o.data.html += '<br>';
                });
                n.on('themeLoaded', function () {
                    n.fireOnce('editingBlockReady');
                });
                n.on('uiReady', function () {
                    n.setMode(n.config.startupMode);
                });
                n.on('afterSetData', function () {
                    if (!m) {
                        function o() {
                            m = true;
                            l(n).loadData(n.getData());
                            m = false;
                        };
                        if (n.mode) o();
                        else n.on('mode', function () {
                            o();
                            n.removeListener('mode', arguments.callee);
                        });
                    }
                });
                n.on('beforeGetData', function () {
                    if (!m && n.mode) {
                        m = true;
                        n.setData(l(n).getData());
                        m = false;
                    }
                });
                n.on('getSnapshot', function (o) {
                    if (n.mode) o.data = l(n).getSnapshotData();
                });
                n.on('loadSnapshot', function (o) {
                    if (n.mode) l(n).loadSnapshotData(o.data);
                });
                n.on('mode', function (o) {
                    o.removeListener();
                    var p = n.container;
                    if (b.webkit && b.version < 528) {
                        var q = n.config.tabIndex || n.element.getAttribute('tabindex') || 0;
                        p = p.append(h.createFromHtml('<input tabindex="' + q + '"' + ' style="position:absolute; left:-10000">'));
                    }
                    p.on('focus', function () {
                        n.focus();
                    });
                    if (n.config.startupFocus) n.focus();
                    setTimeout(function () {
                        n.fireOnce('instanceReady');
                        a.fire('instanceReady', null, n);
                    });
                });
            }
        });
        a.editor.prototype.mode = '';
        a.editor.prototype.addMode = function (n, o) {
            o.name = n;
            (this._.modes || (this._.modes = {}))[n] = o;
        };
        a.editor.prototype.setMode = function (n) {
            var o, p = this.getThemeSpace('contents'),
                q = this.checkDirty();
            if (this.mode) {
                if (n == this.mode) return;
                this.fire('beforeModeUnload');
                var r = l(this);
                o = r.getData();
                r.unload(p);
                this.mode = '';
            }
            p.setHtml('');
            var s = l(this, n);
            if (!s) throw '[CKEDITOR.editor.setMode] Unknown mode "' + n + '".';
            if (!q) this.on('mode', function () {
                this.resetDirty();
                this.removeListener('mode', arguments.callee);
            });
            s.load(p, typeof o != 'string' ? this.getData() : o);
        };
        a.editor.prototype.focus = function () {
            var n = l(this);
            if (n) n.focus();
        };
    })();
    i.startupMode = 'wysiwyg';
    i.startupFocus = false;
    i.editingBlock = true;
    j.add('panel', {
        beforeInit: function (l) {
            l.ui.addHandler(2, k.panel.handler);
        }
    });
    a.UI_PANEL = 2;
    k.panel = function (l, m) {
        var n = this;
        if (m) e.extend(n, m);
        e.extend(n, {
            className: '',
            css: []
        });
        n.id = e.getNextNumber();
        n.document = l;
        n._ = {
            blocks: {}
        };
    };
    k.panel.handler = {
        create: function (l) {
            return new k.panel(l);
        }
    };
    k.panel.prototype = {
        renderHtml: function (l) {
            var m = [];
            this.render(l, m);
            return m.join('');
        },
        render: function (l, m) {
            var o = this;
            var n = 'cke_' + o.id;
            m.push('<div class="', l.skinClass, '" lang="', l.langCode, '" style="display:none;z-index:' + (l.config.baseFloatZIndex + 1) + '">' + '<div' + ' id=', n, ' dir=', l.lang.dir, ' class="cke_panel cke_', l.lang.dir);
            if (o.className) m.push(' ', o.className);
            m.push('">');
            if (o.forceIFrame || o.css.length) {
                m.push('<iframe id="', n, '_frame" frameborder="0" src="javascript:void(');
                m.push(b.isCustomDomain() ? "(function(){document.open();document.domain='" + document.domain + "';" + 'document.close();' + '})()' : '0');
                m.push(')"></iframe>');
            }
            m.push('</div></div>');
            return n;
        },
        getHolderElement: function () {
            var l = this._.holder;
            if (!l) {
                if (this.forceIFrame || this.css.length) {
                    var m = this.document.getById('cke_' + this.id + '_frame'),
                        n = m.getParent(),
                        o = n.getAttribute('dir'),
                        p = n.getParent().getAttribute('class'),
                        q = n.getParent().getAttribute('lang'),
                        r = m.getFrameDocument();
                    r.$.open();
                    if (b.isCustomDomain()) r.$.domain = document.domain;
                    var s = e.addFunction(e.bind(function (u) {
                        this.isLoaded = true;
                        if (this.onLoad) this.onLoad();
                    },
                    this));
                    r.$.write('<!DOCTYPE html><html dir="' + o + '" class="' + p + '_container" lang="' + q + '">' + '<head>' + '<style>.' + p + '_container{visibility:hidden}</style>' + '</head>' + '<body class="cke_' + o + ' cke_panel_frame ' + b.cssClass + '" style="margin:0;padding:0"' + ' onload="( window.CKEDITOR || window.parent.CKEDITOR ).tools.callFunction(' + s + ');">' + '</body>' + '<link type="text/css" rel=stylesheet href="' + this.css.join('"><link type="text/css" rel="stylesheet" href="') + '">' + '</html>');
                    r.$.close();
                    var t = r.getWindow();
                    t.$.CKEDITOR = a;
                    r.on('keydown', function (u) {
                        var w = this;
                        var v = u.data.getKeystroke();
                        if (w._.onKeyDown && w._.onKeyDown(v) === false) {
                            u.data.preventDefault();
                            return;
                        }
                        if (v == 27) w.onEscape && w.onEscape();
                    },
                    this);
                    l = r.getBody();
                } else l = this.document.getById('cke_' + this.id);
                this._.holder = l;
            }
            return l;
        },
        addBlock: function (l, m) {
            var n = this;
            m = n._.blocks[l] = m || new k.panel.block(n.getHolderElement());
            if (!n._.currentBlock) n.showBlock(l);
            return m;
        },
        getBlock: function (l) {
            return this._.blocks[l];
        },
        showBlock: function (l) {
            var p = this;
            var m = p._.blocks,
                n = m[l],
                o = p._.currentBlock;
            if (o) o.hide();
            p._.currentBlock = n;
            n._.focusIndex = -1;
            p._.onKeyDown = n.onKeyDown && e.bind(n.onKeyDown, n);
            n.show();
            return n;
        }
    };
    k.panel.block = e.createClass({
        $: function (l) {
            this.element = l.append(l.getDocument().createElement('div', {
                attributes: {
                    'class': 'cke_panel_block'
                },
                styles: {
                    display: 'none'
                }
            }));
            this.keys = {};
            this._.focusIndex = -1;
        },
        _: {},
        proto: {
            show: function () {
                this.element.setStyle('display', '');
            },
            hide: function () {
                var l = this;
                if (!l.onHide || l.onHide.call(l) !== true) l.element.setStyle('display', 'none');
            },
            onKeyDown: function (l) {
                var q = this;
                var m = q.keys[l];
                switch (m) {
                case 'next':
                    var n = q._.focusIndex,
                        o = q.element.getElementsByTag('a'),
                        p;
                    while (p = o.getItem(++n)) if (p.getAttribute('_cke_focus') && p.$.offsetWidth) {
                        q._.focusIndex = n;
                        p.focus();
                        break;
                    }
                    return false;
                case 'prev':
                    n = q._.focusIndex;
                    o = q.element.getElementsByTag('a');
                    while (n > 0 && (p = o.getItem(--n))) if (p.getAttribute('_cke_focus') && p.$.offsetWidth) {
                        q._.focusIndex = n;
                        p.focus();
                        break;
                    }
                    return false;
                case 'click':
                    n = q._.focusIndex;
                    p = n >= 0 && q.element.getElementsByTag('a').getItem(n);
                    if (p) p.$.click ? p.$.click() : p.$.onclick();
                    return false;
                }
                return true;
            }
        }
    });
    j.add('listblock', {
        requires: ['panel'],
        onLoad: function () {
            k.panel.prototype.addListBlock = function (l, m) {
                return this.addBlock(l, new k.listBlock(this.getHolderElement(), m));
            };
            k.listBlock = e.createClass({
                base: k.panel.block,
                $: function (l, m) {
                    var o = this;
                    o.base(l);
                    o.multiSelect = !!m;
                    var n = o.keys;
                    n[40] = 'next';
                    n[9] = 'next';
                    n[38] = 'prev';
                    n[2000 + 9] = 'prev';
                    n[32] = 'click';
                    o._.pendingHtml = [];
                    o._.items = {};
                    o._.groups = {};
                },
                _: {
                    close: function () {
                        if (this._.started) {
                            this._.pendingHtml.push('</ul>');
                            delete this._.started;
                        }
                    },
                    getClick: function () {
                        if (!this._.click) this._.click = e.addFunction(function (l) {
                            var n = this;
                            var m = true;
                            if (n.multiSelect) m = n.toggle(l);
                            else n.mark(l);
                            if (n.onClick) n.onClick(l, m);
                        },
                        this);
                        return this._.click;
                    }
                },
                proto: {
                    add: function (l, m, n) {
                        var q = this;
                        var o = q._.pendingHtml,
                            p = 'cke_' + e.getNextNumber();
                        if (!q._.started) {
                            o.push('<ul class=cke_panel_list>');
                            q._.started = 1;
                        }
                        q._.items[l] = p;
                        o.push('<li id=', p, ' class=cke_panel_listItem><a _cke_focus=1 hidefocus=true title="', n || l, '" href="javascript:void(\'', l, '\')" onclick="CKEDITOR.tools.callFunction(', q._.getClick(), ",'", l, "'); return false;\">", m || l, '</a></li>');
                    },
                    startGroup: function (l) {
                        this._.close();
                        var m = 'cke_' + e.getNextNumber();
                        this._.groups[l] = m;
                        this._.pendingHtml.push('<h1 id=', m, ' class=cke_panel_grouptitle>', l, '</h1>');
                    },
                    commit: function () {
                        var l = this;
                        l._.close();
                        l.element.appendHtml(l._.pendingHtml.join(''));
                        l._.pendingHtml = [];
                    },
                    toggle: function (l) {
                        var m = this.isMarked(l);
                        if (m) this.unmark(l);
                        else this.mark(l);
                        return !m;
                    },
                    hideGroup: function (l) {
                        var m = this.element.getDocument().getById(this._.groups[l]),
                            n = m && m.getNext();
                        if (m) {
                            m.setStyle('display', 'none');
                            if (n && n.getName() == 'ul') n.setStyle('display', 'none');
                        }
                    },
                    hideItem: function (l) {
                        this.element.getDocument().getById(this._.items[l]).setStyle('display', 'none');
                    },
                    showAll: function () {
                        var l = this._.items,
                            m = this._.groups,
                            n = this.element.getDocument();
                        for (var o in l) n.getById(l[o]).setStyle('display', '');
                        for (var p in m) {
                            var q = n.getById(m[p]),
                                r = q.getNext();
                            q.setStyle('display', '');
                            if (r && r.getName() == 'ul') r.setStyle('display', '');
                        }
                    },
                    mark: function (l) {
                        var m = this;
                        if (!m.multiSelect) m.unmarkAll();
                        m.element.getDocument().getById(m._.items[l]).addClass('cke_selected');
                    },
                    unmark: function (l) {
                        this.element.getDocument().getById(this._.items[l]).removeClass('cke_selected');
                    },
                    unmarkAll: function () {
                        var l = this._.items,
                            m = this.element.getDocument();
                        for (var n in l) m.getById(l[n]).removeClass('cke_selected');
                    },
                    isMarked: function (l) {
                        return this.element.getDocument().getById(this._.items[l]).hasClass('cke_selected');
                    },
                    focus: function (l) {
                        this._.focusIndex = -1;
                        if (l) {
                            var m = this.element.getDocument().getById(this._.items[l]).getFirst(),
                                n = this.element.getElementsByTag('a'),
                                o, p = -1;
                            while (o = n.getItem(++p)) if (o.equals(m)) {
                                this._.focusIndex = p;
                                break;
                            }
                            setTimeout(function () {
                                m.focus();
                            },
                            0);
                        }
                    }
                }
            });
        }
    });
    j.add('dialogui');
    (function () {
        var l = function (s) {
            var v = this;
            v._ || (v._ = {});
            v._['default'] = v._.initValue = s['default'] || '';
            var t = [v._];
            for (var u = 1; u < arguments.length; u++) t.push(arguments[u]);
            t.push(true);
            e.extend.apply(e, t);
            return v._;
        },
            m = {
            build: function (s, t, u) {
                return new k.dialog.textInput(s, t, u);
            }
        },
            n = {
            build: function (s, t, u) {
                return new k.dialog[t.type](s, t, u);
            }
        },
            o = {
            isChanged: function () {
                return this.getValue() != this.getInitValue();
            },
            reset: function () {
                this.setValue(this.getInitValue());
            },
            setInitValue: function () {
                this._.initValue = this.getValue();
            },
            resetInitValue: function () {
                this._.initValue = this._['default'];
            },
            getInitValue: function () {
                return this._.initValue;
            }
        },
            p = e.extend({},
        k.dialog.uiElement.prototype.eventProcessors, {
            onChange: function (s, t) {
                if (!this._.domOnChangeRegistered) {
                    s.on('load', function () {
                        this.getInputElement().on('change', function () {
                            this.fire('change', {
                                value: this.getValue()
                            });
                        },
                        this);
                    },
                    this);
                    this._.domOnChangeRegistered = true;
                }
                this.on('change', t);
            }
        },
        true),
            q = /^on([A-Z]\w+)/,
            r = function (s) {
            for (var t in s) if (q.test(t) || t == 'title' || t == 'type') delete s[t];
            return s;
        };
        e.extend(k.dialog, {
            labeledElement: function (s, t, u, v) {
                if (arguments.length < 4) return;
                var w = l.call(this, t);
                w.labelId = e.getNextNumber() + '_label';
                var x = this._.children = [],
                    y = function () {
                    var z = [];
                    if (t.labelLayout != 'horizontal') z.push('<div class="cke_dialog_ui_labeled_label" id="', w.labelId, '" >', t.label, '</div>', '<div class="cke_dialog_ui_labeled_content">', v(s, t), '</div>');
                    else {
                        var A = {
                            type: 'hbox',
                            widths: t.widths,
                            padding: 0,
                            children: [{
                                type: 'html',
                                html: '<span class="cke_dialog_ui_labeled_label" id="' + w.labelId + '">' + e.htmlEncode(t.label) + '</span>'
                            },
                            {
                                type: 'html',
                                html: '<span class="cke_dialog_ui_labeled_content">' + v(s, t) + '</span>'
                            }]
                        };
                        a.dialog._.uiElementBuilders.hbox.build(s, A, z);
                    }
                    return z.join('');
                };
                k.dialog.uiElement.call(this, s, t, u, 'div', null, null, y);
            },
            textInput: function (s, t, u) {
                if (arguments.length < 3) return;
                l.call(this, t);
                var v = this._.inputId = e.getNextNumber() + '_textInput',
                    w = {
                    'class': 'cke_dialog_ui_input_' + t.type,
                    id: v,
                    type: 'text'
                },
                    x;
                if (t.validate) this.validate = t.validate;
                if (t.maxLength) w.maxlength = t.maxLength;
                if (t.size) w.size = t.size;
                var y = this,
                    z = false;
                s.on('load', function () {
                    y.getInputElement().on('keydown', function (B) {
                        if (B.data.getKeystroke() == 13) z = true;
                    });
                    y.getInputElement().on('keyup', function (B) {
                        if (B.data.getKeystroke() == 13 && z) {
                            s.getButton('ok') && s.getButton('ok').click();
                            z = false;
                        }
                    },
                    null, null, 1000);
                });
                var A = function () {
                    var B = ['<div class="cke_dialog_ui_input_', t.type, '"'];
                    if (t.width) B.push('style="width:' + t.width + '" ');
                    B.push('><input ');
                    for (var C in w) B.push(C + '="' + w[C] + '" ');
                    B.push(' /></div>');
                    return B.join('');
                };
                k.dialog.labeledElement.call(this, s, t, u, A);
            },
            textarea: function (s, t, u) {
                if (arguments.length < 3) return;
                l.call(this, t);
                var v = this,
                    w = this._.inputId = e.getNextNumber() + '_textarea',
                    x = {};
                if (t.validate) this.validate = t.validate;
                x.rows = t.rows || 5;
                x.cols = t.cols || 20;
                var y = function () {
                    var z = ['<div class="cke_dialog_ui_input_textarea"><textarea class="cke_dialog_ui_input_textarea" id="', w, '" '];
                    for (var A in x) z.push(A + '="' + e.htmlEncode(x[A]) + '" ');
                    z.push('>', e.htmlEncode(v._['default']), '</textarea></div>');
                    return z.join('');
                };
                k.dialog.labeledElement.call(this, s, t, u, y);
            },
            checkbox: function (s, t, u) {
                if (arguments.length < 3) return;
                var v = l.call(this, t, {
                    'default': !!t['default']
                });
                if (t.validate) this.validate = t.validate;
                var w = function () {
                    var x = e.extend({},
                    t, {
                        id: t.id ? t.id + '_checkbox' : e.getNextNumber() + '_checkbox'
                    },
                    true),
                        y = [],
                        z = {
                        'class': 'cke_dialog_ui_checkbox_input',
                        type: 'checkbox'
                    };
                    r(x);
                    if (t['default']) z.checked = 'checked';
                    v.checkbox = new k.dialog.uiElement(s, x, y, 'input', null, z);
                    y.push(' <label for="', z.id, '">', e.htmlEncode(t.label), '</label>');
                    return y.join('');
                };
                k.dialog.uiElement.call(this, s, t, u, 'span', null, null, w);
            },
            radio: function (s, t, u) {
                if (arguments.length < 3) return;
                l.call(this, t);
                if (!this._['default']) this._['default'] = this._.initValue = t.items[0][1];
                if (t.validate) this.validate = t.valdiate;
                var v = [],
                    w = this,
                    x = function () {
                    var y = [],
                        z = [],
                        A = {
                        'class': 'cke_dialog_ui_radio_item'
                    },
                        B = t.id ? t.id + '_radio' : e.getNextNumber() + '_radio';
                    for (var C = 0; C < t.items.length; C++) {
                        var D = t.items[C],
                            E = D[2] !== undefined ? D[2] : D[0],
                        F = D[1] !== undefined ? D[1] : D[0],
                        G = e.extend({},
                        t, {
                            id: e.getNextNumber() + '_radio_input',
                            title: null,
                            type: null
                        },
                        true),
                        H = e.extend({},
                        G, {
                            id: null,
                            title: E
                        },
                        true),
                        I = {
                            type: 'radio',
                            'class': 'cke_dialog_ui_radio_input',
                            name: B,
                            value: F
                        },
                        J = [];
                        if (w._['default'] == F) I.checked = 'checked';
                        r(G);
                        r(H);
                        v.push(new k.dialog.uiElement(s, G, J, 'input', null, I));
                        J.push(' ');
                        new k.dialog.uiElement(s, H, J, 'label', null, {
                            'for': I.id
                        },
                        D[0]);
                        y.push(J.join(''));
                    }
                    new k.dialog.hbox(s, [], y, z);
                    return z.join('');
                };
                k.dialog.labeledElement.call(this, s, t, u, x);
                this._.children = v;
            },
            button: function (s, t, u) {
                if (!arguments.length) return;
                if (typeof t == 'function') t = t(s.getParentEditor());
                l.call(this, t, {
                    disabled: t.disabled || false
                });
                a.event.implementOn(this);
                var v = this;
                s.on('load', function (x) {
                    var y = this.getElement();
                    (function () {
                        y.on('click', function (z) {
                            v.fire('click', {
                                dialog: v.getDialog()
                            });
                            z.data.preventDefault();
                        });
                    })();
                    y.unselectable();
                },
                this);
                var w = e.extend({},
                t);
                delete w.style;
                k.dialog.uiElement.call(this, s, w, u, 'a', null, {
                    style: t.style,
                    href: 'javascript:void(0)',
                    title: t.label,
                    hidefocus: 'true',
                    'class': t['class']
                },
                '<span class="cke_dialog_ui_button">' + e.htmlEncode(t.label) + '</span>');
            },
            select: function (s, t, u) {
                if (arguments.length < 3) return;
                var v = l.call(this, t);
                if (t.validate) this.validate = t.validate;
                var w = function () {
                    var x = e.extend({},
                    t, {
                        id: t.id ? t.id + '_select' : e.getNextNumber() + '_select'
                    },
                    true),
                        y = [],
                        z = [],
                        A = {
                        'class': 'cke_dialog_ui_input_select'
                    };
                    if (t.size != undefined) A.size = t.size;
                    if (t.multiple != undefined) A.multiple = t.multiple;
                    r(x);
                    for (var B = 0, C; B < t.items.length && (C = t.items[B]); B++) z.push('<option value="', e.htmlEncode(C[1] !== undefined ? C[1] : C[0]), '" /> ', e.htmlEncode(C[0]));
                    v.select = new k.dialog.uiElement(s, x, y, 'select', null, A, z.join(''));
                    return y.join('');
                };
                k.dialog.labeledElement.call(this, s, t, u, w);
            },
            file: function (s, t, u) {
                if (arguments.length < 3) return;
                if (t['default'] === undefined) t['default'] = '';
                var v = e.extend(l.call(this, t), {
                    definition: t,
                    buttons: []
                });
                if (t.validate) this.validate = t.validate;
                var w = function () {
                    v.frameId = e.getNextNumber() + '_fileInput';
                    var x = c && document.domain != window.location.hostname,
                        y = ['<iframe frameborder="0" allowtransparency="0" class="cke_dialog_ui_input_file" id="', v.frameId, '" title="', t.label, '" src="javascript:void('];
                    y.push(x ? "(function(){document.open();document.domain='" + document.domain + "';" + 'document.close();' + '})()' : '0');
                    y.push(')"></iframe>');
                    return y.join('');
                };
                s.on('load', function () {
                    var x = a.document.getById(v.frameId),
                        y = x.getParent();
                    y.addClass('cke_dialog_ui_input_file');
                });
                k.dialog.labeledElement.call(this, s, t, u, w);
            },
            fileButton: function (s, t, u) {
                if (arguments.length < 3) return;
                var v = l.call(this, t),
                    w = this;
                if (t.validate) this.validate = t.validate;
                var x = e.extend({},
                t),
                    y = x.onClick;
                x.className = (x.className ? x.className + ' ' : '') + ('cke_dialog_ui_button');
                x.onClick = function (z) {
                    var A = t['for'];
                    if (!y || y.call(this, z) !== false) {
                        s.getContentElement(A[0], A[1]).submit();
                        this.disable();
                    }
                };
                s.on('load', function () {
                    s.getContentElement(t['for'][0], t['for'][1])._.buttons.push(w);
                });
                k.dialog.button.call(this, s, x, u);
            },
            html: (function () {
                var s = /^\s*<[\w:]+\s+([^>]*)?>/,
                    t = /^(\s*<[\w:]+(?:\s+[^>]*)?)((?:.|\r|\n)+)$/,
                    u = /\/$/;
                return function (v, w, x) {
                    if (arguments.length < 3) return;
                    var y = [],
                        z, A = w.html,
                        B, C;
                    if (A.charAt(0) != '<') A = '<span>' + A + '</span>';
                    if (w.focus) {
                        var D = this.focus;
                        this.focus = function () {
                            D.call(this);
                            w.focus.call(this);
                            this.fire('focus');
                        };
                        if (w.isFocusable) {
                            var E = this.isFocusable;
                            this.isFocusable = E;
                        }
                        this.keyboardFocusable = true;
                    }
                    k.dialog.uiElement.call(this, v, w, y, 'span', null, null, '');
                    z = y.join('');
                    B = z.match(s);
                    C = A.match(t) || ['', '', ''];
                    if (u.test(C[1])) {
                        C[1] = C[1].slice(0, -1);
                        C[2] = '/' + C[2];
                    }
                    x.push([C[1], ' ', B[1] || '', C[2]].join(''));
                };
            })()
        },
        true);
        k.dialog.html.prototype = new k.dialog.uiElement();
        k.dialog.labeledElement.prototype = e.extend(new k.dialog.uiElement(), {
            setLabel: function (s) {
                var t = a.document.getById(this._.labelId);
                if (t.getChildCount() < 1) new d.text(s, a.document).appendTo(t);
                else t.getChild(0).$.nodeValue = s;
                return this;
            },
            getLabel: function () {
                var s = a.document.getById(this._.labelId);
                if (!s || s.getChildCount() < 1) return '';
                else return s.getChild(0).getText();
            },
            eventProcessors: p
        },
        true);
        k.dialog.button.prototype = e.extend(new k.dialog.uiElement(), {
            click: function () {
                var s = this;
                if (!s._.disabled) return s.fire('click', {
                    dialog: s._.dialog
                });
                s.getElement().$.blur();
                return false;
            },
            enable: function () {
                this._.disabled = false;
                var s = this.getElement();
                s && s.removeClass('disabled');
            },
            disable: function () {
                this._.disabled = true;
                this.getElement().addClass('disabled');
            },
            isVisible: function () {
                return !!this.getElement().$.firstChild.offsetHeight;
            },
            isEnabled: function () {
                return !this._.disabled;
            },
            eventProcessors: e.extend({},
            k.dialog.uiElement.prototype.eventProcessors, {
                onClick: function (s, t) {
                    this.on('click', t);
                }
            },
            true),
            accessKeyUp: function () {
                this.click();
            },
            accessKeyDown: function () {
                this.focus();
            },
            keyboardFocusable: true
        },
        true);
        k.dialog.textInput.prototype = e.extend(new k.dialog.labeledElement(), {
            getInputElement: function () {
                return a.document.getById(this._.inputId);
            },
            focus: function () {
                var s = this.selectParentTab();
                setTimeout(function () {
                    var t = s.getInputElement();
                    t && t.$.focus();
                },
                0);
            },
            select: function () {
                var s = this.selectParentTab();
                setTimeout(function () {
                    var t = s.getInputElement();
                    if (t) {
                        t.$.focus();
                        t.$.select();
                    }
                },
                0);
            },
            accessKeyUp: function () {
                this.select();
            },
            setValue: function (s) {
                s = s || '';
                return k.dialog.uiElement.prototype.setValue.call(this, s);
            },
            keyboardFocusable: true
        },
        o, true);
        k.dialog.textarea.prototype = new k.dialog.textInput();
        k.dialog.select.prototype = e.extend(new k.dialog.labeledElement(), {
            getInputElement: function () {
                return this._.select.getElement();
            },
            add: function (s, t, u) {
                var v = new h('option', this.getDialog().getParentEditor().document),
                    w = this.getInputElement().$;
                v.$.text = s;
                v.$.value = t === undefined || t === null ? s : t;
                if (u === undefined || u === null) {
                    if (c) w.add(v.$);
                    else w.add(v.$, null);
                } else w.add(v.$, u);
                return this;
            },
            remove: function (s) {
                var t = this.getInputElement().$;
                t.remove(s);
                return this;
            },
            clear: function () {
                var s = this.getInputElement().$;
                while (s.length > 0) s.remove(0);
                return this;
            },
            keyboardFocusable: true
        },
        o, true);
        k.dialog.checkbox.prototype = e.extend(new k.dialog.uiElement(), {
            getInputElement: function () {
                return this._.checkbox.getElement();
            },
            setValue: function (s) {
                this.getInputElement().$.checked = s;
                this.fire('change', {
                    value: s
                });
            },
            getValue: function () {
                return this.getInputElement().$.checked;
            },
            accessKeyUp: function () {
                this.setValue(!this.getValue());
            },
            eventProcessors: {
                onChange: function (s, t) {
                    if (!c) return p.onChange.apply(this, arguments);
                    else {
                        s.on('load', function () {
                            var u = this._.checkbox.getElement();
                            u.on('propertychange', function (v) {
                                v = v.data.$;
                                if (v.propertyName == 'checked') this.fire('change', {
                                    value: u.$.checked
                                });
                            },
                            this);
                        },
                        this);
                        this.on('change', t);
                    }
                    return null;
                }
            },
            keyboardFocusable: true
        },
        o, true);
        k.dialog.radio.prototype = e.extend(new k.dialog.uiElement(), {
            setValue: function (s) {
                var t = this._.children,
                    u;
                for (var v = 0; v < t.length && (u = t[v]); v++) u.getElement().$.checked = u.getValue() == s;
                this.fire('change', {
                    value: s
                });
            },
            getValue: function () {
                var s = this._.children;
                for (var t = 0; t < s.length; t++) if (s[t].getElement().$.checked) return s[t].getValue();
                return null;
            },
            accessKeyUp: function () {
                var s = this._.children,
                    t;
                for (t = 0; t < s.length; t++) if (s[t].getElement().$.checked) {
                    s[t].getElement().focus();
                    return;
                }
                s[0].getElement().focus();
            },
            eventProcessors: {
                onChange: function (s, t) {
                    if (!c) return p.onChange.apply(this, arguments);
                    else {
                        s.on('load', function () {
                            var u = this._.children,
                                v = this;
                            for (var w = 0; w < u.length; w++) {
                                var x = u[w].getElement();
                                x.on('propertychange', function (y) {
                                    y = y.data.$;
                                    if (y.propertyName == 'checked' && this.$.checked) v.fire('change', {
                                        value: this.getAttribute('value')
                                    });
                                });
                            }
                        },
                        this);
                        this.on('change', t);
                    }
                    return null;
                }
            },
            keyboardFocusable: true
        },
        o, true);
        k.dialog.file.prototype = e.extend(new k.dialog.labeledElement(), o, {
            getInputElement: function () {
                var s = a.document.getById(this._.frameId).getFrameDocument();
                return s.$.forms.length > 0 ? new h(s.$.forms[0].elements[0]) : this.getElement();
            },
            submit: function () {
                this.getInputElement().getParent().$.submit();
                return this;
            },
            getAction: function (s) {
                return this.getInputElement().getParent().$.action;
            },
            reset: function () {
                var s = a.document.getById(this._.frameId),
                    t = s.getFrameDocument(),
                    u = this._.definition,
                    v = this._.buttons;

                function w() {
                    t.$.open();
                    if (b.isCustomDomain()) t.$.domain = document.domain;
                    var x = '';
                    if (u.size) x = u.size - (c ? 7 : 0);
                    t.$.write(['<html><head><title></title></head><body style="margin: 0; overflow: hidden; background: transparent;">', '<form enctype="multipart/form-data" method="POST" action="', e.htmlEncode(u.action), '">', '<input type="file" name="', e.htmlEncode(u.id || 'cke_upload'), '" size="', e.htmlEncode(x > 0 ? x : ''), '" />', '</form>', '</body></html>'].join(''));
                    t.$.close();
                    for (var y = 0; y < v.length; y++) v[y].enable();
                };
                if (b.gecko) setTimeout(w, 500);
                else w();
            },
            getValue: function () {
                return '';
            },
            eventProcessors: p,
            keyboardFocusable: true
        },
        true);
        k.dialog.fileButton.prototype = new k.dialog.button();
        a.dialog.addUIElement('text', m);
        a.dialog.addUIElement('password', m);
        a.dialog.addUIElement('textarea', n);
        a.dialog.addUIElement('checkbox', n);
        a.dialog.addUIElement('radio', n);
        a.dialog.addUIElement('button', n);
        a.dialog.addUIElement('select', n);
        a.dialog.addUIElement('file', n);
        a.dialog.addUIElement('fileButton', n);
        a.dialog.addUIElement('html', n);
    })();
    a.skins.add('kama', (function () {
        var l = [];
        if (c && b.version < 7) l.push('icons.png', 'images/sprites_ie6.png', 'images/dialog_sides.gif');
        return {
            preload: l,
            editor: {
                css: ['editor.css']
            },
            dialog: {
                css: ['dialog.css']
            },
            templates: {
                css: ['templates.css']
            },
            margins: [0, 0, 0, 0],
            init: function (m) {
                if (m.config.width && !isNaN(m.config.width)) m.config.width -= 12;
                var n;

                function o(q) {
                    if (!n) return null;
                    var r = n.append('style'),
                        s = '/* UI Color Support */.cke_skin_kama .cke_menuitem .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a:focus .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a:active .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover .cke_label,.cke_skin_kama .cke_menuitem a:focus .cke_label,.cke_skin_kama .cke_menuitem a:active .cke_label{\tbackground-color: $color !important;}.cke_skin_kama .cke_menuitem a.cke_disabled:hover .cke_label,.cke_skin_kama .cke_menuitem a.cke_disabled:focus .cke_label,.cke_skin_kama .cke_menuitem a.cke_disabled:active .cke_label{\tbackground-color: transparent !important;}.cke_skin_kama .cke_menuitem a.cke_disabled:hover .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a.cke_disabled:focus .cke_icon_wrapper,.cke_skin_kama .cke_menuitem a.cke_disabled:active .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuitem a.cke_disabled .cke_icon_wrapper{\tbackground-color: $color !important;\tborder-color: $color !important;}.cke_skin_kama .cke_menuseparator{\tbackground-color: $color !important;}.cke_skin_kama .cke_menuitem a:hover,.cke_skin_kama .cke_menuitem a:focus,.cke_skin_kama .cke_menuitem a:active{\tbackground-color: $color !important;}';
                    r.setAttribute('type', 'text/css');
                    var t = /\$color/g;
                    if (b.webkit) {
                        s = s.split('}').slice(0, -1);
                        for (var u in s) s[u] = s[u].split('{');
                    }
                    return (o = function (v) {
                        if (b.webkit) for (var w in s) r.$.sheet.addRule(s[w][0], s[w][1].replace(t, v));
                        else {
                            var x = s.replace(t, v);
                            if (c) r.$.styleSheet.cssText = x;
                            else r.setHtml(x);
                        }
                    })(q);
                };
                e.extend(m, {
                    uiColor: null,
                    getUiColor: function () {
                        return this.uiColor;
                    },
                    setUiColor: function (q) {
                        var r = a.document.getHead().append('style'),
                            s = '#cke_' + m.name.replace('.', '\\.'),
                            t = [s + ' .cke_wrapper', s + '_dialog .cke_dialog_contents', s + '_dialog a.cke_dialog_tab', s + '_dialog .cke_dialog_footer'].join(','),
                            u = 'background-color: $color !important;';
                        r.setAttribute('type', 'text/css');
                        return (this.setUiColor = function (v) {
                            var w = u.replace('$color', v);
                            m.uiColor = v;
                            if (c) r.$.styleSheet.cssText = t + '{' + w + '}';
                            else if (b.webkit) r.$.sheet.addRule(t, w);
                            else r.setHtml(t + '{' + w + '}');
                            o(v);
                        })(q);
                    }
                });
                if (a.menu) {
                    var p = a.menu.prototype.show;
                    a.menu.prototype.show = function () {
                        p.apply(this, arguments);
                        if (!n && m == this.editor) {
                            n = this._.element.getDocument().getHead();
                            o(m.getUiColor());
                        }
                    };
                }
                if (m.config.uiColor) m.setUiColor(m.config.uiColor);
            }
        };
    })());
    if (a.dialog) a.dialog.on('resize', function (l) {
        var m = l.data,
            n = m.width,
            o = m.height,
            p = m.dialog,
            q = p.parts.contents,
            r = !b.quirks;
        if (m.skin != 'kama') return;
        q.setStyles(c || b.gecko && b.version < 10900 ? {
            width: n + 'px',
            height: o + 'px'
        } : {
            'min-width': n + 'px',
            'min-height': o + 'px'
        });
        if (!c) return;
        setTimeout(function () {
            var s = q.getParent(),
                t = s.getParent(),
                u = t.getChild(2);
            u.setStyle('width', s.$.offsetWidth + 'px');
            u = t.getChild(7);
            u.setStyle('width', s.$.offsetWidth - 28 + 'px');
            u = t.getChild(4);
            u.setStyle('height', s.$.offsetHeight - 31 - 14 + 'px');
            u = t.getChild(5);
            u.setStyle('height', s.$.offsetHeight - 31 - 14 + 'px');
        },
        100);
    });
    a.themes.add('default', (function () {
        return {
            build: function (l, m) {
                var n = l.name,
                    o = l.element,
                    p = l.elementMode;
                if (!o || p == 0) return;
                if (p == 1) o.hide();
                var q = l.fire('themeSpace', {
                    space: 'top',
                    html: ''
                }).html,
                    r = l.fire('themeSpace', {
                    space: 'contents',
                    html: ''
                }).html,
                    s = l.fireOnce('themeSpace', {
                    space: 'bottom',
                    html: ''
                }).html,
                    t = r && l.config.height,
                    u = l.config.tabIndex || l.element.getAttribute('tabindex') || 0;
                if (!r) t = 'auto';
                else if (!isNaN(t)) t += 'px';
                var v = '',
                    w = l.config.width;
                if (w) {
                    if (!isNaN(w)) w += 'px';
                    v += 'width: ' + w + ';';
                }
                var x = h.createFromHtml(['<span id="cke_', n, '" onmousedown="return false;" class="', l.skinClass, '" dir="', l.lang.dir, '" title="', b.gecko ? ' ' : '', '" lang="', l.langCode, '" tabindex="' + u + '"' + (v ? ' style="' + v + '"' : '') + '>' + '<span class="', b.cssClass, '"><span class="cke_wrapper cke_', l.lang.dir, '"><table class="cke_editor" border="0" cellspacing="0" cellpadding="0"><tbody><tr', q ? '' : ' style="display:none"', '><td id="cke_top_', n, '" class="cke_top">', q, '</td></tr><tr', r ? '' : ' style="display:none"', '><td id="cke_contents_', n, '" class="cke_contents" style="height:', t, '">', r, '</td></tr><tr', s ? '' : ' style="display:none"', '><td id="cke_bottom_', n, '" class="cke_bottom">', s, '</td></tr></tbody></table><style>.', l.skinClass, '{visibility:hidden;}</style></span></span></span>'].join(''));
                x.getChild([0, 0, 0, 0, 0]).unselectable();
                x.getChild([0, 0, 0, 0, 2]).unselectable();
                if (p == 1) x.insertAfter(o);
                else o.append(x);
                l.container = x;
                l.fireOnce('themeLoaded');
                l.fireOnce('uiReady');
            },
            buildDialog: function (l) {
                var m = e.getNextNumber(),
                    n = h.createFromHtml(['<div id="cke_' + l.name.replace('.', '\\.') + '_dialog" class="cke_skin_', l.skinName, '" dir="', l.lang.dir, '" lang="', l.langCode, '"><div class="cke_dialog', ' ' + b.cssClass, ' cke_', l.lang.dir, '" style="position:absolute"><div class="%body"><div id="%title#" class="%title"></div><div id="%close_button#" class="%close_button"><span>X</span></div><div id="%tabs#" class="%tabs"></div><div id="%contents#" class="%contents"></div><div id="%footer#" class="%footer"></div></div><div id="%tl#" class="%tl"></div><div id="%tc#" class="%tc"></div><div id="%tr#" class="%tr"></div><div id="%ml#" class="%ml"></div><div id="%mr#" class="%mr"></div><div id="%bl#" class="%bl"></div><div id="%bc#" class="%bc"></div><div id="%br#" class="%br"></div></div>', c ? '' : '<style>.cke_dialog{visibility:hidden;}</style>', '</div>'].join('').replace(/#/g, '_' + m).replace(/%/g, 'cke_dialog_')),
                    o = n.getChild([0, 0]);
                o.getChild(0).unselectable();
                o.getChild(1).unselectable();
                return {
                    element: n,
                    parts: {
                        dialog: n.getChild(0),
                        title: o.getChild(0),
                        close: o.getChild(1),
                        tabs: o.getChild(2),
                        contents: o.getChild(3),
                        footer: o.getChild(4)
                    }
                };
            },
            destroy: function (l) {
                var m = l.container;
                if (c) {
                    m.setStyle('display', 'none');
                    var n = document.body.createTextRange();
                    n.moveToElementText(m.$);
                    try {
                        n.select();
                    } catch(o) {}
                }
                if (m) m.remove();
                if (l.elementMode == 1) {
                    l.element.show();
                    delete l.element;
                }
            }
        };
    })());
    a.editor.prototype.getThemeSpace = function (l) {
        var m = 'cke_' + l,
            n = this._[m] || (this._[m] = a.document.getById(m + '_' + this.name));
        return n;
    };
    a.editor.prototype.resize = function (l, m, n, o) {
        var p = /^\d+$/;
        if (p.test(l)) l += 'px';
        var q = a.document.getById('cke_contents_' + this.name),
            r = o ? q.getAscendant('table').getParent() : q.getAscendant('table').getParent().getParent().getParent();
        b.webkit && r.setStyle('display', 'none');
        r.setStyle('width', l);
        if (b.webkit) {
            r.$.offsetWidth;
            r.setStyle('display', '');
        }
        var s = n ? 0 : (r.$.offsetHeight || 0) - (q.$.clientHeight || 0);
        q.setStyle('height', Math.max(m - s, 0) + 'px');
        this.fire('resize');
    };
    a.editor.prototype.getResizable = function () {
        return this.container.getChild([0, 0]);
    };
})();