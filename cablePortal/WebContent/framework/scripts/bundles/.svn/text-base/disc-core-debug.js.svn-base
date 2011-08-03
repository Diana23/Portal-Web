// bea.wlp.disc
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

/**
 * @name bea.wlp.disc
 * @overview
 *      This module provides the core infrastructure for the basic OOP and code modularization facilities for the rest
 *      of Disc and other frameworks and applications that may be based on it.  The OOP and modularization models expressed
 *      in this library are most directly implemented as the <code>Class</code> and <code>Module</code> constructs,
 *      though other support entities are defined in this module that are worthy of note, including a Firebug-like
 *      console abstraction (the <code>Console</code> object), and a module-based localization facility 
 *      (the <code>Catalog</code> class).
 *      <p/>
 *      Individual methods, as well as modules, classes and objects named with a leading underscore ('_')
 *      character are considered internal implementation details. Use of such objects/methods is unsupported
 *      and the behavior and existence of such objects/methods is subject to change without notice.
 */
(function() {
    var $ = new function() {
        // Set up a module self-reference for convenience and clarity
        var $ = this;

        /**
         * A basic configuration API for the core Disc module.
         *
         * @object Config
         */
        $.Config = {
            _scriptPath: "framework/scripts/",

            _combine: function(a, b) {                
                return (a && b ? a + "/" + b : (a ? a : b || "")).replace(/\/{2,}/, "/");
            },

            /**
             * This method should be used to set the webapp's context path. This setting effects all on-demand script
             * loading done by Disc, and should therefore only be set once for a given document.  Not setting this field
             * to an appropriate value may result in unreliable on-demand script loading.  On-demand script loading
             * may occur for WLP server instances running in development mode.
             *
             * @method setContextPath
             * @param {string} contextPath
             *      The value to use as context path for on-demand module loading
             */
            setContextPath: function(contextPath) {
                if(""==contextPath){
            		this._contextPath = "/";
            	}else{
                	this._contextPath = contextPath;
                }
            },

            /**
             * Returns the current context path setting.
             *
             * @method getContextPath
             * @returns {string}
             *      The context path, if set; null otherwise
             */
            getContextPath: function() {
                return (this._contextPath || null);
            },

            /**
             * Returns the current <i>script path</i> setting, with any additional path information (indicated by the
             * optional <code>scriptName</code> argument) appended.  The script path is the result of combining
             * the webapp's context path with the constant "framework/scripts/".
             *
             * @method getScriptPath
             * @param {string} [scriptName]
             *      Additional path/filename information to be appended to this object's <i>script path</i> setting,
             *      if any; if not specified, the bare script path is returned
             * @returns {string}
             *      The raw <i>script path</i> value or the union of said and the scriptName param or an empty string
             *      if neither value is defined
             */
            getScriptPath: function(scriptName) {
                return this._combine(this._contextPath, this._combine(this._scriptPath, scriptName));
            }
        }

        /**
         * A simple helper that provides utilities for dealing with objects.
         *
         * @object _Object
         */
        $._Object = {
            /**
             * The <code>mixin</code> function provides an algorithm to apply mixin fields and methods from a source
             * object to a destination object.  Its main intent is in the application of the Mixin OOP pattern,
             * but it is also serviceable as a mechanism to copy public fields and methods from one object to another.
             * <p/>
             * Example:
             * <pre>
             *      var forEachPropertyMixin = {
             *          forEachProperty: function(fn) {
             *              for (var x in this) {
             *                  if (x != "forEachProperty") {
             *                      fn(x, this[x]);
             *                  }
             *              }
             *          }
             *      }
             *      var obj = { one: "1", two: "2", three: "3" }
             *      bea.wlp.disc.Object.mixin(obj, forEachPropertyMixin);
             *      var results = [];
             *      obj.forEachProperty(function(name, value) {
             *          results.push(name + "=" + value);
             *      });
             *      assertEquals("one=1,two=2,three=3", results.join(","));
             * </pre>
             * Using mixins to apply a single method or field to an element is overkill, but the above example
             * illustrates how one object can be used as a mixin for another.  Typically mixin source objects will
             * contain multiple, related members to be applied to destination objects and prototypes.
             * <p/>
             * Also note that the mixin operation is akin to a shallow copy of an object; be aware that deep copying
             * does not take place during the mixin-operation, so changes to objects referenced by the source will be
             * reflected in the destination; as such, references in source objects should generally be treated as
             * read-only for the most effective application of this pattern.
             *
             * @method mixin
             * @param {object} dest
             *      The destination object to which the <code>src</code> mixin should be applied
             * @param {object} src
             *      The source mixin object from which to apply the mixin to <code>dest</code>
             * @returns {object}
             *      The <code>dest</code> object instance, after having the <code>src</code> mixin applied
             */
            mixin: function(dest, src) {
                this.each(src,
                    function(name, item) { // iterator
                        dest[name] = item;
                    },
                    function(name, item) { // when
                        return (dest[name] != item);
                    }
                );
                return dest;
            },

            /**
             * The <code>each</code> function is used to iterate over the properties of an object.  Only properties that
             * cause <code>src.hasOwnProperty(propName)</code> to evaluate to true will be reported to the iterator
             * callback.
             * 
             * @method each
             * @param {object} src
             *      The source object whose properties should be traversed
             * @param {function} iterator
             *      The callback to be invoked for each property; the callback will be passed two parameters; first,
             *      the name of the property, and second, the value of the property
             * @param {function} [when]
             *      A second, optional callback that can be used to filter which properties cause the callback to be
             *      invoked
             */
            each: function(src, iterator, when) {
                if (src) {
                    function it(name, item) {
                        if (!when || when(name, item)) {
                            iterator(name, item);
                        }
                    }
                    
                    // Methods like toString, valueOf exist on Object.prototype so they are not
                    // enumerated in a for/in loop and must be considered specially.
                    var ts = "toString", vo = "valueOf";
                    for (var name in src) {
                        if (src.hasOwnProperty(name)) {
                        
                            // just in case "toString" and "valueOf" WERE enumerable
                            ts = (name == ts ? null : ts);
                            vo = (name == vo ? null : vo);
                            it(name, src[name]);
                        }
                    }
                    if (ts && src.hasOwnProperty(ts)) { it(ts, src[ts]); }
                    if (vo && src.hasOwnProperty(vo)) { it(vo, src[vo]); }
                }
            },

            /**
             * The <code>map</code> method is a power constructor that returns a new hash-like object.  It is primarily
             * useful when the number of entries in a hash-like structure needs to be tracked, which is not something
             * easily available using standard associative array notation in JavaScript.  Map objects created with this
             * power constructor also provide a built-in convenience version of the <code>each</code> method.
             * <p/>
             * The map API provides the following common map operations:
             * <ul>
             *      <li>size()</li>
             *      <li>put(key, value)</li>
             *      <li>get(key)</li>
             *      <li>remove(key)</li>
             *      <li>each(iterator, when)</li>
             * </ul>
             *
             * @method each
             * @returns {object}
             *      A new instance of a map object
             */
            map: function() {
                var size = 0;
                var data = { };
                return {
                    size: function() {
                        return size;
                    },
                    put: function(key, value) {
                        var prev = data[key];
                        var prevExists = prev != null;
                        
                        // add the new value (or delete the old one if given an empty value)
                        var valueExists = value != null;                       
                        valueExists ? data[key] = value : delete data[key];
                        size += (valueExists && !prevExists ? 1 : (!valueExists && prevExists ? -1 : 0));
                        return prev;
                    },
                    get: function(key) {
                        return data[key];
                    },
                    remove: function(key) {
                        return this.put(key, null);
                    },
                    each: function(iterator, when) {
                        $._Object.each(data, function(key) {
                            iterator(key, data[key]);
                        }, when);
                    }
                }
            }
        }

        /**
         * The <code>Console</code> object provides a simple abstraction over the well-known but not always present
         * Firebug or Firebug Lite <code>console</code> global.  Writing Firebug console operations to this object
         * instead of the global Firebug console ensures that when that global console is not present, the operation
         * will not fail due to the console object being undefined.  When not available, the firebug-level operations
         * on this object may execute as no-ops.
         * <p/>
         * Listeners can be applied to this object as well, so that any time a message is sent to this object via one
         * of the Firebug APIs, the message is routed to any configured listeners <i>instead</i> of to the default
         * destination (the Firebug console, or lacking Firebug or Firebug Lite installed, the "no-op destination").
         * Adding one or more listeners to this object allows arbitrary message sinks to be set up, such as custom
         * UI components or remote monitors, but the exact nature of such sinks is not defined in this scope.
         * <p/>
         * In the absence of any of Firebug, Firebug Lite, or custom listeners, the default behavior of this object is
         * to no-op all message operations, with the exception of error messages, which cause a standard alert dialog to
         * be displayed with the contents of the error.  Once a custom listener is added, the default error handler is
         * suppressed.
         *
         * @object Console
         * @see <a href="http://getfirebug.com">http://getfirebug.com</a>
         */
        $.Console = new function() {
            var listeners = [];
            listeners.push(function(op, args) {
                if (listeners.length == 1) {
                    if (window.console && console[op]) {
                        console[op].apply(console, args);
                    }
                    else if (op == "error" && args.length > 0) {
                        alert(op.toUpperCase() + ": " + args.join(", "));
                    }
                }
            });

            /**
             * This method allows listeners to be added to the global Disc console object.  All operations
             * (aside from <code>addListener</code> or <code>removeListener</code>) called on this object will cause
             * registered listener callback functions to be invoked.  Listener callback functions should accept two
             * parameters: "op" and "args".  The former is the name of the operation that was invoked (e.g. "log"), and
             * the latter is the list of arguments with which it was called.  Possible operation names include:
             * "assert", "count", "debug", "dir", "dirxml", "error", "group", "groupEnd", "info", "log", "profile",
             * "profileEnd", "time", "timeEnd", "trace", and "warn".  Applying appropriate semantics to each operation
             * is left as an exercise to each listener implementation.
             * <p/>
             * For example, the following code would register a naive listener that routed all messages to an element
             * named "myConsoleOutput", simply appending a new "line" for each routed message:
             * <pre>
             *      bea.wlp.disc.Console.addListener(function(op, args) {
             *          var output = document.getElementById("myConsoleOutput");
             *          output.appendChild(document.createElement("br"));
             *          output.appendChild(document.createTextNode(op + ": " + args.join(",")));
             *      });
             * </pre>
             *
             * @method addListener
             * @param {function} listener
             *      The listener callback function to be added
             */
            this.addListener = function(listener) {
                if (typeof listener == "function") {
                    listeners.push(listener);
                }
            }

            /**
             * This method simply removes a listener from the Disc console object if previously added.  The function
             * reference must be identical to the one added for it to be removed.
             *
             * @method removeListener
             * @param {function} listener
             *      The listener function to be removed
             */
            this.removeListener = function(listener) {
                for (var i = 0, len = listeners.length; i < len; i++) {
                    if (listeners[i] === listener) {
                        listeners.splice(i, 1);
                        break;
                    }
                }
            }

            /** @method assert */
            /** @method count */
            /** @method debug */
            /** @method dir */
            /** @method dirxml */
            /** @method error */
            /** @method group */
            /** @method groupEnd */
            /** @method info */
            /** @method log */
            /** @method profile */
            /** @method profileEnd */
            /** @method time */
            /** @method timeEnd */
            /** @method trace */
            /** @method warn */
            var ops = [
                "assert", "count", "debug", "dir", "dirxml", "error", "group", "groupEnd",
                "info", "log", "profile", "profileEnd", "time", "timeEnd", "trace", "warn"
            ];
            var self = this;
            for (var i = 0; i < ops.length; i++) {
                this[ops[i]] = function(op) {
                    return function() {
                        var i, len, args = [];
                        for (i = 0, len = arguments.length; i < len; i++) {
                            args.push(arguments[i]);
                        }
                        for (i = 0, len = listeners.length; i < len; i++) {
                            listeners[i].call(self, op, args);
                        }
                    }
                }(ops[i]);
            }
        }

        /**
         * The <code>Class</code> helper provides functionality for declaring new "classes" in the Disc framework.
         * These classes support single inheritance of instance and static member fields and methods (mixins may be
         * used in place of multiple inheritance), public or internal initializers, super references, and dynamic
         * injection and removal of members, as demonstrated below.  Additionally, class body declarations may be
         * expressed multiple ways.
         * <p/>
         * <b>Basic Example</b>
         * <p/>
         * This example demonstrates class and subclass declaration, basic inheritance, overrides, initializers, and
         * super references.  Additionally, it uses the concise object literal-notation to express its class body
         * declarations.
         * <pre>
         *      // Declare a base class
         *      var Person = bea.wlp.disc.Class.create({
         *          // Base class initializer
         *          initialize: function(name) {
         *              // Initialize internal instance variables
         *              this._name = name;
         *              this._greeting = "hi";
         *          },
         *          // Declare some instance methods
         *          greet: function(person) {
         *              return (this + " says '" + this._greeting + "' to " + person);
         *          },
         *          toString: function() {
         *              return this._name;
         *          }
         *      });
         *
         *      // Declare the Cowboy subclass of Person
         *      var Cowboy = Person.extend({
         *          initialize: function(name) {
         *              // Call this class's super constructor (Person.initialize)
         *              this.sup(name);
         *              // Override the base class's greeting
         *              this._greeting = "howdy";
         *          },
         *          // Override the base class's toString method
         *          toString: function() {
         *              // Call the super class's toString and prepend the Cowboy designation
         *              return "Cowboy " + this.sup();
         *          }
         *      });
         *
         *      // Example usage
         *      var john = new Person("John");
         *      var bill = new Cowboy("Bill");
         *      assertEquals("John says 'hi' to Cowboy Bill", john.greet(bill));
         *      assertEquals("Cowboy Bill says 'howdy' to John", bill.greet(john));
         * </pre>
         * <p/>
         * <b>Advanced Example</b>
         * <p/>
         * This example demonstrates static field and method inheritance.  Additionally, it uses the more expressive
         * (but also more verbose) function style of class body declaration to provide convenient access to the class's
         * static context.
         * <pre>
         *      // Declare a base class
         *      var Shape = bea.wlp.disc.Class.create(function(Shape) {
         *          // Base class initializer
         *          this.initialize = function(x, y) {
         *              // Initialize internal instance variables
         *              this.x = 0;
         *              this.y = 0;
         *              this.translate(x, y);
         *              this._name = "Shape";
         *          };
         *          // Declare some instance methods
         *          this.translate = function(x, y) {
         *              this.x += (x || Shape.ORIGIN.x);
         *              this.y += (y || Shape.ORIGIN.y);
         *          };
         *          this.draw = function(x, y) {
         *              this.translate(x, y);
         *              return (this._name + ": x=" + this.x + ", y=" + this.y);
         *          };
         *          // Declare a public static field
         *          Shape.ORIGIN = { x: 0, y: 0 };
         *      });
         *
         *      // Subclass Shape to create the Circle class
         *      var Circle = Shape.extend(function(Circle) {
         *          // Declare a private static variable
         *          var DEFAULT_RADIUS = 1;
         *          // Initializer
         *          this.initialize = function(x, y, r) {
         *              this.sup(x, y);
         *              this.r = (r || DEFAULT_RADIUS);
         *              this._name = "Circle";
         *          };
         *          // Override Shape's draw method
         *          this.draw = function(x, y) {
         *              // Indirectly reference the base class's ORIGIN static
         *              var containsOrigin = Circle.contains(this, Circle.ORIGIN);
         *              return (this.sup(x, y) + ", r=" + this.r + ", contains origin=" + containsOrigin);
         *          };
         *          // Static public method
         *          Circle.contains = function(circle, point) {
         *              var xd = point.x - circle.x;
         *              var yd = point.y - circle.y;
         *              return (Math.abs(Math.sqrt(xd * xd + yd * yd)) < circle.r);
         *          };
         *      });
         *
         *      var circle = new Circle();
         *      assertEquals("Circle: x=0, y=0, r=1, contains origin=true", circle.draw());
         *      circle = new Circle(2, 2, 2);
         *      assertEquals("Circle: x=4, y=4, r=2, contains origin=false", circle.draw(2, 2));
         * </pre>
         * <p/>
         * <b>Built-in Constructor Statics</b>
         * <p/>
         * The following static methods are automatically mixed in to class constructors created with
         * <code>Class.create</code>:
         * <p/>
         * <b>extend(subdecl)</b> {typeof subdecl == "object" or "function"; returns new class constructor}
         * <p/>
         * The <code>extend</code> static method supports subclassing as demonstrated in the above examples.
         * In the following example the <code>extend</code> method of the <code>SuperClass</code> constructor function
         * is used to declare the <code>SubClass</code> subclass of <code>SuperClass</code>.
         * <pre>
         *      var SuperClass = bea.wlp.disc.Class.create();
         *      var SubClass = SuperClass.extend();
         *      assertTrue(new SubClass() instanceof SuperClass);
         * </pre>
         * <b>inject(name, value)</b> {typeof name == "string" or "object" && typeof value == "object" or "function"}
         * <p/>
         * The <code>inject</code> static method is used to insert a method or field into a class declaration after
         * it has been created with <code>Class.create</code>.  The following example shows the use of
         * <code>inject</code> to do just that.
         * <pre>
         *      var Cowboy = bea.wlp.disc.Class.create({
         *          initialize: function(name) {
         *              this._name = name;
         *          }
         *      });
         *      Cowboy.inject("lasso", function(target) {
         *          return "Cowboy " + this._name + " lassoed a " + target + "!";
         *      });
         *      assertEquals("Cowboy Bill lassoed a goat!", new Cowboy("Bill").lasso("goat"));
         * </pre>
         * The <code>inject</code> method can also be used to batch inject the contents of a mixin.  As such, the
         * previous example can be rewritten like this:
         * <pre>
         *      var Cowboy = bea.wlp.disc.Class.create({
         *          initialize: function(name) {
         *              this._name = name;
         *          }
         *      });
         *      Cowboy.inject({ lasso: function(target) {
         *          return "Cowboy " + this._name + " lassoed a " + target + "!";
         *      }});
         *      assertEquals("Cowboy Bill lassoed a goat!", new Cowboy("Bill").lasso("goat"));
         * </pre>
         * Of course more than one item can be injected using the batch syntax:
         * <pre>
         *      var Cowboy = bea.wlp.disc.Class.create({
         *          initialize: function(name) {
         *              this._name = name;
         *          }
         *      });
         *      Cowboy.inject({
         *          lasso: function(target) {
         *              return "Cowboy " + this._name + " lassoed a " + target + "!";
         *          },
         *          shoot: function(target) {
         *              return "Cowboy " + this._name + " shot a " + target + "!";
         *          }
         *      });
         *      var bill = new Cowboy("Bill");
         *      assertEquals("Cowboy Bill lassoed a goat!", bill.lasso("goat"));
         *      assertEquals("Cowboy Bill shot a varmint!", bill.shoot("varmint"));
         * </pre>
         * <p/>
         * <b>remove(name)</b> {typeof name == "string" or "object"}
         * <p/>
         * The <code>remove</code> static method is used to remove methods or fields from a class declaration.  This
         * method is the counterpart to the <code>inject</code> method, though it does not necessarily have to operate
         * on members added to the class via injection.  The following example shows the <code>remove</code> method
         * removing members created through both declaration mechanisms.
         * <pre>
         *      var MyClass = bea.wlp.disc.Class.create({
         *          one: function() { return "remove me"; }
         *      });
         *      MyClass.inject("two", function() { return "remove me, too"; });
         *      var instance = new MyClass();
         *      assertNotNull(instance.one);
         *      assertNotNull(instance.two);
         *      // ... later ...
         *      MyClass.remove("one");
         *      MyClass.remove("two");
         *      instance = new MyClass();
         *      assertUndefined(instance.one);
         *      assertUndefined(instance.two);
         * </pre>
         * The <code>remove</code> method can also be used in batch mode just like <code>inject</code>:
         * <pre>
         *      var mixin = {
         *          one: function() { return "remove me"; },
         *          two: function() { return "remove me, too"; }
         *      }
         *      var MyClass = bea.wlp.disc.Class.create();
         *      MyClass.inject(mixin);
         *      var instance = new MyClass();
         *      assertNotNull(instance.one);
         *      assertNotNull(instance.two);
         *      // ... later ...
         *      MyClass.remove(mixin);
         *      instance = new MyClass();
         *      assertUndefined(instance.one);
         *      assertUndefined(instance.two);
         * </pre>
         * The design and implementation of this class framework was influenced by several sources, especially the
         * work of Ben Newman at <a href="http://seraph.im">http://seraph.im</a>.
         *
         * @object Class
         */
        $.Class = {
            /**
             * Create a new class.  New classes can be created with the <code>create</code> method in one of three
             * main ways:
             * <p/>
             * <b>...with a body expressed in object literal notation:</b>
             * <p/>
             * This approach creates a new class whose body is defined by the object literal passed as the value of the
             * <code>decl</code> argument.
             * <pre>
             *      var MyClass = bea.wlp.disc.Class.create({
             *          myMethod: function() {
             *              return "some stuff I did";
             *          }
             *      });
             *      var myInstance = new MyClass();
             *      assertNotNull(myInstance.myMethod);
             *      assertEquals("some stuff I did", myInstance.myMethod());
             * </pre>
             * <b>...with a body declared as a static initializer function:</b>
             * <p/>
             * This approach creates a new class whose body is defined by the function passed as the value of the
             * <code>decl</code> argument.  Note that the term "static initializer function" is used to describe this
             * function since it staticly initializes the class when constructing the class's actual
             * constructor function.  Any local variables over which closures are created in this context will act like
             * class static variables since they will be shared by all instances of the class.
             * <p/>
             * Also note the argument passed to the function used in this example: <code>MyClass</code>.  This argument
             * provides a static context for the class to which can be attached any public, inheritable, static members
             * that maybe be appropriate for the class being created.  The name of this argument should generally
             * match the name of the class being created, for clarity, but this convention is not enforced by the
             * <code>create</code> function.
             * <p/>
             * <pre>
             *      var MyClass = bea.wlp.disc.Class.create(function(MyClass) {
             *          this.myMethod = function() {
             *              return "some stuff I did";
             *          };
             *          MyClass.PI = 3.14;
             *      });
             *      var myInstance = new MyClass();
             *      assertNotNull(myInstance.myMethod);
             *      assertEquals("some stuff I did", myInstance.myMethod());
             *      assertEquals(3.14, MyClass.PI);
             * </pre>
             * <b>...or without a body:</b>
             * <p/>
             * This approach creates a bare new class without any unique members.  Members can be added after
             * declaration as needed using the newly created class constructor's <code>inject</code> method, as follows:
             * <pre>
             *      var MyClass = bea.wlp.disc.Class.create();
             *      MyClass.inject("myMethod", function() {
             *          return "some stuff I did";
             *      });
             *      var myInstance = new MyClass();
             *      assertNotNull(myInstance.myMethod);
             *      assertEquals("some stuff I did", myInstance.myMethod());
             * </pre>
             * This approach is discouraged in favor of the use of the more encapsulated forms described above but
             * may still be useful in some scenarios, such as the dynamic application of mixins.
             *
             * @method create
             * @param {object|function} [decl]
             *      An optional object or static initializer function to serve as the class's body
             * @param {object} [_statics]
             *      A object containing assignable statics; to be used internally only
             * @returns {function}
             *      A constructor function for the new created class
             */
            create: function(decl, _statics) {
                var ctor = $._Object.mixin(function() {
                    if (typeof this.initialize == "function") {
                        this.initialize.apply(this, arguments);
                    }
                }, _statics);
                decl = decl || { };
                ctor.prototype = (typeof decl == "function") ? new decl(ctor) : decl;
                $.Class._inherit(ctor.prototype, decl.prototype || { });
                ctor.prototype.constructor = ctor;
                $._Object.mixin(ctor, $.Class._mixin);
                return ctor;
            },

            /*
             * Used for internal class declaration only.
             */
            _inherit: function(self, parent) {
                $._Object.each(self,
                    function(p) { // action
                        self[p] = $.Class._wrap(self[p], p, parent);
                    },
                    function(p) { // when
                        return (self[p] !== Object.prototype[p] && self[p] !== parent[p] && typeof self[p] == "function");
                    }
                );
            },

            /*
             * Used for internal class declaration only.
             */
            _wrap: function(override, name, parent) {
                var supRegExp = /\Wsup\s*(\(|[\'\"\[\.][^=]*?\()/;
                return supRegExp.test(override) ? function() {
                    var saved = this.sup;
                    this.sup = parent[name];
                    try {
                        return override.apply(this, arguments);
                    }
                    finally {
                        this.sup = saved;
                    }
                } : override;
            },

            /*
             * Used for internal class declaration only.
             */
            _mixin: {
                /*
                 * Documentation included in class doc.
                 */
                extend: function(subdecl) {
                    if (typeof subdecl != "function") {
                        var obj = subdecl;
                        subdecl = function() {
                            $._Object.mixin(this, obj);
                        }
                    }
                    subdecl.prototype = this.prototype;
                    return $.Class.create(subdecl, this);
                },

                /*
                 * Documentation included in class doc.
                 */
                inject: function(nameOrSrc, value) {
                    if (typeof nameOrSrc == "string") {
                        var old = this.prototype[nameOrSrc];
                        this.prototype[nameOrSrc] = $.Class._wrap(value, "prop", { prop: old });
                        this.prototype[nameOrSrc]._base = old;
                    }
                    else {
                        var self = this;
                        $._Object.each(nameOrSrc,
                            function(prop) { // action
                                self.inject(prop, nameOrSrc[prop]);
                            },
                            function(prop) { // when
                                return (self[prop] != nameOrSrc[prop]);
                            }
                        );
                    }
                },

                /*
                 * Documentation included in class doc.
                 */
                remove: function(nameOrSrc) {
                    if (typeof nameOrSrc == "string") {
                        var old = this.prototype[nameOrSrc]._base;
                        delete this.prototype[nameOrSrc];
                        if (old && old !== this.prototype[nameOrSrc]) {
                            this.prototype[nameOrSrc] = old;
                        }
                    }
                    else {
                        var self = this;
                        $._Object.each(nameOrSrc, function(prop) {
                            self.remove(prop);
                        });
                    }
                }
            }
        }
        
        /**
         * This is a special-purpose abstraction over the native XMLHttpRequest object, servicing both
         * synchronous and asynchronous request types for various internal Disc needs.
         *
         * @class _Request
         */
        $._Request = $.Class.create(function(_Request) {
            // An internal, static set of possible XHR sources
            var sources = [
                function() { return new XMLHttpRequest(); },
                function() { return new ActiveXObject("Msxml2.XMLHTTP"); },
                function() { return new ActiveXObject("Microsoft.XMLHTTP"); },
                function() { return new ActiveXObject("Msxml2.XMLHTTP.4.0"); }
            ];
            // An index into the sources array for environment-optimized traversal
            var index = (location.protocol == "file:" && window.ActiveXObject ? [1, 0, 2, 3] : [0, 1, 2, 3]);

            /**
             * Initializes this instance.
             *
             * @method initialize
             * @param {string} method
             *      The method to use when opening the underlying XHR connection
             * @param {string} uri
             *      The URI to use when opening the underlying XHR connection
             * @param {boolean} [async]
             *      Whether or not the underlying XHR connection should be run asynchronously; defaults to false
             */
            this.initialize = function(method, uri, async) {
                for (var i = 0; i < index.length; i++) {
                    try {
                        this._xhr = sources[index[i]]();
                        
                        // successfully invoked the ctor for the XHR source, so replace the index array 
                        // with an array containing the single index that worked
                        index.splice(0, index.length, index[i]);
                        break;
                    }
                    catch (ignore) {
                        // the ctor for that XHR source did not work so we will try another one 
                    }
                }
                this._xhr.open(method, uri, async);
                this._async = async;
            }
            
            /**
             * Sends the underlying XHR request.
             *
             * @method send
             * @param {function} callback
             *      The callback to invoke when a successful response is received
             * @param {function} error
             *      The callback to invoke when an error response is received
             * @param {string} [data]
             *      Post data to be sent with the request, if any
             */
            this.send = function(callback, error, data) {
                var called, doError, xhr = this._xhr;
                function update() {
                    if (xhr.readyState == 4) {
                        called = true;
                        var s = xhr.status;
                        if (((s == 0 || s == undefined) && location.protocol != "file:")
                            || (s > 1 && s < 200) || (s >= 300 && s != 304)) {
                            if (error) {
                                this._async ? error(xhr) : doError = true;
                            }
                        }
                        else if (callback) {
                            callback.call(xhr, xhr);
                        }
                    }
                }
                xhr.onreadystatechange = update;
                try {
                    xhr.send(data || null);
                }
                catch (e) {
                    error(xhr, e);
                }
                if (!this._async) {
                    if (!called) {
                        update();
                    }
                    if (error && doError) {
                        error(xhr);
                    }
                }
            }
        });

        /**
         * The <code>Resource</code> class provides a simple OO layer on top of simple synchronous XMLHttpRequests
         * (XHRs). Instantiation of this class prepares the instance to make its request, but does not yet execute it.
         * Calling either <code>getText</code> or <code>evaluate</code> will cause the request to be performed.
         * <p/>
         * Be careful to use this class sparingly, as sync XHR interactions will block <i>all</i> interaction in most
         * browsers, rendering them useless until the request completes.  Additionally, data fetched through such
         * requests will not typically be cached through standard browser caching mechanisms.  As such, sync XHR
         * requests should typically be reserved for development and resource loading fallback scenarios.
         *
         * @class _Resource
         */
        $._Resource = $.Class.create(function() {
            /**
             * Initializes a new instance with the specified URI.
             *
             * @method initialize
             * @param {string} uri
             *      A non-null URI from which a resource should be loaded
             */
            this.initialize = function(uri) {
                this._uri = uri;
            }

            /**
             * The first time this method is called for a given <code>Resource</code> instance, its underlying sync
             * XHR is executed; subsequent requests return the locally cached result of the first operation.  The result
             * of calling getText is to return the response from the sync XHR operation once complete.
             *
             * @method getText
             * @returns {string}
             *      The text response from the request, if successful
             * @throws {Error}
             *      Thrown when the sync XHR request completes without a successful response code, or the underlying
             *      XHR send operation itself throws an exception; <code>status</code>, <code>statusText</code>,
             *      and <code>responseText</code> fields are added to the thrown error when available
             */
            this.getText = function() {
                if (!this._text) {
                    var self = this;
                    new $._Request("GET", this._uri, false).send(
                        // success
                        function(xhr) {
                            if (xhr.readyState == 4) {
                                self._text = xhr.responseText;
                            }
                        },
                        // error
                        function(xhr, e) {
                            var error;
                            if (e) {
                                error = new Error((e && e.message) || "Unspecified error");
                                error.cause = e;
                            }
                            else {
                                error = new Error(xhr.status + ": " + self._uri);
                            }
                            error.status = xhr.status;
                            error.statusText = xhr.statusText;
                            error.responseText = xhr.responseText;
                            throw error;
                        }
                    );
                }
                return this._text;
            }

            /**
             * A convenience method to call and/or evaluate the result of <code>getText</code>.
             *
             * @method evaluate
             * @returns {object}
             *      The result of the evaluation, if any
             */
            this.evaluate = function() {
                if (this.getText()) {
                    return eval(this._text);
                }
            }
        });

        /**
         * The <code>Catalog</code> class is an interface to a module's localization subsystem.  A Catalog represents
         * a set of localized data and is accessed by module-based code to convert default-text strings into
         * localized versions of those strings when such localized versions are available.
         *
         * @class Catalog
         */
        $.Catalog = $.Class.create(function(Catalog) {
            /**
             * Initializes a new <code>Catalog</code> instance.
             *
             * @private
             * @method initialize
             * @param {bea.wlp.disc.Module} module
             *      The module to which this catalog instance belongs
             */
            this.initialize = function(module) {
                this._module = module;
                this._lookup = { };
            }

            /**
             * This method loads a module's catalog with the localization data specified in the l10nData parameter.
             * The l10nData parameter should be an object mapping default message values to localized message values,
             * as demonstrated in the following example:
             * <pre>
             *      $MyModule.$meta.getCatalog().load({
             *          "default message value" : "localized message value",
             *          // ...
             *      });
             * </pre>
             * A missing mapping in the l10n data object (or a mapping with a "falsy" value) will result in the use of
             * the default message (the key) for a given lookup.
             * <p/>
             * Explicit catalog localization will generally be used for purposes of l10n catalog aggregation, but may
             * also be used to explicitly set or override l10n data on a per-catalog basis, as appropriate.
             *
             * @method load
             * @param {object} l10nData
             *      A mapping of default message values to localized message values
             */
            this.load = function(l10nData) {
                this._lookup = l10nData || { };
            }

            /**
             * Returns a localized version of the supplied message, if such a localization can be found.  The search
             * for a localized form of the message starts with this catalog instance's local l10n data, if any.  If
             * no match is found, the search then asks its associated module's parent's catalog to localize the value,
             * when possible.  The search continues on up the hierarchy until either a localized value is found or
             * the top of the module hierarchy is reached.
             * <p/>
             * This method also provides basic message formatting capabilities.  Message formatting can be achieved
             * by combining object tokens (matching the regexp: <i>\{\d\}</i>) in a message with additional var arg
             * parameters.  For example:
             * <pre>
             *      var msg = myCatalog.localize("Some default message value with two args: {0} and {1}", "one", "two");
             * </pre>
             *
             * @method localize
             * @param {string} msg
             *      A default message value to attempt to localize
             * @param [varargs]
             *      Any number of additional parameters to the method to be used as values for message formatting
             * @returns {string}
             *      A formatted version of the message, localized if matching l10n data was found
             */
            this.localize = function(msg) {
                return (msg ? Catalog._L(this).apply(this, arguments) : null);
            }

            /**
             * Resolves an individual message string, starting with the current catalog and walking up the module
             * hierarchy until resolution occurs or the top of the module hierarchy is reached.
             *
             * @method _resolve
             * @param {string} msg
             *      The message for which a localized version should be resolved
             * @returns {string}
             *      The resolved string or the original string if no localized version was found
             */
            this._resolve = function(msg) {
                return (this._lookup[msg] || (function(self) {
                    var parent = self._module.$meta.getParent();
                    var parentCat = parent.$meta && parent.$meta.getCatalog();
                    return (parentCat && parentCat._resolve(msg));
                })(this) || msg);
            }

            /**
             * A convenience function that generates a partially-applied function to do catalog-specific localization.
             * The product of this function is passed to module declaration and initializer routines, and supports the
             * <code>localize</code> method of all <code>Catalog</code> instances.
             *
             * @static
             * @method _L
             * @param {bea.wlp.disc.Catalog} catalog
             *      The catalog for which a localizer function should be produced
             * @returns {function}
             *      A catalog-specific localizer function
             */
            Catalog._L = function(catalog) {
                return function() {
                    var args = arguments;
                    return (catalog._resolve(args[0]) || "").replace(/\{(\d)\}/g,
                        function(str, index) {
                            // str is the string that matched the regEx pattern.
                            // index is the string that matched the parenthesized subexpression in 
                            // the regEx for the String.replace() call.
                            index = +index + 1;
                            return (args.length > index ? new String(args[index]) : str);
                        }
                    );
                }
            }
        });

        /**
         * The <code>Module</code> class comprises one of the most central facilities of the Disc core API: it provides
         * a namespacing infrastructure for managing modularized code in the global execution context.  While this class
         * facilitates the creation of and interaction with namespace objects (modules), it does not impose a fixed
         * structure upon them.
         * <p/>
         * By convention Oracle WebLogic Portal owns anything in the <code>bea.wlp.*</code> namespace; any modifications
         * to that namespace (and/or the constituents thereof) which are not supported through public APIs may result in
         * undefined and unsupported behavior.
         * <p/>
         * Modules are created via calls to the static <code>Module.create</code> function.  Modules should be named to
         * follow typical Java package naming conventions, that is, a '.' delimited list of valid JavaScript
         * identifiers, typically composed of all lower-case letters.
         * <p/>
         * Each module contains, at a minimum, a <code>$meta</code> field that provides a number of basic operations
         * around the module itself, including accessors for this module's name, parent module, catalog, and an
         * indication of whether or not the module has been formally defined (or if, instead, it exists purely for
         * namepsacing purposes).
         * <p/>
         * Modules support loading via either static script tag references (HTML) or dynamic loading via sync-XHR.  The
         * former should be used to deliver module source (or compiled module bundles) in production-mode scenarios.
         * The latter can be a convenient tool for iteratively developing Disc-based JavaScript.  Production-mode
         * construction and deployment of module bundles is performed automatically for well-known Disc APIs; for
         * custom Disc APIs, such construction and deployment is currently left as an exercise to the API developer.
         * Dynamic module loading is driven through the use of the static <code>Module.use</code> method.
         * <p/>
         * Dynamically loaded modules assume a specific file layout on the resource server.  Each individual
         * name-component of the overall module name (i.e. each identifier in the module name chain, delimited with '.'
         * characters) is assumed to be a directory.  The filesystem hierarchy of these directories must match the
         * hierarchy specified by the overall module name.  Each directory with a non-trivial module (that is, any
         * bottom-level module for which one may call <code>Module.use</code>) must contain a file named
         * <code>module.js</code>, containing an appropriate call to <code>Module.create</code>.  Any module facets to
         * be contributed to the dynamically loaded module must live in the module's directory, peer to it's
         * <code>module.js</code> file.  For example, the hypothetical module named "my.fancy.api" might look like
         * this on the filesystem:
         * <pre>
         *      + my
         *      |
         *      ----+ fancy
         *          |
         *          ----+ api
         *              |
         *              ----  module.js
         *              |
         *              ----  FacetOne.js
         *              |
         *              ----  FacetTwo.js
         * </pre>
         * ...where the module declaration in <code>module.js</code> specifies its facet inclusions as
         * <code>include: ["FacetOne", "FacetTwo"]</code> (see <code>Module.create</code> and
         * <code>Module.contribute</code> for module and facet declaration details).  This structure will then be
         * synchronously loaded and realized on-demand, via the <code>Module.use</code> invocation.
         * <p/>
         * Lastly, each <code>Module</code> instance has an associated <code>Catalog</code> instance used for the
         * organization of any given module's localizable messages.  See the documentation for the <code>Catalog</code>
         * class for more information.
         * <p/>
         * <b>Module instantiation should only be performed internally; use <code>bea.wlp.disc.Module.create</code> to
         * explicitly create a new module.  Directly contructing modules via the <code>new</code> operator is not
         * supported.</b>
         *
         * @class Module
         * @param {string} _path
         *      The name of this module in module path notation; e.g. <code>bea.wlp.disc</code>
         * @see bea.wlp.disc.Module.create
         * @see bea.wlp.disc.Module.use
         * @see bea.wlp.disc.Catalog
         */
        $.Module = function(_path) {
            var self = this;

            /**
             * @field {object}
             *      This field is a special member of all <code>Module</code> instances that provides some simple
             *      metadata getters.
             */
            this.$meta = {
                _name: _path,
                _facets: [],
                _initializers: [],
                _includes: $._Object.map(),
                _tryRealized: function() {
                    if (this._includes.size() == 0 && this._initializers.length > 0) {
                        var init;
                        while ((init = this._initializers.shift())) {
                            init.call(self, self, $.Catalog._L(this._catalog));
                        }
                    }
                },

                /**
                 * Returns the name of the current module. e.g. <i>bea.wlp.disc.$meta.getName() == "bea.wlp.disc"</i>.
                 * If passed the optional <code>sub</code> param, sub is appended to the module name returned in
                 * the no-args version; e.g. <i>bea.wlp.disc.$meta.getName("foo") == "bea.wlp.disc.foo"</i>.
                 * <p/>
                 * For example:
                 * <pre>
                 *      bea.wlp.disc.Module.create("ns.mod", { });
                 *      assertEquals("ns.mod", ns.mod.$meta.getName());
                 *      assertEquals("ns.mod.sub", ns.mod.$meta.getName("sub"));
                 * </pre>
                 *
                 * @method $meta.getName
                 * @param {string} [sub]
                 *      An additional string to append to the normal result of <code>getName()</code>, if any
                 * @returns {string}
                 *      The module's name, with the <code>sub</code> param appended if specified
                 */
                getName: function(sub) {
                    var prefix = this._name + ".";
                    return (sub ? (sub.indexOf(prefix) === 0 ? sub : prefix + sub) : this._name);
                },

                /**
                 * Returns the module's parent object.  This object will typically be an instance of
                 * <code>bea.wlp.disc.Module</code>, but may be another object type or the global
                 * <code>window</code> object if called on a root module, such as <code>bea</code>.  Calls to
                 * this method should always check the type of the returned object before making assumptions
                 * about that object's properties.
                 * <p/>
                 * For example:
                 * <pre>
                 *      bea.wlp.disc.Module.create("ns.mod", { });
                 *      assertEquals(ns, ns.mod.$meta.getParent());
                 *      assertEquals(window, ns.$meta.getParent());
                 * </pre>
                 *
                 * @method $meta.getParent
                 * @returns {bea.wlp.disc.Module|window|object}
                 *      The value returned is one of <code>bea.wlp.disc.Module<code>, the global <code>window</code>
                 *      object, or another object; the exact result depends on the environment in which the module
                 *      was created
                 */
                getParent: function() {
                    return this._parent;
                },

                /**
                 * Returns this module's <code>bea.wlp.disc.Catalog</code> instance.
                 *
                 * @method $meta.getCatalog
                 * @returns {bea.wlp.disc.Catalog}
                 *      This module's catalog; not null
                 */
                getCatalog: function() {
                    return this._catalog;
                },

                /**
                 * Indicates whether or not this module has been explicitly declared, or is instead a placeholder
                 * module existing (so far) purely for namepsacing.
                 * <p/>
                 * For example:
                 * <pre>
                 *      bea.wlp.disc.Module.create("ns.mod", { });
                 *      assertFalse(ns.$meta.isDeclared());
                 *      assertTrue(ns.mod.$meta.isDeclared());
                 * </pre>
                 *
                 * @method $meta.isDeclared
                 * @returns {boolean}
                 *      True if the module has been explicitly declared; false otherwise
                 */
                isDeclared: function() {
                    return !!this._isDeclared;
                }
            }
            
			/**
	         * This method returns this module's name as a string.
	         *
	         * @method toString
	         * @returns {string}
	         *      The name of the module on which this method was called
	         */
	        this.toString = function() {
	            return this.$meta.getName();
	        }
        }        

        // The set of all known modules
        var registry = { };

        /**
         * Attempts to locate and return a module or an object child of a module.  The supplied name parameter
         * should be either the name of a module or the name of a module plus the name of an object declared on
         * that module instance (if it exists), delimited with a '.'.  If the module or object is found at the
         * specified location, it is returned.
         * <p/>
         * For example:
         * <pre>
         *      bea.wlp.disc.Module.create("ns.mod", {
         *          declare: function($, L) {
         *              $.AnObject = { data: "stuff" };
         *          }
         *      });
         *      assertTrue(!!bea.wlp.disc.Module.find("ns"));
         *      assertTrue(!!bea.wlp.disc.Module.find("ns.mod"));
         *      assertTrue(!!bea.wlp.disc.Module.find("ns.mod.AnObject"));
         *      assertEquals("stuff", bea.wlp.disc.Module.find("ns.mod.AnObject").data);
         * </pre>
         * Note that any module instance returned by this method is <i>not</i> guaranteed to be declared.  Module
         * instances may exist as simple namespace constructs without having their own explicit declarations, yet
         * this method does not distinguish between the two.
         * <p/>
         * Use <code>bea.wlp.disc.Module.isDefined</code> to determine if a module exists and has been declared.
         * <p/>
         * Unlike <code>bea.wlp.disc.Module.use</code>, this method does not attempt to load a module if it was not
         * found.
         *
         * @static
         * @method find
         * @param {string} name
         *      The name of the module or object to find
         * @returns {bea.wlp.disc.Module|object}
         *      The module or object, if found
         */
        $.Module.find = function(name) {
            var found = registry[name];
            if (!found) {
                var parts = name.split(".");
                
                // for the name "ns.mod.AnObject", look for the "ns.mod" module:
                found = registry[parts.slice(0, -1).join(".")];
                
                // if found the "ns.mod" module then check it for AnObject like this: ns.mod["AnObject"]  
                found = (found && found[parts[parts.length - 1]]);
            }
            return found;
        }

        /**
         * Determines whether or not the specified module both exists and has been declared.  If a valid module
         * reference is passed in, this is the same as calling <code>myModule.$meta.isDeclared()</code>.  If a string
         * name is passed, this method attempts to find the module, and then, if it exists, returns its declaration
         * status, as above.
         *
         * @static
         * @method isDefined
         * @param {string|bea.wlp.disc.Module} nameOrModule
         *      Either a module or module name to determine
         * @returns {boolean}
         *      True if the module exists and is declared; false otherwise
         */
        $.Module.isDefined = function(nameOrModule) {
            var module = (typeof nameOrModule == "string" ? registry[nameOrModule] : nameOrModule);
            return !!(module && module.$meta && module.$meta._isDeclared);
        }

        /**
         * Creates a new module.  Attempting to create a module once it already exists will simply result in the
         * existing module being returned.  This means that the first attempt to declare a module will win, whether or
         * not subsequent attempts specify the same module contents as the successful call to
         * <code>Module.create</code>.
         * <p/>
         * Module names should typically be defined as a sequence of valid JavaScript identifiers delimited with the '.'
         * character.  Each '.' in the name specifies a tier in the overall module hierarchy, with each distinct
         * identifier representing a distinct module.  Any parent modules not already in existence will be created on
         * the fly to support the bottom-level module in the specified name passed to this method.  Any modules so
         * created will be valid Disc modules, but will not be technically "declared" until a subsequent call to
         * <code>Module.create</code> is made with that module as the bottom-level module in the overall module name
         * (which may never occur).  Such modules exist in support of the namespacing hierarchy, but do not
         * necessarily provide any meaningful APIs in and of themselves.
         * <p/>
         * The <code>decl</code> param passed to this method must be an object supplying one or more of the following
         * fields:
         * <ul>
         *      <li><b>require</b>:
         *          An array of strings, where each string is the name of another module on which this module depends;
         *          all modules (including the current module) listed here are guaranteed to be fully realized before
         *          this module's initializer is called; modules are considered to be fully realized once all of their
         *          constituents have been materialized</li>
         *      <li><b>include</b>:
         *          An array of strings, where each string is the name of a module facet that should be contributed to
         *          this module; module facets are contained in supplementary files located peer to this module's
         *          <code>module.js</code> file named as "<i>&lt;facet name&gt;</i>.js"; see the
         *          <code>Module.contribute</code> static method for more information on facet contribution to a
         *          module</li>
         *      <li><b>declare</b>:
         *          Either an object or a constructor function used to declare the constituent members of a module;
         *          this param is much like the argument to <code>Class.create</code> in form and function, except
         *          that, if specified as a function, accepts exactly two (optional) arguments, typically named
         *          <code>$</code> and <code>L</code>; the <code>$</code> argument is the module's self-reference (a
         *          shortcut for the "this" reference), and the <code>L</code> argument is a convenience shortcut
         *          function for this module's catalog's <code>localize</code> function
         *          (alternately addressable as <code>$.$meta.getCatalog().localize</code>)</li>
         *      <li><b>initialize</b>:
         *          An initializer function that accepts the same arguments as the function form of the
         *          <code>declare</code> field; called once this module and any modules upon which this module depends
         *          (as specified in the <code>require</code> field) are fully realized</li>
         * </ul>
         *
         * @static
         * @method create
         * @param {string} name
         *      The name of the new module
         * @param {object} decl
         *      A declaration object, as defined above
         * @returns {bea.wlp.disc.Module}
         *      The module associated with the indicated module name
         */
        $.Module.create = function(name, decl) {
            var module = $.Module._create(name);
            if (!module.$meta._isDeclared) {
                module.$meta._catalog = new $.Catalog(module);
                module.$meta._isDeclared = true;
                
                // include interceptor
                var include = $.Module._include;
                $.Module._include = function(facetName, host) {
                    module.$meta._includes.put(facetName, true);
                    arguments.callee.orig.call($.Module, facetName, (host || module));
                }
                $.Module._include.orig = (include.orig || include);
                
                // initializer interceptor
                var initializer = $.Module._initializer;
                $.Module._initializer = function(init, _module) {
                    arguments.callee.orig.call($.Module, init, (_module || module));
                }
                $.Module._initializer.orig = (initializer.orig || initializer);
                
                // use interceptor
                var use = $.Module.use;
                var modules;
                if (!use.orig) {
                    modules = $._Object.map();
                    modules.put(name, module);
                    $.Module.use = function(name) {
                    
                        // add it to the modules map so that we can initialize each module
                        // in the chain after they are all created
                        modules.put(name, arguments.callee.orig.call($.Module, name));
                        return modules.get(name);
                    };
                    $.Module.use.orig = use;
                }
                
                try {
                    $.Module._realize(module, decl);
                }
                finally {
                    $.Module._include = include;
                    $.Module._initializer = initializer;
                    
                    // after the module and all modules it depends on are fully realized, we initialize all created modules 
                    if (!use.orig) {
                        $.Module.use = use;
                        modules.each(function(key, value) {
                            value.$meta._tryRealized();
                        });
                    }
                }
            }
            return module;
        }

        /**
         * This method is used to contribute module facets to an existing module.  Module facets are a mechanism that
         * allows the declaration of a particular module to be easily broken up into multiple files, so that a single
         * module need not be defined in a single place.  This method is then employed to apply partial module
         * declarations made by facets to the module with which they are associated.
         * <p/>
         * The <code>decl</code> parameter supplied to this method is an initializer function exactly like those
         * used to create modules.  It can receive the same parameters as module initializers (i.e. <code>$</code>
         * and <code>L</code>, which respectively refer to the target module's self reference and catalog's
         * localization function) and whose <code>this</code> reference also points to the target module.  In
         * essence this function provides an additional module initializer to use in the context in which the
         * call to <code>contribute</code> is made.
         *
         * @static
         * @method contribute
         * @param {object|function} decl
         *      A module initializer object or function used to supply additional content for the module with which it
         *      is associated
         * @param {string} [_moduleName]
         *      The name of the target module; internal use only
         * @param {string} [_facetName]
         *      The name of the facet being contributed; internal use only
         * @see bea.wlp.disc.Module.create
         */
        $.Module.contribute = function(decl, _moduleName, _facetName) {
            var module = $.Module.use(_moduleName);
            if (!module.$meta._facets[_facetName]) {
                delete decl.include;
                $.Module._realize(module, decl);
                module.$meta._facets[_facetName] = true;
            }
        }

        /**
         * Attempts to return a module instance by name; if the requested module does not yet exist or exists but
         * has not yet been created, this method attempts to load the module from this script's host server,
         * according to the setting(s) in <code>bea.wlp.disc.Config</code>.  If you wish to simply check whether or
         * not a module exists, consider using <code>bea.wlp.disc.Module.find</code> or
         * <code>bea.wlp.disc.Module.isDefined</code>.
         * <pre>
         *      // bea.wlp.disc is created
         *      var $Disc = bea.wlp.disc.Module.use("bea.wlp.disc");
         *      assertTrue(!!$Disc);
         *      assertTrue($Disc.$meta.isDeclared());
         *      // my.module not yet created
         *      var $MyModule = bea.wlp.disc.Module.use("my.module")
         *      assertTrue(!!$MyModule);
         *      assertFalse($MyModule.$meta.isDeclared());
         * </pre>
         * This method always returns a module instance whether or not the instance could be resolved and
         * loaded/created.  That is, if no declaration for the module can be resolved, a module is still created and
         * returned.  If you are unsure that a module will be resolved when calling this method, you can check to see
         * whether or not it was loaded/created by checking its <code>$meta.isDeclared()</code> value.
         *
         * @static
         * @method use
         * @param {string} name
         *      The name of the module to use
         * @returns {bea.wlp.disc.Module}
         *      A module for the requested name, whether or not a declaration for the module was created
         */
        $.Module.use = function(name) {
            if (!$.Module.isDefined(name)) {
                $.Module._load(name);
            }
            return (registry[name] || $.Module._create(name));
        }

        /**
         * This method is used to incorporate "facets" into a module.  Module facets are blocks of code defined
         * outside of a module's main declaration statement that contribute members to a module that explicitly
         * includes them with this method.  This allows the constituent code for a given module to be broken up
         * amongst several files.
         * <p/>
         * For example:
         * <pre>
         *      // ns/mod/module.js
         *      bea.wlp.disc.Module.create("ns.mod", {
         *          include: ["SomeFacet"]
         *      });
         *      // ns/mod/SomeFacet.js
         *      bea.wlp.disc.Module.contribute({
         *          declare: function($, L) {
         *              $.SomeFacet = {
         *                  // ...
         *              }
         *          }
         *      });
         *      assertTrue(!!ns.mod.SomeFacet);
         * </pre>
         * The above example code defines an object in the <i>ns.mod</i> module called <code>SomeFacet</code>.
         * Notice that the declaration for the ns/mod/module.js module file explicitly includes the facet, which
         * in turn then contributes its own declaration to that module.
         * <p/>
         * Module facets should always be defined as logical include/contribute pairs.  Since the facet system is
         * designed to be used to distribute module declaration code across multiple files, the use of the include
         * and contribute APIs have been designed to this end.  They are however, usable also in an aggregated
         * script as well, but a little more effort is used to ensure that the proper contextual information
         * is available to these methods in the aggregated scenario.
         * <p/>
         * Script aggregators may use the extended form of this method (explicitly specifying the optional params)
         * within calls to <code>bea.wlp.disc.Module._bundle</code> to successfully inline module facet decarations
         * made with the <code>bea.wlp.disc.Module.contribute</code> method.
         * <p/>
         * For example:
         * <pre>
         *      // aggregated js file
         *      bea.wlp.disc.Module._bundle(function() {
         *          bea.wlp.disc.Module.create("ns.mod", {
         *              include: ["SomeFacet"]
         *          });
         *          bea.wlp.disc.Module._include("SomeFacet", "ns.mod", function() {
         *              bea.wlp.disc.Module.contribute({
         *                  declare: function($, L) {
         *                      $.SomeFacet = {
         *                          // ...
         *                      }
         *                  }
         *              });
         *          });
         *      });
         *      // ...
         *      assertTrue(!!ns.mod.SomeFacet);
         * </pre>
         * Notice the three param version of the call to <code>bea.wlp.disc.Module._include</code> in this example.
         * This call dynamically provides contextual information to the <code>bea.wlp.disc.Module.contribute</code>
         * method.
         * <p/>
         * Note that it is expected that module aggregation will be performed in an automated fashion; the use of
         * the contextual arguments to this method (host and decl) are intended for use only by such aggregators
         * and are not necessary (or typically even useful) for use within module decarations (wherein the
         * necessary data is provided automatically by the Disc module system).
         * <p/>
         * This method should only be called from module declaration initializer functions or surrounding a
         * <code>bea.wlp.disc.Module.contribute</code> call inside a call to <code>bea.wlp.disc.Module._bundle</code>
         * (i.e. when aggregating modules and facets into a single bundle file; see {bea.wlp.disc.Module._bundle}).
         * <p/>
         * This method is the private implementation behind the "include" field of a module declaration, and should not
         * be used directly.  Module loading and bundling code uses this internally to perform any necessary facet
         * inclusions.
         *
         * @static
         * @method _include
         * @param {string} facetName
         *      The name of the facet to include
         * @param {bea.wlp.disc.Module|string} [host]
         *      The name of the host module to which the included facet will be contributed; only used when this function 
         *      is used as a wrapper for <code>bea.wlp.disc.Module.contribute</code> statements in an aggregated context;
         *      may be either a module instance or a module name
         * @param {function} [decl]
         *      A wrapper function acting as a declaration harness (to allow delayed facet declarations in
         *      aggregated contexts); only used when this function is used as a wrapper for 
         *      <code>bea.wlp.disc.Module.contribute</code> statements in an aggregated context;
         *      this function should simply contain a call to<code>bea.wlp.disc.Module.contribute</code>
         */
        $.Module._include = function(facetName, host, decl) {
            host = (typeof host == "string" ? $.Module.use(host) : host);
            if (!host.$meta._facets[facetName]) {
            
                // contribute interceptor
                var contribute = $.Module.contribute;
                $.Module.contribute = function(decl, moduleName, fName) {
                    var name = (moduleName || host.$meta._name);
                    contribute.call($.Module, decl, name, (fName || facetName));
                }
                try {
                    decl ? decl() : $.Module._load(host.$meta._name, facetName);
                }
                finally {
                    $.Module.contribute = contribute;
                }
                host.$meta._includes.remove(facetName);
            }
        }

        /**
         * This method is the private implementation behind the "initialize" field of a module declaration and is only
         * intended for use by the module declaration subsystem (invoked by <code>Module.create</code>).  This exists
         * largely due to historical incarnations of the core Disc API and could be optimized out in future iterations
         * of the core module implementation, by streamlining the module bundling and creation methods.
         *
         * @static
         * @method _initializer
         * @param {function} initializer
         *      The actual initializer function to be invoked when a module and all of its declared dependencies have
         *      been realized
         * @param {bea.wlp.disc.Module} _module
         *      The module with which this initalizer is associated
         */
        $.Module._initializer = function(initializer, _module) {
            if (typeof initializer == "function") {
                _module.$meta._initializers.push(initializer);
            }
        }

        /**
         * This method is used to aggregate multiple modules and facets into a single physical file.  When multiple
         * module and facet files are concatenated together to form an aggregate script, the entire body from
         * those contatenated declarations should be contained in a single invocation of this method, as shown
         * below.  When evaluated this method will orchestrate the assembly of all modules and facets while
         * preventing premature XHR invocations for unresolved components.  Once all locally defined components
         * have been resolved, any still undefined components will be loaded (if possible) via XHR invocations.
         * <p/>
         * For example, an aggregated file assembling multiple modules and facets might look like this (the funky
         * ordering in this example is intentional to show that ordering is not important):
         * <pre>
         *      // aggregated js file
         *      bea.wlp.disc.Module._bundle(function() {
         *          bea.wlp.disc.Module._include("FacetA", "ns.mod1", function() {
         *              bea.wlp.disc.Module.contribute({
         *                  declare: function($, L) {
         *                       $.FacetA = { }
         *                  }
         *              });
         *          });
         *          bea.wlp.disc.Module.create("ns.mod1", {
         *              require: ["ns.mod2"],
         *              include: ["FacetA", "FacetB"]
         *          });
         *          bea.wlp.disc.Module._include("FacetB", "ns.mod1", function() {
         *              bea.wlp.disc.Module.contribute({
         *                  declare: function($, L) {
         *                       $.FacetB = { }
         *                  }
         *              });
         *          });
         *          bea.wlp.disc.Module._include("FacetC", "ns.mod2", function() {
         *              bea.wlp.disc.Module.contribute({
         *                  declare: function($, L) {
         *                       $.FacetC = { }
         *                  }
         *              });
         *          });
         *          bea.wlp.disc.Module.create("ns.mod2", {
         *              require: ["ns.mod1"],
         *              include: ["FacetC"]
         *          });
         *          bea.wlp.disc.Module.create("ns", {
         *              declare: function($, L) {
         *                  $.AnObject = { }
         *              }
         *          });
         *      });
         *      // ...
         *      assertTrue(!!ns.mod1.FacetA);
         *      assertTrue(!!ns.mod1.FacetB);
         *      assertTrue(!!ns.mod2.FacetC);
         *      assertTrue(!!ns.AnObject);
         * </pre>
         * As shown above and in the aggregation example for <code>bea.wlp.disc.Module._include</code>, additional
         * wrapping treatment is used around calls to <code>bea.wlp.disc.Module.contribute</code> for aggregated
         * assembly via this method to be successful.
         * <p/>
         * Note that order of inclusion for module and facet components in the concatenation is unimportant in this
         * model -- this method coordinates assembly as needed.
         * <p/>
         * It should also be noted that aggregation scenarios require that module and facet initializers do not
         * rely on modules being fully created and/or populated with facet contributions <i>during</i> the
         * execution of the module initializer.  The module constituents set up by such initializers may obviously
         * rely on such entities, but to reiterate, the module initializer code themselves should not do so.  The
         * one exception to this rule, of course, is that all Disc-derived code can rely on the presence of the core
         * Disc module itself (the module in which this is defined).
         * <p/>
         * Lastly, observe that while this method can coordinate the assembly of any constituents within its grasp,
         * it cannot provide the same benefits across multiple invocations.  That is, if multiple bundles are
         * employed in a given script deployment, those bundles should be inserted into the overall document to
         * which they apply in dependency order.  Such deployments should take care to only allow upward
         * dependencies, lest they risk executing unnecessary XHR invocations for components that may simply be
         * scheduled to be loaded with later aggregate inclusions.
         *
         * @static
         * @method _bundle
         * @param {function} bundle
         *      A function containing an aggregated script to process as a Disc core bundle
         */
        $.Module._bundle = function(bundle) {
            var i, len;
            // use interceptor
            var uses = [];
            var use = $.Module.use;
            $.Module.use = function(name) {
                uses.push(name);
                return $.Module._create(name);
            }
            // include interceptor
            var includes = { };
            var include = $.Module._include;
            $.Module._include = function(facetName, host, decl) {
                includes[host + "|" + facetName] = decl;
            }
            // create interceptor
            var creates = { };
            var createIncludes = [];
            var create = $.Module.create;
            $.Module.create = function(name, decl) {
                // declaration include interceptor
                var createInclude = $.Module._include;
                $.Module._include = function(facetName, host) {
                    createIncludes.push([facetName, host]);
                }
                try {
                    create.call($.Module, name, decl);
                    creates[name] = true;
                }
                finally {
                    $.Module._include = createInclude;
                }
            }
            // execute bundle
            try {
                bundle();
            }
            finally {
                // restore intercepted methods
                $.Module.use = use;
                $.Module._include = include;
                $.Module.create = create;
            }
            // realize includes
            for (i = 0, len = createIncludes.length; i < len; i++) {
                var decl = includes[createIncludes[i][1].$meta._name + "|" + createIncludes[i][0]];
                createIncludes[i].push(decl);
                // include initializer interceptor
                var initializer = $.Module._initializer;
                $.Module._initializer = (function(module) {
                    return function(init, _module) {
                        initializer.call($.Module, init, (_module || module));
                    }
                })(createIncludes[i][1]);
                try {
                    include.apply($.Module, createIncludes[i]);
                }
                finally {
                    $.Module._initializer = initializer;
                }
            }
            // dynamically load any modules and facets not included in this bundle
            for (i = 0, len = uses.length; i < len; i++) {
                if (!creates[uses[i]]) {
                    $.Module.use(uses[i]);
                }
            }
            // finally, try realizing all created modules
            $._Object.each(creates, function(key) {
                registry[key].$meta._tryRealized();
            });
        }

        /**
         * Frees a module for garbage collection by removing it from the global namespace hierarchy and deleting
         * internal references to it.  While module code itself can never be truly unloaded from the current
         * document's code space, this attempts to do the next best thing by releasing Disc's constructed references to
         * the module.  All of the module's child modules are likewise released, though external references to the
         * module, its children, or constituent members will not be affected by this action.
         * <p/>
         * This method does <i>not</i> work on namespace objects that are not true Disc modules; e.g. those that
         * existed in the global namespace hierarchy before being created by the Disc module system.
         *
         * @static
         * @method _detach
         * @param {string|bea.wlp.disc.Module} nameOrModule
         *      Either the name of the module to detach, or the module instance itself
         */
        $.Module._detach = function(nameOrModule) {
            var name = (nameOrModule instanceof $.Module ? nameOrModule.$meta._name : nameOrModule);
            if (registry[name]) {
                $._Object.each(registry, function(mod) {
                    if (registry[mod] instanceof $.Module && registry[mod].$meta.getName().indexOf(name + ".") == 0) {
                        delete registry[mod];
                    }
                });
                delete registry[name];
                eval("try { delete window." + name + "; } catch (e) { window[name] = undefined; }")
            }
        }

        /**
         * This method is responsible for the dynamic loading of unresolved module or facet files, as needed.  This
         * should only ever be invoked by Disc core internals.
         *
         * @static
         * @method _load
         * @param {bea.wlp.disc.Module} module
         *      The module to be realized through dynamic script loading
         * @param {string} [facet]
         *      The base name of the facet file to be loaded, if not the main module file
         * @returns {boolean}
         *      True if the target file was loaded; false otherwise
         */
        $.Module._load = function(module, facet) {
            var uri = module.replace(/\./g, "/") + "/" + (facet || "module") + ".js";
            uri = $.Config.getScriptPath(uri);
            try {
                // load the module or module facet with a synchronous XHR request
                new $._Resource(uri).evaluate(true);
                return true;
            }
            catch (e) {
                if (e.status != 404) {
                    throw e;
                }
            }
            return false;
        }

        /**
         * This method is the low-level module creation function, used to convert a raw, multi-part module name into
         * an uninitialized hierarchy of Disc module instances.  Existing modules are returned from cache rather than
         * recreated.  This method should only ever be invoked by Disc core internals.
         *
         * @static
         * @method _create
         * @param {string} name
         *      The multi-part name of the module to be created
         * @returns
         *      A module for the specified name
         */
        $.Module._create = function(name) {
            var module;
            if (typeof name == "string" && name.length > 0) {
                module = registry[name];
                if (!module) {
                    module = (function(path) {
                        var module = registry[path];
                        if (!module) {
                            module = new $.Module(path);
                            registry[path] = module;
                        }
                        if (!module.$meta._parent) {
                            var parts = path.split(".");
                            path = parts.slice(0, parts.length - 1).join(".");
                            
                            // Set the child --> parent link.  Recursively invoke this function to register each parent module.
                            // The root module has the window object set as its parent. 
                            module.$meta._parent = (path.length > 0 ? arguments.callee(path) : window);
                            
                            // Set the parent --> child link
                            module.$meta._parent[parts[parts.length - 1]] = module;
                        }
                        return module;
                    })(name);
                }
            }
            return module;
        }

        /**
         * This method is used to realize an unpopulated module instance from a declaration object.  This method
         * should only ever be invoked by Disc core internals.
         *
         * @static
         * @method _realize
         * @param {bea.wlp.disc.Module} module
         *      The unpopulated module to which the decl param should be applied
         * @param {object} decl
         *      The module declaration specificiation object
         */
        $.Module._realize = function(module, decl) {
            if (module) {
                if (decl && (decl.require || decl.include || decl.declare || decl.initialize)) {
                    var i;
                    
                    // load each module that is designated as a dependency in the "require" array
                    decl.require = (typeof decl.require == "string" ? [decl.require] : decl.require);
                    for (i = 0; decl.require instanceof Array && i < decl.require.length; i++) {
                        $.Module.use(decl.require[i]);
                    }
                    
                    // include each module facet that is designated to be included in the module 
                    decl.include = (typeof decl.include == "string" ? [decl.include] : decl.include);
                    for (i = 0; decl.include instanceof Array && i < decl.include.length; i++) {
                        $.Module._include(decl.include[i]);
                    }
                    
                    // this module and its dependents are fully realized, so process the initializer function (to be executed later)
                    if (decl.initialize) {
                        $.Module._initializer(decl.initialize);
                    }
                    
                    // process the object or constructor function used to declare the constituent members of the module
                    if (typeof decl.declare == "function") {
                        decl.declare.call(module, module, $.Catalog._L(module.$meta._catalog));
                    }
                    else {
                        $._Object.mixin(module, decl.declare);
                    }
                }
                else {
                    $._Object.mixin(module, decl);
                }
            }
        }
    }

    // Bootstrap the core module
    $.Module.create("bea.wlp.disc", $);
})();
