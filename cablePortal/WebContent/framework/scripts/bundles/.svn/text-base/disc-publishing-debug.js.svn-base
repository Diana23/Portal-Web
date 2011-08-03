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
bea.wlp.disc.Module._bundle(function() {
// bea.wlp.disc._util
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

bea.wlp.disc.Module.create("bea.wlp.disc._util", {
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var ua = navigator.userAgent;

        $.Browser = {
            IE: !!(window.attachEvent && !window.opera) && new function() {
                var offset = ua.indexOf("MSIE ");
                this.version = +(ua.substring(offset + 5, ua.indexOf(";", offset)));
                this.is7Up = (this.version >= 7);
                this.isPre7 = !this.is7Up;
                this.jscript = ScriptEngine && parseFloat(ScriptEngineMajorVersion() + "." + ScriptEngineMinorVersion());
            },
            Gecko: !!(ua.indexOf("Gecko") > -1 && ua.indexOf("KHTML") == -1) && { },
            Opera: !!window.opera && { },
            WebKit: !!(ua.indexOf("AppleWebKit/") > -1) && { }
        }

        // See bea.wlp.disc._Object for more utilities available on this object
        $.Object = $Disc._Object.mixin({
            // Add additional object utilities here, as needed
        }, $Disc._Object);

        $.Array = {
            each: function(src, iterator, when) {
                if (src) {
                    for (var i = 0, len = src.length; i < len; i++) {
                        if (!when || when(i, src[i])) {
                            iterator(i, src[i]);
                        }
                    }
                }
            }
        }

        $.Function = {
            nop: function() { },
            filter: function(condition, fn) {
                return (condition ? fn : $.Function.nop);
            },
            name: function(fn) {
                var match = fn.toString().match(/function\s*([^\(\s]*)/);
                return (match ? match[1] : "");
            },
            signature: function(fn, body) {
                if (body) {
                    var sig = fn.toString().replace(/\s+/g, " ");
                    return (sig.length > 100 ? sig.substring(0, 97) + "..." : sig);
                }
                else {
                    var match = fn.toString().match(/function[^\)]*/);
                    return (match ? match[0] + ")" : "");
                }
            },
            trace: function(fn, start, depth, max) {
                fn = (fn || arguments.callee && arguments.callee.caller);
                if (fn && fn.caller) {
                    var trace = [];
                    start = (start || 0), depth = (depth || 0), max = (max || 10);
                    if (depth >= start) {
                        trace.push(fn);
                    }
                    if (depth < max) {
                        try {
                            var next = arguments.callee(fn.caller, start, depth + 1, max);
                            if (next) { trace = trace.concat(next); }
                        }
                        catch (ignore) {
                        }
                    }
                    if (depth == 0) {
                        trace.toString = function() {
                            var st = "";
                            $.Array.each(this, function(i, v) { st += "  " + $.Function.signature(v, true) + "\n"; });
                            return st;
                        }
                    }
                    return trace;
                }
            }
        }
        
        $.Error = $Disc.Class.create({
            initialize: function(error, traceFrom) {
                if (error instanceof Error) {
                    this.name = error.name;
                    this.message = error.message;
                    this.cause = error;
                }
                else if (typeof error == "string") {
                    this.name = "Error";
                    this.message = args;
                }
                else if (typeof error == "object") {
                    $.Object.mixin(this, error);
                }
                else {
                    this.name = "Error";
                }
                if (arguments.callee.caller) {
                    this.trace = $.Function.trace(arguments.callee, (traceFrom || 0) + 2);
                }
            },
            toString: function() {
                return (
                    (this.trace && (L("Trace to capture:") + "\n" + this.trace.toString())) ||
                    (this.cause && this.cause.toString()) ||
                    (this.name + (this.message ? ": " + this.message : ""))
                );
            }
        });

        $.Dom = {
            VISIBILITY: $.$meta.getName("Dom.visibility"),

            suppressSelects: $.Function.filter(
                $.Browser.IE.isPre7,
                function() {
                    var selects = document.getElementsByTagName("select");
                    if (selects) {
                        for (var i = 0, len = selects.length; i < len; i++) {
                            var visibility = selects[i].style.visibility;
                            var isSet = visibility && visibility.length > 0;
                            selects[i][$.Dom.VISIBILITY] = (isSet ? visibility : "visible");
                            selects[i].style.visibility = "hidden";
                        }
                    }
                }
            ),

            restoreSelects: $.Function.filter(
                $.Browser.IE.isPre7,
                function() {
                    var selects = document.getElementsByTagName("select");
                    if (selects) {
                        for (var i = 0, len = selects.length; i < len; i++) {
                            var visibility;
                            try {
                                visibility = selects[i][$.Dom.VISIBILITY];
                            }
                            catch (ignore) {
                            }
                            if (visibility) {
                                selects[i].style.visibility = visibility;
                            }
                            selects[i][$.Dom.VISIBILITY] = undefined;
                        }
                    }
                }
            ),

            purge: $.Function.filter(
                ($.Browser.IE && $.Browser.IE.jscript < 5.7),
                function(d) {
                    var a = d.attributes, i, l, n;
                    if (a) {
                        for (i = 0, l = a.length; i < l; i++) {
                            n = a[i].name;
                            if (typeof d[n] === 'function') {
                                d[n] = null;
                            }
                        }
                    }
                    a = d.childNodes;
                    if (a) {
                        for (i = 0, l = a.length; i < l; i++) {
                            arguments.callee(d.childNodes[i]);
                        }
                    }
                }
            ),

            clearContents: function(container) {
                while (container.hasChildNodes()) {
                    $.Dom.purge(container.firstChild);
                    container.removeChild(container.firstChild);
                }
            },

            getTextContent: function(el) {
                return (el.innerText ? el.innerText : (el.textContent ? el.textContent : ""));
            },

            getViewport: function() {
                var w = 0, h = 0;
                if ($.Browser.Gecko) {
                    w = document.documentElement.clientWidth;
                    h = window.innerHeight;
                }
                else {
                    if (!$.Browser.Opera && window.innerWidth) {
                        w = window.innerWidth;
                        h = window.innerHeight;
                    }
                    else {
                        if (!$.Browser.Opera && document.documentElement && document.documentElement.clientWidth) {
                            var w2 = document.documentElement.clientWidth;
                            if (!w || w2 && w2 < w) {
                                w = w2;
                            }
                            h = document.documentElement.clientHeight;
                        }
                        else {
                            if (document.body && document.body.clientWidth) {
                                w = document.body.clientWidth;
                                h = document.body.clientHeight;
                            }
                        }
                    }
                }
                return { width: w, height: h };
            },

            getScroll: function () {
                var top, left;
                if (window.pageYOffset)
                {
                    top = window.pageYOffset;
                    left = window.pageXOffset;
                }
                else if (document.documentElement)
                {
                    top = document.documentElement.scrollTop;
                    left = document.documentElement.scrollLeft;
                }
                else if (document.body)
                {
                    top = document.body.scrollTop;
                    left = document.body.scrollLeft;
                }
                return { top: top || 0, left: left || 0 };
            },

            getHeadTag: function() {
                var head = document.getElementsByTagName("head");
                if (head) {
                    head = head[0];
                }
                else {
                    head = document.createElement("head");
                    var body = document.getElementsByTagName("body");
                    if (body) {
                        document.documentElement.insertBefore(head, body[0]);
                    }
                    else {
                        document.appendChild(head);
                    }
                }
                return head;
            },

            eachAncestor: function(element, action, self) {
                if (!self) {
                    element = element.parentNode;
                }
                while (element && action(element)) {
                    element = element.parentNode;
                }
            },

            eachDescendantLinear: function(element, action, tagName) {
                var name = tagName || '*';
                var elements = element.getElementsByTagName(name);
                for (var i = 0; i < elements.length; i++) {
                    if (!action(elements[i])) {
                        break;
                    }
                }
            },

            eachDescendantRecursive: function(element, action, tagName) {
                var name = tagName.toLowerCase();
                var elements = element.childNodes;
                for (var i = 0; i < elements.length; i++) {
                    var child = elements[i];
                    var childName = child.tagName && child.tagName.toLowerCase();
                    if ( !((name == childName) || (name == '*')) || action(child)) {
                        this.eachDescendantRecursive(child, action, tagName);
                    }
                }
            }
        }

        $.Event = {
            getEvent: function(event) {
                return (event ? event : window.event);
            },
            connect: function(target, type, handler) {
                function wrapper(event) {
                    return handler($.Event.getEvent(event));
                }
                var result = false;
                if (target.addEventListener) {
                    target.addEventListener(type, wrapper, false);
                    result = true;
                }
                else if (target.attachEvent) {
                    result = target.attachEvent("on" + type, wrapper);
                }
                else {
                    var name = "on" + type;
                    var old = (target[name]) ? target[name] : function() {};
                    target[name] = function(event) { old(event); wrapper(event); };
                    result = true;
                }
                return result;
            }
        }

        $.Json = {
            parse: function(jsonText) {
                if (jsonText.indexOf("/*") == 0 && jsonText.lastIndexOf("*/") == jsonText.length - 2) {
                    jsonText = jsonText.substring(2, jsonText.length - 2);
                }
                return eval("(" + jsonText + ")");
            },
            serialize: function (obj) {
                var serialize = arguments.callee;
                var objtype = typeof (obj);
                var props = [];
                var value;
                var val;

                if (obj === null) {
                    value = "null";
                }
                else if (objtype == "undefined") {
                    value = "undefined";
                }
                else if ((objtype == "number") || (objtype == "boolean")) {
                    value = obj;
                }
                else if (objtype == "string") {
                    value = '"' + obj + '"';
                }
                else if (objtype == "function") {
                    value = "(function)";
                }
                else if ((objtype != "function") && (typeof (obj.length) == "number")) {
                    for (var i = 0; i < obj.length; i++) {
                        val = serialize(obj[i]);
                        props.push(val);
                    }
                    value = '[' + props.join(',') + ']';
                }
                else {
                    for (var p in obj) {
                        val = serialize(obj[p]);
                        props.push('"' + p + '"' + ':' + val);
                    }
                    value = '{' + props.join(',') + '}';
                }
                return value;
            }
        }

        // @review Param operations are compact, but inefficient
        $.Uri = $Disc.Class.create(function(Uri) {
            var regex = /^((\w+):\/\/)?((\w+):?(\w+)?@)?([^\/\?:]+)?:?(\d+)?(\/?[^\?#]+)?\??([^#]+)?#?(\w*)/;
            var fields = { 'Protocol': 2, 'UserName': 4, 'Password': 5, 'Host': 6, 'Port': 7, 'PathName': 8, 'QueryString': 9, 'Fragment': 10 };
            this.initialize = function(config) {
                this._values = { };
                var self = this;
                $.Object.each(fields, function(k, v) {
                    self['get' + k] = function() { return this._values[k]; };
                    self['set' + k] = function(value) { this._values[k] = value || ''; };
                });
                if (config instanceof Uri) {
                    $.Object.each(fields, function(k, v) {
                        self['set' + k].call(self, config['get' + k].call(config));
                    });
                }
                else if (typeof config == 'object') {
                    $.Object.each(fields, function(k, v) {
                        var value = config[k.charAt(0).toLowerCase() + k.slice(1)];
                        value && self['set' + k].call(self, value);
                    });
                    config.params && this.setParams(config.params);
                }
                if (typeof config == 'string') {
                    var match = regex.exec(config);
                    if (!match) {
                        throw L('Invalid URI: {0}', config);
                    }
                    $.Object.each(fields, function(k, v) {
                        self['set' + k].call(self, match[v]);
                    });
                }
                $.Object.each({
                    add: function(name, value) { this[name] = (this[name] || []).push(value); },
                    set: function(name, value) { this[name] = [value]; },
                    get: function(name) { return this[name]; },
                    remove: function(name) { delete this[name]; }
                }, function(k, v) {
                    self[k + 'Param'] = function() {
                        var params = self.getParams();
                        var result = v.apply(params, arguments);
                        self.setParams(params);
                        return result;
                    }
                });
            }
            this.getParams = function() {
                var params = { };
                $.Array.each(this.getQueryString().split('&'), function(i, v) {
                    var kv = v.split('=');
                    if (kv[0]) {
                        var name = decodeURIComponent(kv[0]), value = decodeURIComponent(kv[1]) || '';
                        (params[name] = params[name] || []).push(value);
                    }
                });
                return params;
            }
            this.setParams = function(params) {
                var qs = '';
                $.Object.each(params, function(k, v) {
                    $.Array.each(v instanceof Array ? v : [v], function(i, item) {
                        qs = qs + (qs ? '&' : '') + encodeURIComponent(k) + '=' + encodeURIComponent(item);
                    });
                });
                this.setQueryString(qs);
            }
            this.toString = function() {
                var uri = '', self = this;
                function get(f) { return self._values[f]; }
                function pre(d, f) { return get(f) ? (d ? d : '') + get(f) : ''; }
                function post(f, d) { return get(f) ? get(f) + (d ? d : '') : ''; }
                uri += post('Protocol', '://');
                uri += post('UserName', pre(':', 'Password') + '@');
                uri += post('Host', pre(':', 'Port'));
                uri += pre('', 'PathName');
                uri += pre('?', 'QueryString');
                uri += pre('#', 'Fragment');
                return uri;
            }
        });
        
        $.Wlp = {
            // @review Ideally the WLP JSON response would encode the parsed fields separately
            parseHookId: function(hookId) {
                var label = hookId.slice(0, hookId.length - 5);
                var type = label.slice(0, 2);
                label = label.slice(2, label.length);
                return { label: label, type: type };
            }
        }
    }
});
bea.wlp.disc.Module._include("AsyncRequestOverlay", "bea.wlp.disc.xie", function() {
// bea.wlp.disc.xie[AsyncRequestOverlay]
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
    require: ["bea.wlp.disc._util", "bea.wlp.disc.event"],
    declare: function($, L) {
        var $Util = bea.wlp.disc._util;

        /**
         * This object represents the global asynchronous request overlay used to block UI input during asynchronous
         * XIE interactions.  The small public API available on this object allows for a significant degree of
         * customization of the overlay, or even allows for the overlay to be suppressed for
         * applications that either wish to provide their own blocking overlay implementation or simply don't need or
         * want it to be employed.
         * <p/>
         * Further customization of UI-blocking appearance and behavior during XIE requests can be achieved by
         * listening for the overlay's "shown" and "hidden" events. Advanced behaviors such as wait animations or the
         * display of additional UI during XIE requests are easily synchronized with the display of this object by
         * observing these events.
         *
         * @object AsyncRequestOverlay
         */
        $.AsyncRequestOverlay = new (bea.wlp.disc.Class.create(function() {
            
            /**
             * @private
             * @method initialize
             */
            this.initialize = function() {
                var dom = document.createElement("div");
                this._dom = dom;
                dom.id = this._getElementId();
                dom.style.position = (this._useFixed() ? "fixed" : "absolute");
                dom.style.visibility = "hidden";
                dom.style.top = "0";
                dom.style.left = "0";
                this.setOpacity();
                this.setColor();
                this.setZIndex();
                var self = this;
                function update() { self._update(); }
                $Util.Event.connect(window, "resize", update);
                $Util.Event.connect(window, "scroll", update);
                this._enabled = true;
                this._events = {
                    OnShown: new bea.wlp.disc.event.Event("OnShown"),
                    OnHidden: new bea.wlp.disc.event.Event("OnHidden")
                }
            }

            /**
             * Allows the application to control whether or not XIE's overlay system is employed for input blocking
             * during asynchronous XIE requests.  Setting the 'enabled' property to <code>false</code> suppresses all
             * use of the overlay.  Setting this property to <code>false</code> will also cause the overlay to be
             * immediately hidden if it is currently showing.
             *
             * @method setEnabled
             * @param {boolean} enabled
             *      True if this overlay system should be enabled; false otherwise
             */
            this.setEnabled = function(enabled) {
                this._enabled = !!enabled;
                if (!this._enabled) {
                    this._hide();
                }
            }
            
            /**
             * Returns the current 'enabled' property state for the overlay.
             *
             * @method getEnabled
             * @returns {boolean}
             *      The current 'enabled' property state
             */
            this.getEnabled = function() {
                return this._enabled;
            }

            /**
             * Allows the opacity of the overlay to be set. By default, the overlay is configured to be completely
             * transparent (i.e. set to a value of 0.0).
             *
             * @method setOpacity
             * @param {number} opacity
             *      A numeric value ranging from 0.0 to 1.0
             */
            this.setOpacity = function(opacity) {
                this._opacity = ((typeof opacity == "number" && opacity <= 1.0 && opacity >= 0 && opacity) || 0);
                if ($Util.Browser.IE) {
                    this._dom.style.filter = "alpha(opacity=" + (this._opacity * 100) + ")";
                }
                else {
                    this._dom.style.opacity = this._opacity;
                }
            }

            /**
             * Returns the current opacity setting for the overlay.
             *
             * @method getOpacity
             * @returns {number}
             *      The currently set opacity value for the overlay, ranging from 0.0 to 1.0
             */
            this.getOpacity = function() {
                return this._opacity;
            }

            /**
             * Allows the color of the overlay to be set.  By default, the overlay color is configured to "#000"
             * (black).  Valid values include anything accepted/recognized by CSS color properties.  Examples include
             * "#ffffff" or "white".
             *
             * @method setColor
             * @param {string} color
             *      A valid CSS color value
             */
            this.setColor = function(color) {
                this._dom.style.backgroundColor = (color || "#000");
            }

            /**
             * Reurns the current color setting for the overlay.
             *
             * @method getColor
             * @returns {string}
             *      The current color setting
             */
            this.getColor = function() {
                return this._dom.style.backgroundColor;
            }

            /**
             * Allows the z-index of the overlay to be configured.  By default, a value of 9000 is used.
             *
             * @method setZIndex
             * @param {number} zIndex
             *      The z-index setting for the overlay
             */
            this.setZIndex = function(zIndex) {
                this._dom.style.zIndex = (typeof zIndex == "number" ? zIndex : 9000);
            }

            /**
             * Returns the current z-index setting for the overlay.
             *
             * @method getZIndex
             * @returns {number}
             *      The current z-index setting
             */
            this.getZIndex = function() {
                return this._dom.style.zIndex;
            }
            
            /**
             * This method allows listeners to be added to supported events.  Supported event names include:
             * <ul>
             *      <li><b>"shown"</b>: Fired when the overlay is shown to the user during an async request</li>
             *      <li><b>"hidden"</b>: Fired when the overlay is hidden from view after the request completes</li>
             * </ul>
             *
             * @method on
             * @param {string} name
             *      The name of the event to listen to (either "shown" or "hidden")
             * @param {function} listener
             *      The listener callback function to invoke when the event is fired
             * @see bea.wlp.disc.xie.AsyncRequestOverlay.un
             */
            this.on = function(name, listener) {
                this._listener("add", name, listener);
            }
            
            /**
             * Removes a listener from one of the overlay's events that was added via the <code>on</code>
             * method.
             *
             * @method un
             * @param {string} name
             *      The name of the event being listened to (either "shown" or "hidden")
             * @param {function} listener
             *      The listener callback to remove; must match a function passed to <code>on</code> to succeed
             * @see bea.wlp.disc.xie.AsyncRequestOverlay.on
             */
            this.un = function(name, listener) {
                this._listener("remove", name, listener);
            }
            
            /**
             * A generic, internal method to set up or teardown event listeners.
             *
             * @method _listener
             * @param {string} op
             *      The type of operation to perform ("add" or "remove")
             * @param {string} name
             *      The name of the event to modify
             * @param {function}
             *      The event listener function of interest
             */
            this._listener = function(op, name, listener) {
                if (name) {
                    var event = this._events["On" + name.charAt(0).toUpperCase() + name.slice(1)];
                    if (event instanceof bea.wlp.disc.event.Event) {
                        event[op + "Listener"].call(event, listener);
                    }
                }
            }

            /**
             * An internal method used to show the overlay at the start of an XIE request.
             *
             * @method _show
             */
            this._show = function() {
                if (this._enabled) {
                    if (!document.getElementById(this._getElementId())) {
                        (document.body || document.documentElement).appendChild(this._dom);
                    }
                    this._isShowing = true;
                    this._update();
                    this._dom.style.visibility = "visible";
                    this._dom.style.cursor = "wait";
                    $Util.Dom.suppressSelects();
                    this._events.OnShown._fire(this);
                }
            }

            /**
             * An internal method used to hide the overlay when an XIE request completes.
             *
             * @method _hide
             */
            this._hide = function() {
                $Util.Dom.restoreSelects();
                this._dom.style.cursor = "default";
                this._dom.style.visibility = "hidden";
                this._isShowing = false;
                this._events.OnHidden._fire(this);
            }

            /**
             * Generates an id for the overlay's underlying HTML element.
             *
             * @method _getElementId
             * @returns {string}
             *      The id for the overlay's HTML element
             */
            this._getElementId = function() {
                return $.$meta.getName("AsyncRequestOverlay._dom");
            }

            /**
             * Determines whether or not 'fixed' CSS positioning should be used when applying the overlay to the
             * document.
             *
             * @method _useFixed
             * @returns {boolean}
             *      Whether or not to use fixed CSS positioning
             */
            this._useFixed = function() {
                return (!$Util.Browser.IE || $Util.Browser.IE.is7Up);
            }

            /**
             * Updates the size and position of the HTML element to cover the current browser viewport.
             *
             * @method _update
             */
            this._update = function() {
                if (this._isShowing) {
                    var viewport = $Util.Dom.getViewport();
                    var h = viewport.height;
                    var w = viewport.width;
                    this._dom.style.width = w + "px";
                    this._dom.style.height = h + "px";
                    if (!this._useFixed()) {
                        var scroll = $Util.Dom.getScroll();
                        this._dom.style.top = scroll.top + "px";
                        this._dom.style.left = scroll.left + "px";
                    }
                    viewport = $Util.Dom.getViewport();
                    if (viewport.width != w) {
                        this._dom.style.width = viewport.width + "px";
                    }
                    if (viewport.height != h) {
                        this._dom.style.height = viewport.height + "px";
                    }
                }
            }
        }));
    }
});
});
bea.wlp.disc.Module._include("Events", "bea.wlp.disc.xie", function() {
// bea.wlp.disc.xie[Events]
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
    require: ["bea.wlp.disc.event", "bea.wlp.disc._util"],
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Event = $Disc.event;
        var $Util = $Disc._util;

        /**
         * This object contains the set of public, global events that are fired during XIE's WLP interaction lifecycle.
         * The interaction lifecycle involves setting up and executing an Ajax request to the WLP server, receiving a
         * response, and subsequently processing the response.  WLP Ajax responses are encoded in an internal JSON
         * format (the form of which is reserved and subject to change), and XIE manages the evaluation and handling of
         * the body of these responses.  The suite of public XIE events provides public access to key moments of
         * interest during this lifecycle; listening code can use these event hooks to respond to or even influence the
         * outcome of the interaction.
         * <p/>
         * The XIE interaction lifecycle fires these events over the course of an interactions as follows:
         * <ol>
         *      <li><b>OnPrepareUpdate</b>: Fired when an interaction has been initiated, but before the request is
         *          made; this event is cancellable; cancellation will terminate the interaction without making the
         *          underlying request</li>
         *      <li><b>OnHandleUpdate</b>: Fired immediately after XIE receives the response, but before processing has
         *          begun</li>
         *      <li><b>OnRedirectUpdate</b>: Fired <i>iff</i> the server is forcing the client to perform a redirect;
         *          processing will complete ASAP and then perform a client-side redirect</li>
         *      <li>Then, for each piece of response markup returned by the server, the following events are fired:
         *          <ol type="a">
         *              <li><b>OnPrepareMarkup</b>: Fired before converting a response markup fragment into a DOM subtree; this
         *                  event will fire once for each markup fragment that was returned in the response</li>
         *              <li><b>OnPrepareContent</b>: Fired immediately after converting a markup fragment into a DOM subtree,
         *                  but before doing additional processing (such as rewriting anchor hrefs and form actions); this
         *                  event will fire once for each markup fragment that was returned in the response</li>
         *              <li><b>OnInjectContent</b>: Fired immediately after injecting the fully processed (e.g. rewritten)
         *                  DOM subtree into the document, but before executing any scripts associated with that content; this
         *                  event will fire once for each markup fragment that was returned in the response</li>
         *          </ol>
         *      </li>
         *      <li><b>OnCompleteUpdate</b>: Fired after XIE has completed all response processing for the interaction,
         *          including the execution of any scripts defined by the markup updated during the interaction</li>
         *      <li><b>OnError</b>: Fired any time an error occurs during interaction processing; processing may or may
         *          not continue once an OnError event has fired</li>
         * </ol>
         * Each event delivers a payload object to its listeners when the event is fired.  The type and capabilities of
         * each payload object differ from event to event.  See the documentation for each individual event for more
         * information about specific event payloads.
         *
         * @object Events
         * @see bea.wlp.disc.xie.Events.Payloads
         * @see bea.wlp.disc.event.Event
         */
        $.Events = {
            /**
             * This event delivers a <code>MutableUpdatePayload</code> when fired.  It can be useful in preparing
             * for the coming interaction, saving current UI state, or even preventing the interaction from
             * occurring (by cancelling the current event; see <code>bea.wlp.disc.event.Event</code> for more
             * information on cancelling events).
             *
             * @object {bea.wlp.disc.event.Event} Events.OnPrepareUpdate
             * @see bea.wlp.disc.xie.Events.Payloads.MutableUpdatePayload
             */
            OnPrepareUpdate: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnPrepareUpdate", true);
                },
                _payload: function(args) {
                    return new $.Events.Payloads.MutableUpdatePayload(args);
                }
            })),

            /**
             * This event delivers a <code>RedirectPayload</code> when fired.  It can be useful for performing
             * last minute operations before the redirect is actually performed.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnRedirectUpdate
             * @see bea.wlp.disc.xie.Events.Payloads.RedirectPayload
             */
            OnRedirectUpdate: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnRedirectUpdate", $);
                },
                _payload: function(args, xhr, response) {
                    return new $.Events.Payloads.RedirectPayload(args, xhr, response);
                }
            })),

            /**
             * This event delivers a <code>ResponsePayload</code> when fired.  It can be useful for last minute
             * sanitizing, preparation, or saving of document state before XIE begins replacing portions of the
             * document with new content.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnHandleUpdate
             * @see bea.wlp.disc.xie.Events.Payloads.ResponsePayload
             */
            OnHandleUpdate: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnHandleUpdate", $);
                },
                _payload: function(args, xhr, response) {
                    return new $.Events.Payloads.ResponsePayload(args, xhr, response);
                }
            })),

            /**
             * This event delivers a <code>MutableMarkupPayload</code> when fired.  It can be useful for filtering
             * or rewriting the raw markup response or a document fragment before XIE begins processing it.  Note
             * that such filtering should be undertaken with great care -- if the integrity of the markup is harmed
             * during this event, subsequent events for both this markup and the overall interaction may fail.
             * Also beware that certain HTML elements and artifacts present in the markup exist to support both XIE
             * and other Disc client-side constructs, and are considered by WLP to be internal implementation
             * details; relying on the presence of or otherwise interfering with these elements can also cause
             * irreparable harm to the XIE response processing sequence, or may cause undefined behavior in other
             * Disc APIs or even general application code.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnPrepareMarkup
             * @see bea.wlp.disc.xie.Events.Payloads.MutableMarkupPayload
             */
            OnPrepareMarkup: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnPrepareMarkup", $);
                },
                _payload: function(args, xhr, response, label, type, control) {
                     return new $.Events.Payloads.MutableMarkupPayload(args, xhr, response, label, type, control);
                }
            })),

            /**
             * This event delivers a <code>ContentPayload</code> when fired.  It can be useful for preliminary
             * DOM inspection and modification, before XIE has made any of its own modifications to the DOM subtree
             * in preparation for injection into the document.  As with <code>OnPrepareMarkup</code>, note
             * that such changes should be undertaken with great care -- if the integrity of the markup is harmed
             * during this event, subsequent events for both this markup and the overall interaction may fail.
             * Also beware that certain HTML elements and artifacts present in the markup exist to support both XIE
             * and other Disc client-side constructs, and are considered by WLP to be internal implementation
             * details; relying on the presence of or otherwise interfering with these elements can also cause
             * irreparable harm to the XIE response processing sequence, or may cause undefined behavior in other
             * Disc APIs or even general application code.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnPrepareContent
             * @see bea.wlp.disc.xie.Events.Payloads.ContentPayload
             */
            OnPrepareContent: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnPrepareContent", $);
                },
                _payload: function(args, xhr, response, label, type, control, container) {
                    return new $.Events.Payloads.ContentPayload(args, xhr, response, label, type, control, container);
                }
            })),

            /**
             * This event delivers a <code>ContentPayload</code> when fired.  It can be useful for DOM inspection
             * and modification, after XIE has made any of its own modifications (e.g. rewriting) to the DOM
             * subtree after injection into the document.  As with <code>OnPrepareMarkup</code>, note
             * that such changes should be undertaken with great care -- if the integrity of the markup is harmed
             * during this event, subsequent events for both this markup and the overall interaction may fail.
             * Also beware that certain HTML elements and artifacts present in the markup exist to support both XIE
             * and other Disc client-side constructs, and are considered by WLP to be internal implementation
             * details; relying on the presence of or otherwise interfering with these elements can also cause
             * irreparable harm to the XIE response processing sequence, or may cause undefined behavior in other
             * Disc APIs or even general application code.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnInjectContent
             * @see bea.wlp.disc.xie.Events.Payloads.ContentPayload
             */
            OnInjectContent: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnInjectContent", $);
                },
                _payload: function(args, xhr, response, label, type, control, container) {
                    return new $.Events.Payloads.ContentPayload(args, xhr, response, label, type, control, container);
                }
            })),

            /**
             * This event delivers a <code>ResponsePayload</code> when fired.  It can be useful for cleaning up
             * custom code after an interaction or providing feedback to the user that the interaction is complete.
             * This event is fired after all other XIE events have completed and necessary scripts have been
             * executed.  This event may or may not fire in error scenarios, depending on the severity of the
             * error.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnCompleteUpdate
             * @see bea.wlp.disc.xie.Events.Payloads.ResponsePayload
             */
            OnCompleteUpdate: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnCompleteUpdate", $);
                },
                _payload: function(args, xhr, response) {
                    return new $.Events.Payloads.ResponsePayload(args, xhr, response);
                }
            })),

            /**
			* This event delivers an <code>AbandonedPayload</code> when fired.  It can be useful for handling responses
              * that did not originate from a BEA WebLogic Portal server application.  Such cases may include proxy server
              * redirects.
              *
              * @object {bea.wlp.disc.event.Event} Events.OnAbandonUpdate
              * @see bea.wlp.disc.xie.Events.Payloads.AbandonedPayload
              */
             OnAbandonUpdate: new ($Event.Event.extend({
                 initialize: function() {
                     this.sup("OnAbandonUpdate", true);
                 },
                 _payload: function(args, xhr) {
                     return new $.Events.Payloads.AbandonedPayload(args,xhr);
                 }
             })),
 
             /**

             * This event delivers an <code>ErrorPayload</code> when fired.  This event only fires if XIE encouters
             * an unexpected problem during a given interaction.
             *
             * @object {bea.wlp.disc.event.Event} Events.OnError
             * @see bea.wlp.disc.xie.Events.Payloads.ErrorPayload
             */
            OnError: new ($Event.Event.extend({
                initialize: function() {
                    this.sup("OnError", $);
                },
                _payload: function(message, description, error) {
                    return new $.Events.Payloads.ErrorPayload(message, description, error);
                }
            })),

            _Debug: {
                enable: function(verbose) {
                    if (!this._listeners) {
                        this._listeners = { };
                        var self = this;
                        $Util.Object.each($.Events, function(name, event) {
                            if (event instanceof $Event.Event) {
                                self._listeners[name] = function(payload) {
                                    $Disc.Console.debug(name + (verbose ? ": " + payload.toString() : ""));
                                };
                                event.addListener(self._listeners[name]);
                            }
                        });
                    }
                },
                disable: function() {
                    if (this._listeners) {
                        $Util.Object.each(this._listeners, function(name, listener) {
                            $.Events[name].removeListener(listener);
                        });
                        delete this._listeners;
                    }
                }
            }
        }
    }
});
});
// bea.wlp.disc.xie
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
 * @name bea.wlp.disc.xie
 * @overview
 *      This module defines public APIs for Disc's "XMLHttpRequest Interaction Engine" (XIE), which is the client-side
 *      foundation for Ajax-driven interactions with WebLogic Portal (WLP).  XIE is also the platform on which various
 *      other Ajax-based, public Disc APIs are implemented, including <code>bea.wlp.disc.io.XMLHttpRequest</code>
 *      and <code>bea.wlp.disc.publishing.PortletSource</code>.
 *      <p/>
 *      XIE's public API can be divided into two main areas:
 *      <ul>
 *          <li><b>XIE Events</b>: found in <code>bea.wlp.disc.xie.Events.*</code>, these global events are fired
 *              when Ajax requests are made to WLP, and as subsequent responses are processed; these events provide
 *              public access to the underlying client-server interaction lifecycle</li>
 *          <li><b>Async Request Overlay</b>: found at <code>bea.wlp.disc.xie.AsyncRequestOverlay</code>, this object
 *              provides control over the blocking overlay introduced on a page by some XIE-driven interactions</li>
 *      </ul>
 */
bea.wlp.disc.Module.create("bea.wlp.disc.xie", {
    include: ["AsyncRequestOverlay", "Events", "Payloads", "_Service"]
});
bea.wlp.disc.Module._include("Payloads", "bea.wlp.disc.xie", function() {
// bea.wlp.disc.xie[Payloads]
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
    declare: function($, L) {
        
        /**
         * This object contains the set of Payload class definitions for the payload objects delivered when various XIE
         * events are fired.
         *
         * @object Events.Payloads
         * @see bea.wlp.disc.xie.Events
         */
        $.Events.Payloads = { };

        /**
         * This is the base class for all (non-error) payloads delivered by XIE events.
         *
         * @class Events.Payloads.UpdatePayload
         */
        $.Events.Payloads.UpdatePayload = bea.wlp.disc.Class.create({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args) {
                this._args = args;
                this._args._attrs = (this._args._attrs || { });
                this._args._iattrs = (this._args._iattrs || { });
            },

            /**
             * Returns the request URI for the XIE interaction that spawned this event.
             *
             * @method getRequestUri
             * @returns {string}
             *      The appropriate request URI
             */
            getRequestUri: function() {
                return this._args.uri;
            },

            /**
             * Returns a request header, by name, for the XIE interaction that spawned this event.
             *
             * @method getRequestHeader
             * @param {string} name
             *      The name of the request header to return
             * @returns {string}
             *      The value of the named request header, if found
             */
            getRequestHeader: function(name) {
                return (this._args.headers && this._args.headers[name]);
            },

            /**
             * Sets a custom attribute within the scope of the current interaction (aka 'update').  Any attributes
             * set in this manner will be visible by all event listeners fired within the scope of the same XIE
             * interaction, unless removed by setting a <code>null</code> value.
             *
             * @method setUpdateAttribute
             * @param {string} name
             *      The name of the atribute to set
             * @param {object} [value]
             *      The value of the attribute to set, or null or undefined to clear an existing value
             * @param {boolean} _internal
             *      Use this to specify the 'internal' attribute space; reserved for internal use only
             */
            setUpdateAttribute: function(name, value, _internal) {
                (_internal ? this._args._attrs : this._args._iattrs)[name] = value;
            },

            /**
             * Gets a custom attribute within the scope of the current interaction (aka 'update').
             *
             * @method getUpdateAttribute
             * @param {string} name
             *      The name of the attribute to return
             * @param {boolean} _internal
             *      Use this to specify the 'internal' attribute space; reserved for internal use only
             * @returns {object}
             *      The value of the named attribute
             */
            getUpdateAttribute: function(name, _internal) {
                return (_internal ? this._args._attrs : this._args._iattrs)[name];
            },

            /**
             * Applies a listener (via interception) function to this XIE interaction's internal lifecycle object.  This
             * is used internally to decouple higher level APIs from XIE's internals.
             *
             * @method _addLifecycleListener
             * @param {string} stage
             *      The name of the lifecycle stage to listen on
             * @param {function} action
             *      The function to call when the named lifecycle is invoked
             */
            _addLifecycleListener: function(stage, action) {
                this._args.lifecycle.addLifecycleListener(stage, action);
            },

            /**
             * Allows higher-level, internal APIs to specify whether or not XIE should rewite URLs when this response's
             * markup fragemtns are processed.
             *
             * @method _setRewrite
             * @param {boolean} rewrite
             *      Whether or not XIE should rewrite incoming markup for this request
             */
            _setRewrite: function(rewrite) {
                this._args.rewrite = rewrite;
            },

            /**
             * Returns a string representation of this payload for debugging purposes.  The exact format of this string
             * should not be relied upon, as it may change as XIE's payload implementations evolve.
             *
             * @method toString
             * @returns {string}
             *      A string describing this payload instance
             */
            toString: function() {
                var headers = [];
                bea.wlp.disc._Object.each(this._args.headers, function(name, header) {
                    headers.push("[" + name + "=" + header + "]");
                });
                return "uri: " + this._args.uri + ", headers: [" + headers.join(", ") + "]";
            }
        });

        /**
         * This payload allows custom request headers to be set for outbound XIE requests and is delivered when
         * <code>OnPrepareUpdate</code> requests are fired.
         *
         * @class Events.Payloads.MutableUpdatePayload
         * @extends bea.wlp.disc.xie.Events.Payloads.UpdatePayload
         * @see bea.wlp.disc.xie.Events.OnPrepareUpdate
         */
        $.Events.Payloads.MutableUpdatePayload = $.Events.Payloads.UpdatePayload.extend({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args) {
                this.sup(args);
            },

            /**
             * Sets a custom request header for the outbound XIE interaction request which this payload is
             * associated.
             *
             * @method setRequestHeader
             * @param {string} name
             *      The name of the request header
             * @param {string} value
             *      The value of the request header
             */
            setRequestHeader: function(name, value) {
                (this._args.headers = (this._args.headers || { }))[name] = value;
            }
        });

        /**
         * This is the base class for all non-error XIE payloads delivered for a given interaction after the server's
         * response has been received.  Instances of exactly this class are delivered when both
         * <code>OnHandleUpdate</code> and <code>OnCompleteUpdate</code> events are fired.
         *
         * @class Events.Payloads.ResponsePayload
         * @extends bea.wlp.disc.xie.Events.Payloads.UpdatePayload
         * @see bea.wlp.disc.xie.Events.OnHandleUpdate
         * @see bea.wlp.disc.xie.Events.OnCompleteUpdate
         */
        $.Events.Payloads.ResponsePayload = $.Events.Payloads.UpdatePayload.extend({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args, xhr, response) {
                this.sup(args);
                this._xhr = xhr;
                this._response = response;
            },

            /**
             * Returns the underlying XHR instance associated with this XIE interaction.
             *
             * @method _getXhr
             * @returns {XMLHttpRequest}
             *      The XHR instance used to execute this XIE interaction
             */
            _getXhr: function() {
                return this._xhr;
            }
        });

         /**
		 * This payload is delivered when <code>OnAbandonUpdate</code> events are fired.
          *
          * @class Events.Payloads.AbandonedPayload
          * @extends bea.wlp.disc.xie.Events.Payloads.UpdatePayload
          * @see bea.wlp.disc.xie.Events.OnAbandonUpdate
          */
         $.Events.Payloads.AbandonedPayload = $.Events.Payloads.UpdatePayload.extend({
             /**
              * @private
              * @method initialize
              */
             initialize: function(args, xhr) {
                 this.sup(args, xhr);
                 this._xhr = xhr;
             },
 
             /**
              * Returns the underlying XHR instance associated with this XIE interaction.
              *
              * @method getXhr
              * @returns {XMLHttpRequest}
              *      The XHR instance used to execute this XIE interaction
              */
             getXhr: function() {
                 return this._xhr;
             }
         });
 
         /**
         * This payload is delivered when <code>OnRedirectUpdate</code> events are fired.
         *
         * @class Events.Payloads.RedirectPayload
         * @extends bea.wlp.disc.xie.Events.Payloads.ResponsePayload
         * @see bea.wlp.disc.xie.Events.OnRedirectUpdate
         */
        $.Events.Payloads.RedirectPayload = $.Events.Payloads.ResponsePayload.extend({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args, xhr, response) {
                this.sup(args, xhr, response);
            },

            /**
             * Returns the URI to which the server has specified the client redirect.
             *
             * @method getRedirectUri
             * @returns {string}
             *      The redirect URI
             */
            getRedirectUri: function() {
                return this._response.redirect;
            },

            /**
             * Returns a string representation of this payload for debugging purposes.  The exact format of this string
             * should not be relied upon, as it may change as XIE's payload implementations evolve.
             *
             * @method toString
             * @returns {string}
             *      A string describing this payload instance
             */
            toString: function() {
                return this.sup() + ", redirectUri: " + this._response.redirect;
            }
        });

        /**
         * This is the base class for all fragment-based XIE event payloads.  Only subclasses of this class are
         * delivered by XIE events.
         *
         * @class Events.Payloads.MarkupPayload
         * @extends bea.wlp.disc.xie.Events.Payloads.ResponsePayload
         */
        $.Events.Payloads.MarkupPayload = $.Events.Payloads.ResponsePayload.extend(function(MarkupPayload) {
            /**
             * @private
             * @method initialize
             */
            this.initialize = function(args, xhr, response, label, type, control) {
                this.sup(args, xhr, response);
                this._label = label;
                var ct = MarkupPayload.ControlType;
                this._type = (type == "t_" ? ct.PORTLET : (type == "e_" ? ct.PAGE : ct.BOOK));
                this._control = control;
            }

            /**
             * Returns the label for the control being updated in relation to the event that delivered this payload.
             *
             * @method getControlLabel
             * @returns {string}
             *      The label of the currently updating control
             */
            this.getControlLabel = function() {
                return this._label;
            }

            /**
             * Returns the type for the control being updated in relation to the event that delivered this payload.
             *
             * @method getControlType
             * @returns {string}
             *      The type of the currently updating control
             * @see bea.wlp.disc.xie.Events.Payloads.MarkupPayload.ControlType
             */
            this.getControlType = function() {
                return this._type;
            }

            /**
             * Returns the content-type of the markup for the control being updated in relation to the event that
             * delivered this payload.
             *
             * @method getControlContentType
             * @returns {string}
             *      The content-type of the markup for currently updating control
             * @see bea.wlp.disc.xie.Events.Payloads.MarkupPayload.ControlType
             */
            this.getControlContentType = function() {
                return this._control.contentType;
            }

            /**
             * Returns the response markup for the control being updated in relation to the event that delivered this
             * payload.
             *
             * @method getControlMarkup
             * @returns {string}
             *      The response markup for the currently updating control
             */
            this.getControlMarkup = function() {
                return this._control.markup;
            }

            /**
             * Returns a string representation of this payload for debugging purposes.  The exact format of this string
             * should not be relied upon, as it may change as XIE's payload implementations evolve.
             *
             * @method toString
             * @returns {string}
             *      A string describing this payload instance
             */
            this.toString = function() {
                return this.sup() + ", label: " + this._label + ", type: " + this._type + ", markup: " + this._control.markup;
            }

            /**
             * A simple enumeration of the possible values that may be returned from <code>getControlType</code>
             *
             * @object Events.Payloads.MarkupPayload.ControlType
             * @see bea.wlp.disc.xie.Events.Payloads.MarkupPayload.getControlType
             */
            MarkupPayload.ControlType = {
                /**
                 * @static
                 * @field {string}
                 *      The "book" control type
                 */
                BOOK: "book",

                /**
                 * @static
                 * @field {string}
                 *      The "page" control type
                 */
                PAGE: "page",

                /**
                 * @static
                 * @field {string}
                 *      The "portlet" control type
                 */
                PORTLET: "portlet"
            }
        });

        /**
         * This payload is delivered when <code>OnPrepareMarkup</code> events are fired.
         *
         * @class Events.Payloads.MutableMarkupPayload
         * @extends bea.wlp.disc.xie.Events.Payloads.MarkupPayload
         * @see bea.wlp.disc.xie.Events.OnPrepareMarkup
         */
        $.Events.Payloads.MutableMarkupPayload = $.Events.Payloads.MarkupPayload.extend({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args, xhr, response, label, type, control) {
                this.sup(args, xhr, response, label, type, control);
            },

            /**
             * Sets the markup for this control.  This is typically used to modify the markup data returned by
             * <code>getControlMarkup</code>.  See the warnings specified in the documentation for
             * <code>bea.wlp.disc.xie.Events.OnPrepareMarkup</code> relating to the capability introduced by this
             * method.
             *
             * @method setControlMarkup
             * @param {string} markup
             *      The replacement for this control's existing response markup
             * @see bea.wlp.disc.xie.Events.Payloads.MarkupPayload#getControlMarkup
             * @see bea.wlp.disc.xie.Events.OnPrepareMarkup
             */
            setControlMarkup: function(markup) {
                this._control.markup = markup;
            }
        });

        /**
         * This payload is delivered when <code>OnPrepareContent</code> and <code>OnInjectContent</code> events are
         * fired.
         *
         * @class Events.Payloads.ContentPayload
         * @extends bea.wlp.disc.xie.Events.Payloads.MarkupPayload
         * @see bea.wlp.disc.xie.Events.OnPrepareContent
         * @see bea.wlp.disc.xie.Events.OnInjectContent
         */
        $.Events.Payloads.ContentPayload = $.Events.Payloads.MarkupPayload.extend({
            /**
             * @private
             * @method initialize
             */
            initialize: function(args, xhr, response, label, type, control, container) {
                this.sup(args, xhr, response, label, type, control);
                this._container = container;
            },

            /**
             * Returns an <code>HTMLElement</code> containing the DOM nodes being updated.  The container itself and its
             * parents <i>MUST</i> be considered opaque and <i>MUST NOT</i> be modified by receiving code.  Children
             * of the returned container node may be inspected and modified, but due to the nature of some XIE updates,
             * the exact structure of those children should not be rigidly relied upon, as some such child nodes may
             * also be considered opaque, implementation details of WLP.  Generic traversal and/or structurally agnostic
             * DOM operations such as <code>document.getElementById</code>, <code>document.getElementsByTagName</code>,
             * and JavaScript toolkit helpers like CSS-selector-based functions are useful mechanisms for safely and
             * reliably getting at content nodes in this structure.  Other methods of inspection may not return reliable
             * results in certain Disc execution environments, and should be used with caution, if at all.  Modification
             * of or reliance on the exact nature of the container object itself may result in undefined, unreliable
             * behavior, with the exception of its fundamental type (<code>HTMLElement</code>).
             * <p/>
             * See the warnings found in the documentation for the <code>OnPrepareContent</code> and
             * <code>OnInjectContent</code> events for for more information on the potential impact that may come from
             * modifying the contents of the container node.  While useful, such modifications may also be problematic
             * if not undertaken carefully.
             *
             * @method getControlContainer
             * @returns {HTMLElement}
             *      An opaque container containing the DOM subtree being updated
             * @see bea.wlp.disc.xie.Events.OnPrepareContent
             * @see bea.wlp.disc.xie.Events.OnInjectContent
             */
            getControlContainer: function() {
                return this._container;
            },

            /**
             * Returns a string representation of this payload for debugging purposes.  The exact format of this string
             * should not be relied upon, as it may change as XIE's payload implementations evolve.
             *
             * @method toString
             * @returns {string}
             *      A string describing this payload instance
             */
            toString: function() {
                return this.sup() + ", container.innerHTML: " + this._container.innerHTML;
            }
        });

        /**
         * This payload is delivered when <code>OnError</code> events are fired.
         *
         * @class Events.Payloads.ErrorPayload
         * @see bea.wlp.disc.xie.Events.OnError
         */
        $.Events.Payloads.ErrorPayload = bea.wlp.disc.Class.create({
            /**
             * @private
             * @method initialize
             */
            initialize: function(message, description, error) {
                this._message = message;
                this._description = description;
                this._error = error;
            },

            /**
             * Returns the message associated with the causal error, if any.
             *
             * @method getMessage
             * @returns {string}
             *      The error message, if specified
             */
            getMessage: function() {
                return this._message;
            },

            /**
             * Returns the description associated with the causal error, if any.
             *
             * @method getDescription
             * @returns {string}
             *      The error description, if specified
             */
            getDescription: function() {
                return this._description;
            },

            /**
             * If the error condition that spawned the event with which this payload is associated was caused by a
             * JavaScript <code>Error</code>, this method returns the causal error instance.
             *
             * @method getError
             * @returns {Error}
             *      The causal Error instance, if any
             */
            getError: function() {
                return this._error;
            },

            /**
             * Returns a string representation of this payload for debugging purposes.  The exact format of this string
             * should not be relied upon, as it may change as XIE's payload implementations evolve.
             *
             * @method toString
             * @returns {string}
             *      A string describing this payload instance
             */
            toString: function() {
                return "message: '" + this._message + "', description: '" + this._description
                    + (this._error ? "', error: " + this._error : "");
            }
        });
    }
});
});
bea.wlp.disc.Module._include("_Service", "bea.wlp.disc.xie", function() {
// bea.wlp.disc.xie[_Service]
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
    require: ["bea.wlp.disc._util", "bea.wlp.disc.xie._impl"],
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Impl = $Disc.xie._impl;
        var rewrite;
        
        $._Service = {
            onload: function() {
                $Impl.Rewrite.all();
                rewrite = true;
            },
            update: function(uri, formId) {
                var args = { uri: uri, async: true }
                if (formId) {
                    args.formNode = document.getElementById(formId);
                }
                new this.Gateway(args).send();
            },
            Gateway: $Disc.Class.create(function(Gateway) {
                this.initialize = function(args) {
                    this._args = (args || { });
                    this._args.rewrite = (this._args.rewrite || rewrite);
                }
                this.send = function(responders) {
                    new $Impl.Engine(this._args, responders).send();
                }
            })
        }
    },
    
    initialize: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;

        $.Events.OnError.addListener(function(payload) {
            var args = [L("[{0}] {1}", payload.getMessage(), payload.getDescription())];
            var err = payload.getError();
            if (err) {
                args.push(err);
            }
            $Disc.Console.error.apply($Disc.Console, args);
        });
    }
});
});
bea.wlp.disc.Module._include("Engine", "bea.wlp.disc.xie._impl", function() {
// bea.wlp.disc.xie._impl[Engine]
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
    require: ["bea.wlp.disc._util", "bea.wlp.disc.xie"],
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;
        var $Xie = $Disc.xie;

        $.Engine = $Disc.Class.create(function(Engine) {
            var active = 0;
            var count = 0;
            var pendingEvents = { };

            this.initialize = function(args, responders) {
                this._args = args;
                this._id = "" + count++;
                this._args.lifecycle = new $.Lifecycle(responders);
                this._args.error = this._args.lifecycle.onError;
                if (!this._args.noAsyncOverlay && this._args.async) {
                    function show() { if (active == 0) { $Xie.AsyncRequestOverlay._show(); } active += 1; }
                    function hide() { active -= 1; if (active == 0) { $Xie.AsyncRequestOverlay._hide(); } }
                    this._args.lifecycle.addLifecycleListener("onPrepareUpdate", show, hide);
                    this._args.lifecycle.addLifecycleListener("onCompleteUpdate", hide);
                    this._args.lifecycle.addLifecycleListener("onError", hide);
                }
                this._tagActivator = new $.TagActivator();
            }

            this.getId = function() {
                return this._id;
            }
            
            var current;
            var pending = [];

            this.send = function() {
                var self = this;
                this._args.load = function(xhr, text) {
                    // Only execute one engine's response handler at a time
                    if (self._args.lock) {
                        function doLoad() {
                            current = self;
                            self._load(xhr, text);
                        }
                        if (!current) {
                            doLoad();
                        }
                        else {
                            pending.push(doLoad);
                        }
                    }
                    else {
                        self._load(xhr, text);
                    }
                }
                if (this._args.lifecycle.onPrepareUpdate(this._args)) {
                    try {
                        $.Io.bind(this._args);
                    }
                    catch (e) {
                        this._args.lifecycle.onError(e);
                    }
                }
            }

            this.onCompleteUpdate = function() {
                try {
                    this._args.lifecycle.onCompleteUpdate(this._args, this._xhr, this._response);
                }
                finally {
                    if (this._args.lock) {
                        current = null;
                        if (pending.length > 0) {
                            pending.splice(0, 1)[0]();
                        }
                    }
                    Engine.destroyWorkspace(this._workspace);
                }
            }

            this.addPendingEvent = function(id, event) {
                pendingEvents[this._id] = (pendingEvents[this._id] || $Util.Object.map());
                pendingEvents[this._id].put(id, event);
            }

            this._load = function(xhr, text) {
                this._xhr = xhr;
                try {
                    var response = this._args.lifecycle.onHandleResponseText(text, this._tagActivator);
                    this._args.lifecycle.onHandleUpdate(this._args, xhr, response);
                    if (response.redirect) {
                        this._args.lifecycle.onRedirectUpdate(this._args, xhr, response);
                        window.location = response.redirect;
                    }
                    else {
                        var markups = [], scripts = [];
                        if (response.books) {
                            markups = markups.concat(response.books);
                        }
                        if (response.pages) {
                            markups = markups.concat(response.pages);
                        }
                        if (response.portlets) {
                            markups = markups.concat(response.portlets);
                        }
                        if (markups.length == 0) {
                            this.onCompleteUpdate();
                        }
                        else {
                            for (var i = 0, len = markups.length; i < len; i++) {
                                var processor = new $.MarkupProcessor(markups[i], this._args, xhr, response, this);
                                processor.process(scripts);
                            }
                            this._tagActivator.enqueue({ tags: scripts, container: this._getWorkspace(), temporal: true });
                            this._tagActivator.activate();
                        }
                    }
                }
                catch (e) {
                    if (e instanceof SyntaxError) {
                        throw L("Invalid response from server; please check server log for potential problems");
                    }
                    else {
                        this._args.lifecycle.onError(e);
                    }
                }
            }

            this._getWorkspace = function() {
                var parent = document.body || document.documentElement;
                return (this._workspace || (this._workspace = Engine.createWorkspace(parent, this._id)));
            }

            Engine.firePendingEvent = function(engineId, eventId) {
                var event = pendingEvents[engineId].remove(eventId);
                var size = pendingEvents[engineId].size();
                if (!size) {
                    delete pendingEvents[engineId];
                }
                event(size);
            }

            Engine.createWorkspace = function(parent, id) {
                id = $.$meta.getName("Engine.workspace_" + id);
                var workspace = document.getElementById(id);
                if (workspace) {
                    throw L("Workspace in use for id '{0}'", id);
                }
                workspace = document.createElement("div");
                workspace.id = id;
                workspace.style.display = "none";
                parent.appendChild(workspace);
                return workspace;
            }

            Engine.destroyWorkspace = function(workspace) {
                if (workspace) {
                    workspace.parentNode.removeChild(workspace);
                }
            }
        });

        $.MarkupProcessor = bea.wlp.disc.Class.create({
            initialize: function(envelope, args, xhr, response, engine) {
                this._validate(envelope);
                this._envelope = envelope;
                this._args = args;
                this._xhr = xhr;
                this._response = response;
                this._engine = engine;
                var parsed = $Util.Wlp.parseHookId(envelope.hookId);
                this._label = parsed.label;
                this._type = parsed.type;
                this._origHookId = envelope.hookId;
                this._args.lifecycle.onHandleMarkup(this._label, envelope);
                this._destination = document.getElementById(this._envelope.hookId);
            },

            process: function(scriptQueue) {
                if (this._envelope.returnToCaller) {
                    this._args.lifecycle.onForwardMarkup(this._xhr, this._envelope);
                    this._event = this._tryOnCompleteUpdate;
                    this._enqueueScripts(scriptQueue);
                }
                else if (this._destination) {
                    var workspace = this._getWorkspace();
                    $Util.Dom.clearContents(this._destination);
                    if (this._envelope.markup.length == 0) {
                        this._destination.style.visibility = "hidden";
                    }
                    else {
                        this._onPrepareMarkup();
                        this._destination.style.visibility = "visible";
                        this._innerHTML(workspace, this._envelope.markup);
                        var src = workspace;
                        var child = src.firstChild;
                        if (child && child.id == this._origHookId) {
                            src = child;
                        }
                        this._onPrepareContent(src);
                        $Util.Dom.purge(this._destination);
                        this._innerHTML(this._destination, src.innerHTML);
                        if (this._args.rewrite) {
                            $.Rewrite.all(this._destination);
                        }
                        $Util.Dom.clearContents(workspace);
                        this._enqueueScripts(scriptQueue);
                    }
                    this._event = this._onInjectContent;
                }
                else if (this._envelope.markup) {
                    throw L("Unable to locate tag with id '{0}'", this._envelope.hookId);
                }
            },
            
            _innerHTML: function(destination, markup) {
                var ieFixId;
                // Trick IE into honoring leading 'NoScope' tags (e.g. scripts) in markup inserted with innerHTML
                if (markup && $Util.Browser.IE) {
                    ieFixId = ($.$meta.getName("Engine.ie_fix_" + this._engine.getId() + "_" + this._label));
                    markup = "<div id='" + ieFixId + "' style='display: none;'>&nbsp;</div>" + markup;
                }
                destination.innerHTML = markup;
                // Remove the IE 'NoScope' fix div if it was added
                if (ieFixId) {
                    var ieFix = document.getElementById(ieFixId);
                    ieFix.parentNode.removeChild(ieFix);
                }
            },
            
            _validate: function(envelope) {
                function check(name) {
                    if (typeof envelope[name] == "undefined") {
                        throw L("Invalid server response: markup envelope lacks field '{0}'", name);
                    }
                }
                // Only check required fields
                check("hookId");
                check("contentType");
                check("markup");
            },

            _enqueueScripts: function(queue) {
                if (!this._envelope.returnToCaller) {
                    var tags = this._destination.getElementsByTagName("script");
                    $Util.Array.each(tags, function(i, tag) {
                        queue.push(tag);
                    });
                }
                var self = this, engineId = this._engine.getId(), eventId = this._label;
                this._engine.addPendingEvent(eventId, function(remaining) {
                    try {
                        self._event.call(self, remaining);
                    }
                    catch (e) {
                        self._args.lifecycle.onError(e);
                    }
                })
                queue.push({
                    name: "script",
                    attributes: [{ name: "type", value: "text/javascript" }],
                    text: "bea.wlp.disc.xie._impl.Engine.firePendingEvent('" + engineId + "', '" + eventId + "');"
                });
            },

            _getWorkspace: function() {
                var parent = this._destination.parentNode;
                var id = this._engine.getId() + "_" + this._label;
                return (this._workspace || (this._workspace = $.Engine.createWorkspace(parent, id)));
            },

            _onPrepareMarkup: function() {
                this._args.lifecycle.onPrepareMarkup(this._args, this._xhr, this._response, this._label, this._type, this._envelope);
            },

            _onPrepareContent: function(src) {
                this._args.lifecycle.onPrepareContent(this._args, this._xhr, this._response, this._label, this._type, this._envelope, src);
            },

            _onInjectContent: function(remaining) {
                try {
                    this._args.lifecycle.onInjectContent(this._args, this._xhr, this._response, this._label, this._type, this._envelope, this._destination);
                }
                finally {
                    this._tryOnCompleteUpdate(remaining);
                }
            },

            _tryOnCompleteUpdate: function(remaining) {
                $.Engine.destroyWorkspace(this._workspace);
                if (remaining == 0) {
                    try {
                        this._engine.onCompleteUpdate();
                    }
                    catch (e) {
                        self._args.lifecycle.onError(e);
                    }
                }
            }
        });
    }
});
});
bea.wlp.disc.Module._include("Io", "bea.wlp.disc.xie._impl", function() {
// bea.wlp.disc.xie._impl[Io]
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
    declare: function($, L) {
        var xhrSources = [
            function() { return new XMLHttpRequest(); },
            function() { return new ActiveXObject("Msxml2.XMLHTTP"); },
            function() { return new ActiveXObject("Microsoft.XMLHTTP"); },
            function() { return new ActiveXObject("Msxml2.XMLHTTP.4.0"); }
        ];
        // An index into the sources array for environment-optimized traversal
        var xhrIndex = (location.protocol == "file:" && window.ActiveXObject ? [1, 0, 2, 3] : [0, 1, 2, 3]);
        $.Io = {
            bind: function(args) {
                var uri = args.uri;
                var query = args.postdata;
                if (args.formNode) {
                    if ((args.formNode.method) && (!args.method)) {
                        args.method = args.formNode.method.toUpperCase();
                    }
                    query = this._encodeForm(args.formNode);
                }
                args.method = (args.method ? args.method.toUpperCase() : "GET");
                var async = !!args.async;
                var xhr = this._getXhr();
                var headerName;
                var loadCalled;
                if (xhr) {
                    xhr.onreadystatechange = function() {
                        loadCalled = $.Io._load(args, xhr);
                    }
                    var reqUri;
                    var postData;
                    if (args.method == "POST") {
                        reqUri = uri;
                        postData = query;
                    }
                    else {
                        var queryUri = uri;
                        if (query) {
                            queryUri += (uri.indexOf("?") > -1 ? "&" : "?") + query;
                        }
                        reqUri = queryUri;
                        postData = null;
                    }
                    if(args.user && args.password) {
                        xhr.open(args.method, reqUri, async, args.user, args.password);
                    }
                    else {
                        xhr.open(args.method, reqUri, async);
                    }
                    if (postData) {
                        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    }
                    xhr.setRequestHeader("x-bea-netuix-xhr", "true");
                    if (args.headers) {
                        for (headerName in args.headers) {
                            if(args.headers.hasOwnProperty(headerName.toString())) {
                                xhr.setRequestHeader(headerName, args.headers[headerName]);
                            }
                        }
                    }
                    xhr.send(postData);
                    if (!async && !loadCalled) {
                        this._load(args, xhr);
                    }
                }
                else {
                    throw L("XMLHttpRequest not available");
                }
            },
            _getXhr: function() {
                var xhr;
                for (var i = 0; i < xhrIndex.length; i++) {
                    try {
                        xhr = xhrSources[xhrIndex[i]]();
                        xhrIndex.splice(0, xhrIndex.length, xhrIndex[i]);
                        break;
                    }
                    catch (ignore) { }
                }
                return xhr;
            },
            _load: function(args, xhr) {
                var loaded;
                try {
                    if (xhr.readyState < 4) {
                        // @review Decouple lifecycle from this module facet
                        args.lifecycle.onChangeXhrReadyState(xhr);
                    }
                    else if (xhr.readyState == 4) {
                        var ctype = xhr.getResponseHeader("Content-Type");
                        if (ctype && ctype.indexOf("text/x-netuix-json-comment-filtered") == 0) {
                            var code;
                            try {
                                code = xhr.status;
                            }
                            catch (ignore) {
                                throw L("Service not available");
                            }
                            if (code == 200) {
                                args.load(xhr, xhr.responseText);
                            }
                            else {
                                var status = xhr.statusText, body = xhr.responseText;
                                throw L("Unexpected response: status: '{0}', code: '{1}', body: '{2}'", status, code, body);
                            }
                        }
                        else {
							if (!args.xpr) {
                                 // Not a WLP response; let the user handle it explicitly if desired
                                 // @review Decouple lifecycle from this module facet
                                 args.lifecycle.onAbandonUpdate(args, xhr);
                             }
                            // @review Decouple lifecycle from this module facet
                            args.lifecycle.onCompleteUpdate(args, xhr);
                        }
                        loaded = true;
                    }
                }
                catch (e) {
                    args.error(e);
                }
                return loaded;
            },
            _encodeForm: function(formNode) {
                var values = [];
                var name;
                for (var i = 0; i < formNode.elements.length; i++) {
                    var element = formNode.elements[i];
                    if (element.disabled || element.tagName.toLowerCase() == "fieldset") {
                        continue;
                    }
                    if (element.tagName.toLowerCase() == "input") {
                        name = encodeURIComponent(element.name);
                        var type = element.type.toLowerCase();
                        switch (type) {
                            case "radio":
                            case "checkbox":
                                if (element.checked) {
                                    values.push(name + "=" + encodeURIComponent(element.value));
                                }
                                break;
                            case "button":
                            case "file":
                            case "image":
                            case "reset":
                            case "submit":
                                break;
                            default:
                                values.push(name + "=" + encodeURIComponent(element.value));
                        }
                    }
                    if (element.tagName.toLowerCase() == "textarea") {
                        name = encodeURIComponent(element.name);
                        values.push(name + "=" + encodeURIComponent(element.value));
                    }
                    if (element.tagName.toLowerCase() == "select") {
                        name = encodeURIComponent(element.name);
                        for (var j = 0; j < element.options.length; j++) {
                            if (!element.options[j].disabled && element.options[j].selected) {
                                var value = element.options[j].value || element.options[j].text;
                                values.push(name + "=" + encodeURIComponent(value));
                            }
                        }
                    }
                }
                return values.join("&");
            }
        }
    }
});
});
bea.wlp.disc.Module._include("Lifecycle", "bea.wlp.disc.xie._impl", function() {
// bea.wlp.disc.xie._impl[Lifecycle]
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
    require: ["bea.wlp.disc._util", "bea.wlp.disc.xie"],
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;
        var $Xie = $Disc.xie;
        
        $.Lifecycle = $Disc.Class.create(function() {
            this.initialize = function(responders) {
                responders = (responders || { });
                this.onHandleResponseText = responders.onHandleResponseText || function(text) {
                    return $Util.Json.parse(text);
                };
                this.onChangeXhrReadyState = responders.onChangeXhrReadyState || $Util.Function.nop;
                this.onHandleMarkup = responders.onHandleMarkup || $Util.Function.nop;
                this.onForwardMarkup = responders.onForwardMarkup || $Util.Function.nop;
                var self = this;
                $Util.Object.each($Xie.Events, function(name) {
                    if (name.indexOf("On") >= 0 && name != "OnError") {
                        var eventFn = name.replace(/^On/, "on");
                        self[eventFn] = function() {
                            var result;
                            try {
                                var event = $Xie.Events[name];
                                var payload = event._payload.apply(event, arguments);
                                if (responders && typeof responders[eventFn] == "function") {
                                    responders[eventFn].call(self, payload);
                                }
                                result = event._fire.call(event, payload);
                            }
                            catch (e) {
                                self.onError(e);
                            }
                            return result;
                        }
                    }
                });
            }
            this.onError = function(exception) {
                var message = L("EXCEPTION"), description, result;
                if (exception.name && exception.message) {
                    description = L("{0}: {1}", exception.name, exception.message);
                }
                else {
                    description = exception;
                }
                try {
                    var error;
                    if (exception instanceof Error) {
                        error = (window.console && window.console.firebug ? exception : new $Util.Error(exception, 1));
                    }
                    var payload = $Xie.Events.OnError._payload(message, description, error);
                    result = $Xie.Events.OnError._fire(payload);
                }
                catch (e) {
                    var msg;
                    if (e.name && e.message) {
                        msg = L("{0}: {1}", e.name, e.message);
                    }
                    else {
                        msg = e;
                    }
                    var orig = L("{0}: {1}", message, description);
                    var log = L("Nested error [{0}] when firing OnError event with error [{1}]", msg, orig);
                    $Disc.Console.error(log, e instanceof Error ? e : undefined);
                }
                return result;
            }
            this.addLifecycleListener = function(stage, action, cancelAction) {
                if (stage && typeof action == "function") {
                    var lifecycle = this;
                    var orig = lifecycle[stage];
                    lifecycle[stage] = function() {
                        var result;
                        action.apply(lifecycle, arguments);
                        if (orig) {
                            result = orig.apply(lifecycle, arguments);
                            if (typeof result == "boolean" && !result && typeof cancelAction == "function") {
                                cancelAction.apply(lifecycle, arguments);
                            }
                        }
                        return result;
                    }
                }
            }
        });
    }
});
});
// bea.wlp.disc.xie._impl
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

bea.wlp.disc.Module.create("bea.wlp.disc.xie._impl", {
    include: ["Engine", "Io", "Lifecycle", "Rewrite", "TagActivator"]
});
bea.wlp.disc.Module._include("Rewrite", "bea.wlp.disc.xie._impl", function() {
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
});
bea.wlp.disc.Module._include("TagActivator", "bea.wlp.disc.xie._impl", function() {
// bea.wlp.disc.xie._impl[TagActivator]
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
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;

        function createTag(src) {
            var tag = document.createElement(src.name);
            $Util.Array.each(src.attributes, function(i, attr) {
                tag.setAttribute(attr.name, attr.value);
            });
            if (src.text && src.text != "null") {
                if (typeof tag.appendChild == "function") {
                    var node = document.createTextNode(src.text);
                    tag.appendChild(node);
                }
                else {
                    tag.text = src.text;
                }
            }
            return tag;
        }

        function activateTag(tag, container, onload) {
            if (tag.parentNode) {
                var clone = createTag({
                    name: tag.nodeName,
                    text: (tag.text || tag.textContent || tag.innerHTML),
                    attributes: (function() {
                        var attributes = [];
                        $Util.Array.each(tag.attributes, function(i, attr) {
                            if (attr.specified) {
                                attributes.push({ name: attr.name, value: attr.value });
                            }
                        });
                        return attributes;
                    })()
                });
                onload && (clone.onload = clone.onerror = onload);
                tag.parentNode.replaceChild(clone, tag);
                tag = clone;
            }
            else {
                onload && (tag.onload = tag.onerror = onload);
                container.appendChild(tag);
            }
            return tag;
        }
        
        $.TagActivator = $Disc.Class.create(function() {
            this.initialize = function() {
                this._tagBundles = [];
            }

            this.enqueue = function(tagBundle) {
                // @review We might be able to improve client rendering performance if we immediately activate
                //         non-script tags here (i.e. links and styles)
                this._tagBundles.push(tagBundle);
            }

            this.activate = function() {
                var self = this;
                (function(bundleIndex) {
                    if (bundleIndex >= self._tagBundles.length) {
                        self._tagBundles = [];
                    }
                    else {
                        var bundle = self._tagBundles[bundleIndex], activateBundle = arguments.callee;
                        var container = bundle.container, tags = bundle.tags, temporal = bundle.temporal;
                        (function(tagIndex) {
                            if (tagIndex >= tags.length) {
                                activateBundle(bundleIndex + 1);
                            }
                            else {
                                var tag, activateTags = arguments.callee;
                                if (tags[tagIndex].nodeName) {
                                    tag = tags[tagIndex];
                                }
                                else {
                                    tag = createTag(tags[tagIndex]);
                                    if ($Util.Browser.IE.isPre7 && tag && temporal &&
                                        tags[tagIndex].name.toLowerCase() == "script") {
                                        tag = null;
                                        function evaluate(text) {
                                            if (typeof text == "string") {
                                                try {
                                                    window.execScript(text.replace(/^\s*((<!--)|(<!CDATA\[))/g, ""));
                                                }
                                                catch (e) {
                                                    $Disc.Console.error(e.message);
                                                }
                                            }
                                        }
                                        var src;
                                        $Util.Array.each(tags[tagIndex].attributes, function(tagIndex, attr) {
                                            if (attr.name == "src") {
                                                src = attr.value;
                                            }
                                        });
                                        if (src) {
                                            try {
                                                new $Disc._Request("GET", src, true).send(function(xhr) {
                                                    if (xhr.readyState == 4) {
                                                        if (xhr.status == 200) {
                                                            evaluate(xhr.responseText);
                                                        }
                                                        else {
                                                            $Disc.Console.warn(L("{0}: {1}", xhr.status, src));
                                                        }
                                                        activateTags(tagIndex + 1);
                                                    }
                                                });
                                            }
                                            catch (e) {
                                                $Disc.Console.warn(e.message);
                                                activateTags(tagIndex + 1);
                                            }
                                        }
                                        else if (tags[tagIndex].text) {
                                            evaluate(tags[tagIndex].text);
                                            activateTags(tagIndex + 1);
                                        }
                                    }
                                }
                                if (tag) {
                                    if (tag.nodeName.toLowerCase() == "script") {
                                        function scriptLoaded() {
                                            try {
                                                activateTags(tagIndex + 1);
                                            }
                                            catch (e) {
                                                $Disc.Console.error(e.message);
                                            }
                                        }
                                        tag = activateTag(tag, container, function() {
                                            scriptLoaded();
                                        });
                                        if (tag.src && tag.readyState) {
                                            if (!/loaded|complete/.test(tag.readyState)) {
                                                // @review Can we detect 404s?  If not, this _unsupported_ backdoor
                                                //         allows 404ing external script references to be configured to
                                                //         timeout in this code path; value must be defined in
                                                //         milliseconds
                                                var timeout;
                                                if ($.Config && typeof $.Config.scriptLoadTimeoutHint == "number") {
                                                    timeout = setTimeout(function() {
                                                        scriptLoaded();
                                                    }, $.Config.scriptLoadTimeoutHint);
                                                }
                                                tag.onreadystatechange = function() {
                                                    if (/loaded|complete/.test(tag.readyState)) {
                                                        if (timeout) {
                                                            clearTimeout(timeout);
                                                        }
                                                        scriptLoaded();
                                                    }
                                                }
                                            }
                                            else {
                                                scriptLoaded();
                                            }
                                        }
                                        else if (!tag.src) {
                                            scriptLoaded();
                                        }
                                    }
                                    else {
                                        activateTag(tag, container);
                                        activateTags(tagIndex + 1);
                                    }
                                }
                            }
                        })(0);
                    }
                })(0);
            }
        });
    }
});
});
// bea.wlp.disc.publishing
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
 * @name bea.wlp.disc.publishing
 * @overview
 *      This module provides APIs to facilitate Portlet Publishing; notably, the <code>PortletSource</code> API, which
 *      allows portlet instances to be surfaced in non-WLP-based host HTML pages.
 */
bea.wlp.disc.Module.create("bea.wlp.disc.publishing", {
    include: ["PortletSource"]
});
bea.wlp.disc.Module._include("PortletSource", "bea.wlp.disc.publishing", function() {
// bea.wlp.disc.publishing[PortletSource]
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
    require: ["bea.wlp.disc._util", "bea.wlp.disc.xie"],
    declare: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;
        var $Xie = $Disc.xie;
        
        var activatedTags = [];
        
        /**
         * The <code>PortletSource</code> API is an Ajax-based front end to the Portlet Publishing feature of WLP.
         * This feature provides a mechanism for dynamically injecting interactive, published portlet instance markup
         * into arbitrary host HTML pages, though browser security restrictions and the XMLHttpRequest-based subsystem
         * on which this API is based require that the host HTML and portal be served from the same domain, according
         * to the browser-enforced same-origin policy.
         * <p/>
         * This API handles communication with portlet publishing contexts to request and consume portlet instances.
         * Use of this API begins by acquiring a <code>PortletSource</code> instance using the static <code>get</code>
         * factory method as follows:
         * <pre>
         *      var publishingContext = "/&lt;webapp&gt;/appmanager/&lt;portal&gt;/&lt;desktop&gt;";
         *      var source = bea.wlp.disc.publishing.PortletSource.get(publishingContext);
         * </pre>
         * The value of the <code>publishingContext</code> variable in the above example can be obtained from the Portal
         * Admin Tools, and may address either streaming- or file-mode portals.  The factory method returns the
         * same <code>PortletSource</code> instance for a given publishing context value each time it is called.  The
         * publishing context matches the <code>context</code> parameter passed to Portlet Publishing Service URLs, as
         * described elsewhere (see references below).
         * <p/>
         * Once a <code>PortletSource</code> instance has been obtained, that instance can be used to surface published
         * portlets in the current document by calling its <code>render</code> method, as follows:
         * <pre>
         *      source.render({ portlet: "myInstanceLabel", to: "myElementId" });
         * </pre>
         * In this example, the <code>portlet</code> arg is the instance label of a portlet available in the specified
         * publishing context, and the <code>to</code> arg is the id of an HTML element within the current document.
         * Typically it works well for this element to be an HTML <code>div</code> tag, but other element types will
         * work -- your mileage may vary when using other tag types, given the partculars of individual browsers, so it's
         * recommended that <code>div</code> tags be used whenever possible.
         * <p/>
         * The execution of the render call proceeds asynchronously, based on the Disc XIE subsystem.  Because this
         * API is implemented on the XIE backbone, XIE events are fired and can be used to monitor the progress of
         * and/or respond to the loading of published portlet content.
         * <p/>
         * Each invocation of the <code>render</code> method of a <code>PortletSource</code> instance results in an
         * independent request to the server.  Multiple portlets can be batched together in a single render operation
         * so that the number of requests per page is minimized when multiple portlets are required on a single page.
         * This behavior can also be used to tailor the behavior of the host page's published portlet loading to
         * optimize page loading on a per-portlet/portlet-group basis.
         * <p/>
         * The following is an example of loading multiple portlets in a single render operation:
         * <pre>
         *      source.render([
         *          { portlet: "myInstanceLabel1", to: "myElementId1" },
         *          { portlet: "myInstanceLabel2", to: "myElementId2" },
         *          { portlet: "myInstanceLabel3", to: "myElementId3" }
         *      ]);
         * </pre>
         * In this example, a single request is made to the server to load content from three published portlets.  The
         * consequence of this is that a single response is returned to the client containing all three portlets'
         * contents, which means that, before the client can be fully updated, the server must have completed rendering
         * all three portlets' markup.  This strategy can be useful when trying to reduce the impact of the "1 + n"
         * request problem when initializing the host page (where the "1" request loads the host page, and the "n"
         * represents each portlet being loaded after the initial page load; in this example, 4 potential requests are
         * reduced to a total of 2).
         * <p/>
         * Multiple concurrent render calls can also be made, as needed.  For example:
         * <pre>
         *      source.render({ portlet: "slowPortlet1", to: "myElementId1" });
         *      source.render([
         *          { portlet: "fastPortlet2", to: "myElementId2" },
         *          { portlet: "fastPortlet3", to: "myElementId3" }
         *      ]);
         * </pre>
         * This style may be useful when one or more of the portlets on the page (in this example,
         * the portlet with label "slowPortlet1") takes a non-trivial amount of time to render on the server;
         * the concurrent request style can mitigate this by loading the quickly responding portlets as soon as possible
         * in a single batched request, while allocating the potentially slowly loading portlet its own request.  Of
         * course, browser XHR implementations may limit how many concurrent requests may be simultaneously active
         * given their own, internal request queueing implementation details.
         * <p/>
         * Multiple <code>PortletSource</code> instances can be simultaneously used to surface published portlets within
         * the same document, as well.  This can be useful to integrate or mashup available content from multiple
         * portals within the host document.  For example:
         * <pre>
         *      var $PortletSource = bea.wlp.disc.publishing.PortletSource;
         *      var source1 = $PortletSource.get("/&lt;webapp&gt;/appmanager/&lt;portal&gt;/&lt;desktop1&gt;");
         *      var source2 = $PortletSource.get("/&lt;webapp&gt;/appmanager/&lt;portal&gt;/&lt;desktop2&gt;");
         *      source1.render({portlet: "portlet1", to: "myElementId1"});
         *      source2.render([
         *          { portlet: "portlet2", to: "myElementId2" },
         *          { portlet: "portlet3", to: "myElementId3" }
         *      ]);
         * </pre>
         * <p/>
         * Portlets aggregated in this manner should be especially well-behaved in aggregated contexts, and the
         * developer surfacing portlets from multiple sources in the same document should be keenly aware of the
         * target portlets' impact on the global document execution context.  Best practices in namespacing should
         * be rigorously observed for all aspects of the surfaced portlet including HTML element ids, script variable
         * naming, and so on.  Namespacing in a single portal context is complicated enough; when aggregating
         * from multiple portals, however, the risk of naming collisions is amplified.  In particular, while the
         * uniqueness of portlet instance labels is enforced within the context of a single portal, this mechansim
         * allows the co-mingling of portlets with identical instance labels from different sources; if this is
         * attempted, due diligence should be applied in ensuring that multi-source aware namespacing is applied by
         * all involved portlets.
         * <p/>
         * Note that multiple attempts to render a single published portlet from a single source should be stable, so
         * long as each attempt renders to the same target HTML element.  Re-mapping the same instance to multiple
         * target elements either across multiple render calls or during a single, aggregate render call is not
         * supported, and may result in undefined behavior.  Similarly, mapping multiple instances to a single target
         * element may result in undefined behavior and is not supported.
         * <p/>
         * Attempting to publish portlets through this API from publishing contexts with URL compression enabled is
         * <i>not</i> supported.
         * <p/>
         * Portlets published through this API will have their associated render dependencies applied to the host
         * document, although, at this time, associated script dependencies are not propagated or supported.  Integrated
         * render dependencies are designed to only be integrated and executed a single time per document, no matter how
         * many times the portlet defining them is explicitly rendered.
         * <p/>
         * Lastly, <code>PortletSource.render</code> should only ever be invoked once the document is known to have
         * completely loaded.  As with all cross-browser DOM manipulation APIs, undefined/unsupported results may occur
         * if invoked before the inital page load has completed and the base DOM has materialized in full.  As such,
         * any code invoking this API should only be executed from appropriate window or toolkit onload events (or in
         * response to user actions at some later time).
         * <p/>
         * For in-depth coverage of this API, see the WLP Client-Side Development Guide.
         *
         * @class PortletSource
         * @see bea.wlp.disc.xie.Events
         */
        $.PortletSource = $Disc.Class.create(function(PortletSource) {
            var instances = { };
            var lookup = { };
            
            function key(context, label) {
                return [context, label].join("|");
            }

            /**
             * @private
             * @method initialize
             * @param {string} publishingContext
             *      The publishing context with which this instance is associated
             */
            this.initialize = function(publishingContext) {
                this._context = publishingContext;
                this._portlets = { };
            }

            /**
             * Renders the specified portlet specification.  See class-level documentation for examples and details.
             *
             * @method render
             * @param {object|Array} spec
             *      A portlet specification object or array of such specifiactions, where each specification object
             *      contains both <code>portlet</code> and <code>to</code> values, as described above
             */
            this.render = function(spec) {
                var self = this;
                if (spec) {
                    spec = spec instanceof Array ? spec : [spec];
                    $Util.Array.each(spec, function(i, p) {
                        self._portlets[p.portlet] = p;
                    });
                    new $Xie._Service.Gateway({
                        uri: this._getServiceUri(spec),
                        async: true,
                        rewrite: true,
                        lock: true,
                        headers: { "x-bea-netuix-xhr-type": "source" }
                    }).send({
                        onHandleResponseText: function(text, tagActivator) {
                            var response = { };
                            envelopes = $Util.Json.parse(text);
                            var rdTags = [];
                            if (envelopes.portlets) {
                                $Util.Array.each(envelopes.portlets, function(i, envelope) {
                                    if (envelope.markup) {
                                        // Transform service response into an XIE response
                                        var sub = $Util.Json.parse(envelope.markup);
                                        sub.redirect && (response.redirect = sub.redirect);
                                        if (sub.portlets instanceof Array) {
                                            response.portlets = (response.portlets || []);
                                            response.portlets.push.apply(response.portlets, sub.portlets);
                                        }
                                        // Set up source lookup table to handle outbound XIE requests
                                        $Util.Array.each(response.portlets, function(i, portlet) {
                                            lookup[key(self._context, envelope.portlet)] = self;
                                        });
                                        // Append render dependencies tags and eliminate global redundancy
                                        if (sub.renderDependencies && sub.renderDependencies.tags instanceof Array) {
                                            $Util.Array.each(sub.renderDependencies.tags, function(j, subtag) {
                                                subtag.attributes = (subtag.attributes || []);
                                                var match;
                                                $Util.Array.each(activatedTags.concat(rdTags), function(i, tag) {
                                                    tag.attributes = (tag.attributes || []);
                                                    if (!match && tag.name == subtag.name && tag.text == subtag.text &&
                                                        tag.attributes.length == subtag.attributes.length) {
                                                        var different = tag.attributes.length;
                                                        $Util.Array.each(tag.attributes, function(j, attr) {
                                                            $Util.Array.each(subtag.attributes, function(j, subattr) {
                                                                if (attr.name == subattr.name && attr.value == subattr.value) {
                                                                    different--;
                                                                }
                                                            });
                                                        });
                                                        match = !different;
                                                    }
                                                });
                                                if (!match) {
                                                    rdTags.push(subtag)
                                                }
                                            });
                                        }
                                    }
                                    else if (envelope.error) {
                                        // @review Fire XIE OnError event?
                                        var err = L("Unable to render portlet '{0}': {1}", envelope.portlet, envelope.error);
                                        bea.wlp.disc.Console.error(err);
                                    }
                                });
                            }
                            if (envelopes.error) {
                                // @review Fire XIE OnError event?
                                var err = L("Unable to render portlets : {0}", envelopes.error);
                                bea.wlp.disc.Console.error(err);
                            }
                            tagActivator.enqueue({ container: $Util.Dom.getHeadTag(), tags: rdTags });
                            activatedTags = activatedTags.concat(rdTags);
                            return response;
                        },
                        onHandleMarkup: this._getMapper()
                    });
                }
            }

            /**
             * Returns an appropriate Portlet Publishing Service URI.
             *
             * @method _getServiceUri
             * @param {Array} specs
             *      The array of specification for which the URI should be generated
             * @returns {string}
             *      The service URI for the specified spec array
             */
            this._getServiceUri = function(specs) {
                var ctx = this._context, labels = [];
                $Util.Array.each(specs, function(i, spec) { labels.push(spec.portlet); });
                return (new $Util.Uri({
                    pathName: ctx.split("/").slice(0, 2).join("/") + "/bea/wlp/api/portlet/publish",
                    params : { context: ctx, portlet: labels }
                })).toString();
            }
            
            /**
             * Returns a function for mapping response markup fragments to the desired target elements in the host page.
             *
             * @method _getMapper
             * @returns {function}
             *      The label-to-element-id mapping function
             */
            this._getMapper = function() {
                var specs = this._portlets;
                return function(label, envelope) {
                    $Util.Object.each(specs, function(k, spec) {
                        k == label && (envelope.hookId = spec.to);
                    });
                }
            }

            /**
             * This factory method returns a <code>PortletSource</code> instance for the specified publishing context.
             * See class-level documentation for more information on this method's use.
             *
             * @static
             * @method get
             * @param {string} publishingContext
             *      The publishing context for which a <code>PortletSource</code> instance is desired
             * @returns {bea.wlp.disc.publishing.PortletSource}
             *      The <code>PortletSource</code> instance associated with the specified publishing context
             */
            PortletSource.get = function(publishingContext) {
                var key = new $Util.Uri(publishingContext).getPathName();
                return (key ? instances[key] || (instances[key] = new PortletSource(key)) : undefined);
            }

            /**
             * Looks up a <code>PortletSource</code> instance from an outbound XIE request URI.  Used to map subsequent
             * XIE interaction responses with published portlets to their target elements.
             *
             * @static
             * @method _find
             * @param {string} outbound
             *      The outbound URI to use for the instance look up
             * @returns {bea.wlp.disc.publishing.PortletSource}
             *      The instance found, if found
             */
            PortletSource._find = function(outbound) {
                var uri = new $Util.Uri(outbound);
                return lookup[key(uri.getParam("_portlet.portalUrl"), uri.getParam("_windowLabel"))];
            }
        });
    },

    initialize: function($, L) {
        var $Disc = bea.wlp.disc;
        var $Util = $Disc._util;
        var $Xie = $Disc.xie;

        $Xie.Events.OnPrepareUpdate.addListener(function(payload) {
            // Decorate outbound XIE interactions with the instruction(s) necessary to notify the server that this is a
            // PortletSource API request; also set up additional response handling to map responses to their host
            // destinations; lastly, configure rewriting for response markup
            var source = $.PortletSource._find(payload.getRequestUri());
            if (source) {
                payload.setRequestHeader("x-bea-netuix-xhr-type", "source");
                payload._setRewrite(true);
                payload._addLifecycleListener("onHandleMarkup", source._getMapper());
            }
        });
    }
});
});
// bea.wlp.disc.io
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
 * @name bea.wlp.disc.io
 * @overview
 *      The package provides low-level I/O Disc APIs; particularly the "portal-aware" XMLHttpRequest class.
 */
bea.wlp.disc.Module.create("bea.wlp.disc.io", {
    include: ["XMLHttpRequest"]
});
bea.wlp.disc.Module._include("XMLHttpRequest", "bea.wlp.disc.io", function() {
// bea.wlp.disc.io[XMLHttpRequest]
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
    require: ["bea.wlp.disc.xie"],
    declare: function($, L) {

        /**
         * This class implements a "portal-aware" wrapper around native browser <code>XMLHttpRequest</code>
         * implementations.  Passing portlet action URLs to instances of this class's <code>open</code> method allows
         * such actions to be invoked by Ajax client code, which can then make use of the markup-fragment- or
         * data-oriented responses that may be generated, in typical Ajax-fashion.  The behavior resulting from passing
         * other portal URLs to such instances may result in undefined or undesirable behavior.  Non-portal URLs will
         * simply pass-through to the browser's native <code>XMLHttpRequest</code> implementation.
         * <p/>
         * This class is implemented to be as conforming to the
         * <a href="http://www.w3.org/TR/XMLHttpRequest/">XMLHttpRequest specification</a> as possible.
         * <p/>
         * This object also is provided in the <code>bea.netuix.ajax.client</code> namespace for backward compatibility,
         * though that namespace is considered to be deprecated for purposes of accessing of this class.
         * <p/>
         * This class is implemented as a thin layer on top of Disc's XIE module.  As such, interactions made via
         * instances of this class may cause any or all of the global XIE events to fire (though XIE's UI-blocking
         * overlay is automatically suppressed for such interactions).
         *
         * @class XMLHttpRequest
         * @see bea.wlp.disc.xie.Events
         */
        $.XMLHttpRequest = function() {
            var _requestHeaders = null;
            var _method;
            var _uri;
            var _async = true;
            var _respData;
            var _orig;
            var _aborted = false;
            var _user = null;
            var _password = null;

            /**
             * @field {function}
             *      A settable callback function, akin to its peer in native XHR implementations
             */
            this.onreadystatechange = undefined;

            /**
             * @field {number}
             *      A response status code, akin to its peer in native XHR implementations
             */
            this.status = undefined;

            /**
             * @field {string}
             *      The response status text, akin to its peer in native XHR implementations
             */
            this.statusText = undefined;

            /**
             * @field {string}
             *      The response text, akin to its peer in native XHR implementations
             */
            this.responseText = undefined;

            /**
             * @field {object}
             *      The response XML, akin to its peer in native XHR implementations
             */
            this.responseXML = undefined;

            /**
             * @field {number}
             *      The request ready state, akin to its peer in native XHR implementations
             */
            this.readyState = 0;

            function verifyState() {
                if (this.readyState == 0) {
                    throw "open() not called yet";
                }
                else if (this.readyState != 1) {
                    throw "Currently sending";
                }
            }

            /**
             * Sets a custom request header, akin to its peer in native XHR implementations.
             *
             * @method setRequestHeader
             * @param {string} name
             *      The name of the request header to set
             * @param {string} value
             *      The value of the request header to set
             */
            this.setRequestHeader = function(name, value) {
                verifyState.call(this);
                if (_requestHeaders == null) {
                    _requestHeaders = { };
                }
                _requestHeaders[name] = value;
            }

            /**
             * Prepares this instance to make a request, akin to its peer in native XHR implementations.
             *
             * @method open
             * @param {string} method
             *      The method (e.g. 'POST', 'GET', etc) to use when making the request
             * @param {string} loc
             *      The URL to request
             * @param {boolean} [async]
             *      Whether or not the request should be made asynchronously
             * @param {string} [user]
             *      A user name, for HTTP authentication scenarios
             * @param {string} [password]
             *      A password, for HTTP authentication scenarios
             */
            this.open = function(method, loc, async, user, password) {
                _requestHeaders = null;
                _respData = null;
                _orig = null;
                _aborted = false;

                this.status = undefined;
                this.statusText = undefined;
                this.responseText = undefined;
                this.responseXML = undefined;
                this.readyState = 1;

                _method = method;
                _async = async;
                _uri = undoUriRewriting(loc);
                _user = user;
                _password = password;
            }

            /**
             * Send the prepared request, akin to its peer in native XHR implementations.
             *
             * @method send
             * @param {string} [data]
             *      Optional post data to send with the request
             */
            this.send = function(data) {
                verifyState.call(this);

                var func = this.onreadystatechange;
                var self = this;
                var args = {
                    uri: _uri,
                    async: _async,
                    method: _method,
                    headers: _requestHeaders,
                    postdata: data,
                    user: _user,
                    password: _password,
                    noAsyncOverlay: true,
					xpr: true
                };

                // Execute request
                new bea.wlp.disc.xie._Service.Gateway(args).send({
                    onPrepareUpdate: function(payload) {
                        payload.setRequestHeader("x-bea-rt-client", "true");
                    },
                    onChangeXhrReadyState: function(xhr, slave) {
                        if (!_aborted) {
                            self.readyState = xhr.readyState;
                            if (self.readyState > 2) {
                                self.status = 200;
                                self.statusText = "OK";
                            }
                            if (self.readyState == 3) {
                                delete self.responseText;
                            }
                            if (func && !slave) {
                                func();
                            }
                        }
                    },
                    onForwardMarkup: function(xhr, envelope) {
                        if (!_aborted) {
                            this.onChangeXhrReadyState(xhr, true);
                            _respData = {
                                contentType: envelope.contentType,
                                markup: envelope.markup,
                                headers: envelope.headers
                            };
                            if (_respData.contentType && isXML(_respData.contentType)) {
                                self.responseXML = parseXML(_respData.markup, _respData.contentType);
                            }
                            self.responseText = _respData.markup;
                            if (func) {
                                func();
                            }
                        }
                    },
                    onCompleteUpdate: function(payload) {
                        if (!_aborted && !_respData) {
                            _orig = payload._getXhr();
                            this.onChangeXhrReadyState(_orig, true);
                            if (_orig.responseXML) {
                                self.responseXML = _orig.responseXML;
                            }
                            self.responseText = _orig.responseText;
                            if (func) {
                                func();
                            }
                        }
                    }
                });
            }

            /**
             * Aborts the request, akin to its peer in native XHR implementations.
             *
             * @method abort
             */
            this.abort = function() {
                _requestHeaders = null;
                _method = "GET";
                _uri = null;
                _async = true;
                _respData = null;
                _aborted = true;

                this.status = undefined;
                this.statusText = undefined;
                this.responseText = undefined;
                this.responseXML = undefined;
                this.readyState = 0;
            }

            function verifyNotAborted() {
                if (_aborted) {
                    throw "Aborted";
                }
            }

            /**
             * Returns a response header, by name, akin to its peer in native XHR implementations.
             *
             * @method getResponseHeader
             * @param {string} name
             *      The name of the response header
             * @returns {string}
             *      The value of the named response header, if available
             */
            this.getResponseHeader = function(name) {
                verifyNotAborted();
                if (_orig) {
                    return _orig.getResponseHeader(name);
                }
                else {
                    if (!_respData) {
                        return null;
                    }
                    if (name.toLowerCase() == "content-type") {
                        return _respData.contentType;
                    }
                    else {
                        if (_respData.headers) {
                            return _respData.headers[name];
                        }
                    }
                }
            }

            /**
             * Returns a string containing all response headers, akin to its peer in native XHR implementations.
             *
             * @method getAllResponseHeaders
             * @returns {string}
             *      All response headers in string representation, if available
             */
            this.getAllResponseHeaders = function() {
                verifyNotAborted();
                if (_orig) {
                    return _orig.getAllResponseHeaders();
                }
                else {
                    if (!_respData) {
                        return "";
                    }
                    var str = "Content-Type: " + _respData.contentType;
                    if (_respData.headers) {
                        for (var headerName in _respData.headers) {
                            str = str + "\n" + headerName + ": " + _respData.headers[headerName];
                        }
                    }
                    return str;
                }
            }

            function undoUriRewriting(orig) {
                if (typeof orig != "string") {
                    orig = orig.toString();
                }
                var rewrittenPrefix = "javascript:bea.wlp.disc.xie._Service.update('";
                var index = orig.indexOf(rewrittenPrefix);
                if (index > -1) {
                    var splits = orig.split(', ');
                    orig = splits[0].substring(0, splits[0].length - 1);
                    orig = orig.substring(rewrittenPrefix.length, orig.length - 2);
                }
                return orig;
            }

            function isXML(type) {
                if (type) {
                    var index = type.indexOf(";");
                    if (index > 0) {
                        type = type.substring(0, index);
                    }
                    return (type == "text/xml" || type == "application/xml" || endsWith(type, "+xml"));
                }
                return false;
            }

            function parseXML(text, contentType) {
                var parsed;
                try {
                    if (window.ActiveXObject) {
                        var doc = new ActiveXObject("Microsoft.XMLDOM");
                        doc.async = false;
                        doc.loadXML(text);
                        parsed = doc.documentElement ? doc : null;
                    }
                    else if (document.implementation && document.implementation.createDocument) {
                        var parser = new DOMParser();
                        parsed = parser.parseFromString(text, contentType);
                        // FF does not fail with invalid XML
                        var ns = "http://www.mozilla.org/newlayout/xml/parsererror.xml";
                        if (parsed.firstChild.tagName == "parsererror" && parsed.firstChild.namespaceURI == ns) {
                            parsed = null;
                        }
                    }
                }
                catch (ignore) {
                    // If parsing failed, responseXML will not be available
                    parsed = null;
                }
                return parsed;
            }

            function endsWith(s1, s2) {
                var start = s1.length - s2.length;
                if (start < 0) {
                    throw "IllegalArgumentException: " + s1 + " is shorter than " + s2;
                }
                return s1.substring(start) == s2;
            }
        };
    },

    initialize: function($, L) {
        // Create a module for the old XPR namespace (for backward compatibility)
        bea.wlp.disc.Module.create("bea.netuix.ajax.client", {
            declare: function($, L) {
                $.XMLHttpRequest = bea.wlp.disc.io.XMLHttpRequest;
            }
        });
    }
});
});
// bea.wlp.disc.event
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
 * @name bea.wlp.disc.event
 * @overview
 *      This module provides a basic model for interacting with custom Disc events.  For documentation about specific
 *      events, see the documentation for those events in their parent module's documentation.
 */
bea.wlp.disc.Module.create("bea.wlp.disc.event", {
    declare: function($, L) {
        /**
         * This class provides a simple eventing framework.  Instances of this class represent discrete event types,
         * and an occurrence of the event itself is manifested when the event instance is fired.  Listeners may be
         * added or removed from event instances to signal their interest in particular event types derived from this
         * class.
         *
         * @class Event
         */
        $.Event = bea.wlp.disc.Class.create(function() {
            // A well-known static object to signal intent to break from an event handling chain
            var cancel = { };

            /**
             * Initializes a new Event instance.
             *
             * @private
             * @method initialize
             * @param {string} name
             *      The string name of the event in string form; not null or empty; e.g. OnMyCustomEvent
             * @param {boolean} [cancellable]
             *      Whether or not the propagation of this event can be terminated by listeners;
             *      if true, listeners that return the boolean false will halt event propagation;
             *      all other return values, including undefined, will allow the event to progagate normally;
             *      defaults to false
             */
            this.initialize = function(name, cancellable) {
                this._listeners = [];
                this._name = name;
                this._cancellable = !!cancellable;
            }

            /**
             * Returns the name of this event.
             *
             * @method getName
             * @returns {string}
             *      The name of this event
             */
            this.getName = function() {
                return this._name;
            }

            /**
             * Returns whether or not the event instance is cancellable when the event is fired.
             *
             * @method isCancellable
             * @returns {boolean}
             *      True if the event instance is cancellable
             */
            this.isCancellable = function() {
                return this._cancellable;
            }

            /**
             * Cancels the current firing of this event if this is a cancellable event.  If this event is not
             * cancellable, an error is thrown.
             *
             * @method cancel
             */
            this.cancel = function() {
                if (this._cancellable) {
                    throw cancel;
                }
                else {
                    throw L("Event {0} is not cancellable", this._name);
                }
            }

            /**
             * Adds a function as a listener to an event instance.  Arguments passed to the listener and cancelListener
             * callbacks on invocation include an event-specific payload object (if any) and a reference to the event
             * being fired, respectively.
             *
             * @method addListener
             * @param {function} listener
             *      The callback function to execute when this event is fired; not null
             * @param {function} [cancelListener]
             *      An optional listener to be invoked if the firing of this event is cancelled; this will be called
             *      <i>iff</i> the listener with which it is associated has already been called, and is not the listener
             *      responsible for cancelling the event (i.e. neither unfired listeners nor the listener that cancels
             *      the event will have their cancel listeners invoked); note that the cancel listener will also
             *      only ever be called if the event instance to which the cancel listener is added is, in fact,
             *      cancellable
             */
            this.addListener = function(listener, cancelListener) {
                if (typeof listener == "function" && this._find(listener) < 0) {
                    this._listeners.push({
                        listen: listener,
                        cancel: (typeof cancelListener == "function" && cancelListener)
                    });
                }
            }

            /**
             * Removes a listener function (and associated cancel listener function, if any) from an event instance.
             *
             * @method removeListener
             * @param {function} listener
             *      The listener function to be removed
             */
            this.removeListener = function(listener) {
                var i = this._find(listener);
                if (i >= 0) {
                    if (this._isFiring) {
                        this._listeners[i] = null;
                    }
                    else {
                        this._listeners.splice(i, 1);
                    }
                }
            }

            /**
             * Fires an event with the specified payload.
             *
             * @method _fire
             * @param {object} [payload]
             *      An event-instance-appropriate payload data object, if any
             * @returns
             *      True if the event completely fired; false if the event firing was cancelled
             */
            this._fire = function(payload) {
                this._isFiring = true;
                var i;
                try {
                    // Length must be checked on every iteration of this loop
                    for (i = 0; i < this._listeners.length; i++) {
                        if (this._listeners[i]) {
                            this._listeners[i].listen(payload, this);
                        }
                    }
                }
                catch (e) {
                    if (e === cancel) {
                        for (var j = 0; j < i; j++) {
                            if (this._listeners[j] && this._listeners[j].cancel) {
                                this._listeners[j].cancel(payload, this);
                            }
                        }
                        return false;
                    }
                    throw e;
                }
                finally {
                    var len = this._listeners.length;
                    for (i = 0; i < len; i++) {
                        if (!this._listeners[i]) {
                            this._listeners.splice(i, 1);
                        }
                    }
                    delete this._isFiring;
                }
                return true;
            }

            /**
             * Finds a specific listener in the list of this event's listeners.
             *
             * @method _find
             * @param {function} listener
             *      The listener function to find
             * @returns {number}
             *      The index at which the listener was found; -1 otherwise
             */
            this._find = function(listener) {
                var index = -1;
                for (var i = 0, len = this._listeners.length; i < len; i++) {
                    if (this._listeners[i] && this._listeners[i].listen === listener) {
                        index = i;
                        break;
                    }
                }
                return index;
            }
        });
    }
});
bea.wlp.disc.Module._include("Base", "bea.wlp.disc.context", function() {
// bea.wlp.disc.context[Base]
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
    declare: function($) {

        var $Util = bea.wlp.disc._util;

        $._HookContextProperty = $.$meta.getName('Context');

        /**
         * Instances of the <code>Context</code> class represent contextual information related to the rendered
         * Portal. <code>Context</code> is intended to be an abstract base class from which specific
         * context types are derived.
         *
         * @class Context
         */
        $.Context = bea.wlp.disc.Class.create(function(Context) {
            
            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>Context</code> object
             */
            this.initialize = function(props) {
                this._props = props;
            };

            this.toString = function() {
                var buff = [];
                buff.push(this.getType());
                buff.push(': ');
                var props = this._getProps();
                buff.push($Util.Json.serialize(props));
                return buff.join('');
            };

            this._getPropValue = function(prop) {
                var value = this._props[prop];
                if (value === undefined) {
                    value = null;
                }
                return value;
            };

            // Marker property for dynamic constructor modification
            Context._BEA_WLP_DISC_CONTEXT = true;

        });

        $._Metas = {};

        /**
         * Instances of the <code>MetaContext</code> class represent singletons containing information about
         * the rendered Portal. <code>MetaContext</code> is intended to be an abstract base class from which specific
         * context types are derived.
         *
         * @class MetaContext
         * @extends Context
         */
        $.MetaContext = $.Context.extend(function (MetaContext) {

            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>MetaContext</code> object
             */
            this.initialize = function(props) {
                this.sup(props);
                $._Metas[this.getType()] = this;
            };

            /**
             * Returns the instance of the appropriate meta context object. Each meta context object
             * is a singleton present in every rendered Portal (assuming Disc support is enabled).
             * <p/>
             * <b>NOTE</b>: This method is polymorphic. It uses the calling context to determine the
             * actual type of meta context object to return. For example:
             * <pre>
             * Application.getInstance();
             * Shell.getInstance();
             * </pre>
             *
             * @method getInstance
             * @returns {object}
             *      The meta context object
             */
            MetaContext.getInstance = function() {
                return $._Metas[this.TYPE];
            };
        });

        /**
         * Instances of the <code>HookedContext</code> class represent visible components of the rendered
         * Portal. <code>HookedContext</code> is intended to be an abstract base class from which specific
         * context types are derived.
         *
         * @class HookedContext
         * @extends Context
         */
        $.HookedContext = $.Context.extend(function (HookedContext) {

            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>HookedContext</code> object
             * @param {object} props
             *      The properties of this <code>HookedContext</code> object
             */
            this.initialize = function(id, props) {
                this.sup(props);
                this._id = id;
                var hook = document.getElementById(id);
                hook[$._HookContextProperty] = this;
            };

            /**
             * Returns the DOM <code>Element</code> associated with this object.
             * <p/>
             * <b>NOTE</b>: The <code>Element</code> returned by this method is a container provided by Portal.
             * This container should be considered an immutable opaque object, and any particular characteristics
             * of the container (properties, attributes, etc.) are subject to change in future releases. The only
             * supported uses of this container are operations on its children, including the removal, addition
             * and update of children.
             *
             * @method getMarkupElement
             * @returns {Element}
             *      The DOM <code>Element</code> associated with this object
             */
            this.getMarkupElement = function() {
                return document.getElementById(this._id);
            };

            /**
             * Returns all visible context objects. An empty array is returned when no context objects are
             * visible on the rendered page.
             * <p/>
             * <b>NOTE</b>: This method is polymorphic. It uses the calling context to determine the
             * actual type of context object to search for. For example:
             * <pre>
             * Portlet.getAll();     // Returns all visible Portlets
             * Placeholder.getAll(); // Returns all visible Placeholders
             * </pre>
             *
             * @static
             * @method getAll
             * @returns {object[]}
             *      Array of all visible Window objects
             */
            HookedContext.getAll = function() {
                var all = [];
                var divs = document.getElementsByTagName('div');
                for (var i = 0; i < divs.length; i++) {
                    var ctx = divs[i][$._HookContextProperty];
                    if (ctx instanceof this) {
                        all.push(ctx);
                    }
                }
                return all;
            };

            /**
             * Returns the context object containing the specified element. If no context object
             * containing the specified element is visible on the page, null is returned.
             * <p/>
             * <b>NOTE</b>: This method is polymorphic. It uses the calling context to determine the
             * actual type of context object to search for. For example, if an element <code>ele</code>
             * represents a node within the content of a Portlet, the following results can be expected:
             * <pre>
             * Portlet.findByElement(ele); // Returns Portlet
             * Layout.findByElement(ele);  // Returns Layout
             * </pre>
             *
             * @static
             * @method findByElement
             * @param element {Element}
             *      The element expected to be contained within the context object
             * @param {boolean} [self]
             *      true starts the search at the element passed as an argument; false (default) starts the
             *      search at the element's parent
             * @returns {object}
             *      The context object containing the specified element; null if the specified element
             *      is not contained within a context object
             */
            HookedContext.findByElement = function(element, self) {
                var type = this;
                var context = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && ctx instanceof type) {
                        context = ctx;
                        proceed = false;
                    }
                    return proceed;
                }, self);

                return context;
            };
        });

        /**
         * Instances of the <code>PresentationContext</code> class represent presentation-oriented components
         * of the rendered Portal. <code>PresentationContext</code> is intended to be an abstract base class
         * from which specific context types are derived.
         *
         * @class PresentationContext
         * @extends HookedContext
         */
        $.PresentationContext = $.HookedContext.extend(function (PresentationContext) {

            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>PresentationContext</code> object
             * @param {object} props
             *      The properties of this <code>PresentationContext</code> object
             */

            PresentationContext._PROPS = [

                /**
                 * Returns the value of the "presentationClass" attribute for this object,
                 * if defined.
                 *
                 * @method getPresentationClass
                 * @returns {string}
                 *      The value of the "presentationClass" attribute for this object;
                 *      null if not defined
                 */
                'presentationClass',

                /**
                 * Returns the value of the "presentationId" attribute for this object,
                 * if defined.
                 *
                 * @method getPresentationId
                 * @returns {string}
                 *      The value of the "presentationId" attribute for this object;
                 *      null if not defined
                 */
                'presentationId',

                /**
                 * Returns the value of the "presentationStyle" attribute for this object,
                 * if defined.
                 *
                 * @method getPresentationStyle
                 * @returns {string}
                 *      The value of the "presentationStyle" attribute for this object;
                 *      null if not defined
                 */
                'presentationStyle'
            ];
        });
    }
});
});
bea.wlp.disc.Module._include("Layout", "bea.wlp.disc.context", function() {
// bea.wlp.disc.context[Layout]
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
    declare: function($) {

        var $Util = bea.wlp.disc._util;

        /**
         * Instances of the <code>Layout</code> class represent visible layouts of the rendered Portal.
         * <code>Layout</code> is intended to be an abstract base class from which specific layout types
         * such as <code>FlowLayout</code> are derived.
         *
         * @class Layout
         * @extends PresentationContext
         */
        $.Layout = $.PresentationContext.extend(function(Layout) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Layout</code> object
             * @param {object} props
             *      The properties of this <code>Layout</code> object
             */

            /**
             * Returns the parent <code>Page</code> of this <code>Layout</code>.
             *
             * @method getParentPage
             * @returns {Page}
             *      The parent <code>Page</code> of this <code>Layout</code>
             */
            this.getParentPage = $._Common.getParentPage;

            /**
             * Returns an array of all <code>Books</code> and <code>Portlets</code> contained as immediate children
             * of <code>Placeholders</code> within this <code>Layout</code>.
             *
             * @method getPlaceables
             * @returns {Window[]}
             *      An array of <code>Books</code> and <code>Portlets</code> contained within this <code>Layout</code>
             */
            this.getPlaceables = $._Common.getPlaceables;

            /**
             * Returns an array of all immediate <code>Placeholder</code> objects contained within this
             * <code>Layout</code>.
             * <p>
             * <b>NOTE</b>: Nested <code>Placeholder</code> objects are not returned by this method. To retrieve
             * nested <code>Placeholder</code> objects call this method on a nested <code>Layout</code>. Two
             * scenarios where nested <code>Placeholder</code> objects may be present are: 1) Implicit layouts
             * and 2) Placeable books.
             *
             * @method getPlaceholders
             * @returns {Placeholder[]}
             *      All <code>Placeholders</code> contained immediately within this <code>Layout</code>
             */
            this.getPlaceholders = function() {
                var element = this.getMarkupElement();
                var placeholders = [];
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if ((ctx instanceof $.Window) || (ctx instanceof $.Layout)) {
                            proceed = false;
                        }
                        else if (ctx.getType() == $.Placeholder.TYPE) {
                            placeholders.push(ctx);
                        }
                    }
                    return proceed;
                }, 'div');
                return placeholders;
            };

            Layout._PROPS = [

                /**
                 * Returns the markup name of this <code>Layout</code> if defined.
                 *
                 * @method getMarkupName
                 * @returns {string}
                 *      The markup name of this <code>Layout</code>; null if not defined
                 */
                'markupName'
            ];
        });

        /**
         * Instances of the <code>BorderLayout</code> class represent visible border layouts of the rendered Portal.
         *
         * @class BorderLayout
         * @extends Layout
         */
        $.BorderLayout = $.Layout.extend(function(BorderLayout) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>BorderLayout</code> object
             * @param {object} props
             *      The properties of this <code>BorderLayout</code> object
             */

            BorderLayout._PROPS = [

                /**
                 * Returns the number of columns rendered by this <code>BorderLayout</code>. The total number
                 * of columns is determined as follows:
                 * <ul>
                 *      <li>+1 column if any of the north, center or south <code>Placeholders</code> is present
                 *      <li>+1 column if the west <code>Placeholder</code> is present
                 *      <li>+1 column if the east <code>Placeholder</code> is present
                 * </ul>
                 *
                 * @method getColumns
                 * @returns {number}
                 *      The number of columns in this <code>BorderLayout</code>
                 */
                'columns',

                /**
                 * Returns the layout strategy employed by this <code>BorderLayout</code>. Return values
                 * are constrained by the values in the <code>Strategy</code> enumeration.
                 * <p>
                 * See the <code>BorderLayoutPresentationContext.getLayoutStrategy()</code> Javadoc for
                 * more information about border layout strategies.
                 *
                 * @method getStrategy
                 * @returns {string}
                 *      The layout strategy employed by this <code>BorderLayout</code>
                 * @see #Strategy
                 */
                'strategy'
            ];

            /**
             * Enumerated values for the strategy property of <code>BorderLayout</code>.
             *
             * @object BorderLayout.Strategy
             * @see #getStrategy
             */
            BorderLayout.Strategy = {

                /**
                 * @static
                 * @field {string}
                 *      Value representing layout by order strategy.
                 */
                BY_ORDER: 'order',

                /**
                 * @static
                 * @field {string}
                 *      Value representing layout by title strategy.
                 */
                BY_TITLE: 'title'
            };
        });

        /**
         * Instances of the <code>FlowLayout</code> class represent visible flow layouts of the rendered Portal.
         *
         * @class FlowLayout
         * @extends Layout
         */
        $.FlowLayout = $.Layout.extend(function(FlowLayout) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>FlowLayout</code> object
             * @param {object} props
             *      The properties of this <code>FlowLayout</code> object
             */

            /**
             * Returns the parent <code>Placeholder</code> of this <code>FlowLayout</code> for implicit
             * layouts. For explicit layouts this method returns null.
             *
             * @method getParentPlaceholder
             * @returns {Placeholder}
             *      The parent <code>Placeholder</code> of this <code>FlowLayout</code>
             */
            this.getParentPlaceholder = $._Common.getParentPlaceholder;

            FlowLayout._PROPS = [

                /**
                 * Returns true for implicit <code>FlowLayouts</code>. Implicit layouts are created automatically
                 * by Portal within <code>Placeholders</code> when the placeholder's "usingFlow" attribute is true.
                 *
                 * @method getImplicit
                 * @returns {boolean}
                 *      true if this is an implicit <code>FlowLayout</code>
                 */
                'implicit',

                /**
                 * Returns the orientation in use for this <code>FlowLayout</code>. Return values
                 * are constrained by the values in the <code>Orientation</code> enumeration.
                 *
                 * @method getOrientation
                 * @returns {string}
                 *      The orientation in use for this <code>FlowLayout</code>
                 * @see #Orientation
                 */
                'orientation'
            ];

            /**
             * Enumerated values for the orientation property of <code>FlowLayout</code>.
             *
             * @object FlowLayout.Orientation
             * @see #getOrientation
             */
            FlowLayout.Orientation = {

                /**
                 * @static
                 * @field {string}
                 *      Value representing horizontal orientation.
                 */
                HORIZONTAL: 'horizontal',

                /**
                 * @static
                 * @field {string}
                 *      Value representing vertical orientation.
                 */
                VERTICAL: 'vertical'
            };
        });

        /**
         * Instances of the <code>GridLayout</code> class represent visible grid layouts of the rendered Portal.
         *
         * @class GridLayout
         * @extends Layout
         */
        $.GridLayout = $.Layout.extend(function(GridLayout) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>GridLayout</code> object
             * @param {object} props
             *      The properties of this <code>GridLayout</code> object
             */

            GridLayout._PROPS = [

                /**
                 * Returns the "columns" attribute for this <code>GridLayout</code>, if defined
                 *
                 * @method getColumns
                 * @returns {number}
                 *      The value of the "columns" attribute for this <code>GridLayout</code>;
                 *      null if not defined
                 */
                'columns',

                /**
                 * Returns true if this <code>GridLayout</code> is constrained to a specific
                 * number of columns. (i.e. if getColumns() returns a number)
                 *
                 * @method getRowMajor
                 * @returns {boolean}
                 *      True if <code>getColumns()</code> returns a number
                 */
                'rowMajor',

                /**
                 * Returns the "rows" attribute for this <code>GridLayout</code>, if defined
                 *
                 * @method getRows
                 * @returns {number}
                 *      The value of the "rows" attribute for this <code>GridLayout</code>;
                 *      null if not defined
                 */
                'rows'
            ];
        });

        /**
         * Instances of the <code>Placeholder</code> class represent visible placeholders of the rendered Portal.
         *
         * @class Placeholder
         * @extends PresentationContext
         */
        $.Placeholder = $.PresentationContext.extend(function(Placeholder) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Placeholder</code> object
             * @param {object} props
             *      The properties of this <code>Placeholder</code> object
             */

            /**
             * Returns the implicit <code>FlowLayout</code> when this <code>Placeholder's</code>
             * "usingFlow" attribute is true.
             *
             * @method getImplicitLayout
             * @returns {FlowLayout}
             *      The implicit layout if this <code>Placeholder</code> is "usingFlow"; null otherwise
             */
            this.getImplicitLayout = function() {
                var element = this.getMarkupElement();
                var layout = null;
                $Util.Dom.eachDescendantLinear(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if (ctx instanceof $.Window) {
                            proceed = false;
                        }
                        else if (ctx instanceof $.Layout) {
                            layout = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                }, 'div');
                return layout;
            }

            /**
             * Returns the parent <code>Layout</code> containing this <code>Placeholder</code>.
             *
             * @method getParentLayout
             * @returns {Layout}
             *      The parent <code>Layout</code> of this <code>Placeholder</code>
             */
            this.getParentLayout = function() {
                var element = this.getMarkupElement();
                var layout = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && (ctx instanceof $.Layout)) {
                        layout = ctx;
                        proceed = false;
                    }
                    return proceed;
                });
                return layout;
            };

            /**
             * Returns an array of all <code>Books</code> and <code>Portlets</code> contained as immediate children
             * of this <code>Placeholder</code>.
             *
             * @method getPlaceables
             * @returns {Window[]}
             *      An array of <code>Books</code> and <code>Portlets</code> contained within this
             *      <code>Placeholder</code>
             */
            this.getPlaceables = $._Common.getPlaceables;

            Placeholder._PROPS = [

                /**
                 * Returns the flow direction for this <code>Placeholder</code>. Return values
                 * are constrained by the values in the <code>Flow</code> enumeration.
                 *
                 * @method getFlow
                 * @returns {string}
                 *      The flow direction for this <code>Placeholder</code>
                 * @see #Flow
                 */
                'flow',

                /**
                 * Returns true if this <code>Placeholder</code> is the immediate child of an
                 * implicit </code>FlowLayout</code>, false otherwise.
                 *
                 * @method getImplicit
                 * @returns {boolean}
                 *      true if this <code>Placeholder</code> is the immediate child of a implicit
                 *      </code>FlowLayout</code>; false otherwise
                 */
                'implicit',

                /**
                 * Returns this <code>Placeholder's</code> location within its parent <code>Page</code>.
                 * Locations are ordinal numbers such that <code>Placeholders</code> within the same
                 * <code>Page</code> have the positions 0, 1, 2, 3, 4, <em>...</em>.
                 * <p/>
                 * <b>NOTE</b>: The value returned for implicit placeholders is undefined.
                 *
                 * @method getLocation
                 * @returns {number}
                 *      The location of this <code>Placeholder</code> within the parent <code>Page</code>
                 */
                'location',

                /**
                 * When customization has been enabled this method will return true when the user
                 * is not allowed to update the contents of this </code>Placeholder</code>, false otherwise.
                 *
                 * @method getLocked
                 * @returns {boolean}
                 *      true when a user is not allowed to make updates to this placeholder; false otherwise.
                 * @see Application#getCustomizationEnabled
                 */
                'locked',

                /**
                 * Returns the title of this <code>Placeholder</code>, if defined.
                 * 
                 * @method getTitle
                 * @returns {string}
                 *      The title of this <code>Placeholder</code>; null if not defined
                 */
                'title',

                /**
                 * Returns the "usingFlow" attribute for this <code>Placeholder</code>, if defined.
                 *
                 * @method getUsingFlow
                 * @returns {bolean}
                 *      The value of the "usingFlow" attribute for this <code>Placeholder</code>;
                 *      null if not defined
                 */
                'usingFlow',

                /**
                 * Returns the "width" attribute for this <code>Placeholder</code>, if defined.
                 *
                 * @method getWidth
                 * @returns {string}
                 *      The value of the "width" attribute for this <code>Placeholder</code>;
                 *      null if not defined
                 */
                'width'
            ];

            /**
             * Enumerated values for the flow property of <code>Placeholder</code>.
             *
             * @object Placeholder.Flow
             * @see #getFlow
             */
            Placeholder.Flow = {

                /**
                 * @static
                 * @field {string}
                 *      Value representing horizontal flow.
                 */
                HORIZONTAL: 'horizontal',

                /**
                 * @static
                 * @field {string}
                 *      Value representing vertical flow.
                 */
                VERTICAL: 'vertical'
            };
        });
    }
});
});
// bea.wlp.disc.context
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
 * @name bea.wlp.disc.context
 * @overview
 *      This module provides contextual information about the rendered Portal. When Disc is enabled for
 *      a Portal, instances of context classes are created as part of the Portal rendering process. These
 *      context classes provide information about individual Portal components such as Portlets, Pages,
 *      Layouts and Themes, as well as information about the relationship between these components. Context
 *      (i.e. instances of context objects) is provided only for visible Portal components. Information about
 *      non-visible components, such as books peer to the current book or hidden portlets, will not be
 *      available via context objects.
 *      <p/>
 *      A typical usage scenario may look something like the following:
 *      <pre>
 *          var portlet = bea.wlp.disc.context.Portlet.findByLabel("myPortlet");
 *          var title = portlet.getTitle();
 *          var mode = portlet.getWindowMode();
 *          var page = portlet.getParentPage();
 *      </pre>
 *      <p/>
 *      Disc context classes are loosely representative of server-side PresentationContext classes and
 *      generally provide similar functionality for use in client-side code. Like server-side PresentationContext
 *      objects, the lifecycle of Disc context objects is managed by Portal and application code should generally
 *      not attempt to create or destroy such objects. Furthermore, Disc context objects should be considered
 *      immutable; functions and/or properties should not be added, and existing properties should not be
 *      accessed or manipulated except via the provided APIs.
 *      <p/>
 *      In addition to creating instances of Disc context objects, enabling Disc also causes Portal to render
 *      HTML containers around standard Portal components. This container is a single HTML Element containing
 *      all markup for the corresponding component and is sometimes referred to as the "context hook".
 *      Like the context objects, the context container element itself should be considered immutable; attributes,
 *      styling, event handlers, or other similar functionality and data should not be added to the container
 *      element. Furthermore, the context container element should be considered opaque; no meaning should
 *      be inferred from the element name, individual attributes/properties or their values. All such information
 *      is subject to change without notice. The only supported operations on context container elements are
 *      standard DOM mechanisms to retrieve the container's children. Most context objects support a method
 *      to retrieve the markup associated with the context's component, and this method will return a reference
 *      to the context container element. See the <code>getMarkupElement()</code> method for more information.
 *      <p/>
 *      Application code should not hold references to Disc context objects beyond the immediate use of such
 *      objects. Dynamic updates to the HTML page may make such references stale or invalid.
 *      <p/>
 *      Disc context objects are generally not available in a valid state until the entire HTML page has been
 *      loaded. Application code should not attempt to use context objects from inline script blocks, but should
 *      instead register an "onload" handler that encapsulates interaction with context objects. For example, the
 *      following code when placed inline in a portlet JSP will not behave as expected:
 *      <pre>
 *          var portlet = bea.wlp.disc.context.Portlet.findByLabel("myPortletLabel"); // portlet will be null
 *          var title = portlet.getTitle(); // error
 *      </pre>
 *      However, if we add the same code via a <a href="http://dojotoolkit.org/">Dojo</a> (for example) onload function,
 *      the code works as expected:
 *      <pre>
 *          dojo.addOnLoad(function() {
 *              var portlet = bea.wlp.disc.context.Portlet.findByLabel("myPortletLabel");
 *              var title = portlet.getTitle();
 *          });
 *      </pre>
 */
bea.wlp.disc.Module.create("bea.wlp.disc.context", {
    include: ["_Mixin", "Base", "Window", "Layout", "Other"],
    initialize: function($) {

        bea.wlp.disc._Object.each($,

            // action for each
            function(o) {

                // Add "static" type to each class constructor
                $[o].TYPE = o;

                // Add getType() method to each context object
                $[o].inject('getType', (function(type) {
                    return function() {
                        return type;
                    };
                })(o));

                // Add toString helper function for properties
                $[o].inject('_getProps', (function(ctor) {
                    return function() {
                        var props = this.sup ? this.sup() : {};
                        if (ctor._PROPS) {
                            for (var i = 0; i < ctor._PROPS.length; i++) {
                                var name = ctor._PROPS[i];
                                if (this._props && (this._props[name] != null)) {
                                    props[name] = this._props[name];
                                }
                            }
                        }
                        return props;
                    }
                })($[o]));

                // Create property getters for each context object
                if ($[o].hasOwnProperty('_PROPS')) {
                    var names = $[o]._PROPS;
                    for (var p = 0; p < names.length; p++) {
                        var getter = 'get' + names[p].substring(0, 1).toUpperCase() + names[p].substring(1);
                        $[o].inject(getter, (function(name) {
                            return function() {
                                return this._getPropValue(name);
                            };
                        })(names[p]));
                    }
                }

                // Apply static mixins
                if ($[o].hasOwnProperty('_MIXINS')) {
                    var mixins = $[o]._MIXINS;
                    for (var m = 0; m < mixins.length; m++) {
                        $[o].inject($._Mixin[mixins[m]]);
                    }
                }
            },

            // discriminator for each
            function(o) {
                return $[o]._BEA_WLP_DISC_CONTEXT;
            }
        );
    }
});
bea.wlp.disc.Module._include("Other", "bea.wlp.disc.context", function() {
// bea.wlp.disc.context[Other]
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
    declare: function($) {

        /**
         * Instances of the <code>Header</code> class represent visible headers of the rendered Portal.
         *
         * @class Header
         * @extends PresentationContext
         */
        $.Header = $.PresentationContext.extend({
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Header</code> object
             * @param {object} props
             *      The properties of this <code>Header</code> object
             */
        });

        /**
         * Instances of the <code>Footer</code> class represent visible footers of the rendered Portal.
         *
         * @class Footer
         * @extends PresentationContext
         */
        $.Footer = $.PresentationContext.extend({
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Footer</code> object
             * @param {object} props
             *      The properties of this <code>Footer</code> object
             */
        });

        /**
         * Instances of the <code>Menu</code> class represent visible menus of the rendered Portal.
         * <code>Menu</code> is intended to be an abstract base class from which specific menu types
         * are derived.
         *
         * @class Menu
         * @extends PresentationContext
         */
        $.Menu = $.PresentationContext.extend(function(Menu) {

            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Menu</code> object
             * @param {object} props
             *      The properties of this <code>Menu</code> object
             */

            Menu._PROPS = [

                /**
                 * Returns the markup name of this <code>Menu</code> if defined.
                 *
                 * @method getMarkupName
                 * @returns {string}
                 *      The markup name of this <code>Menu</code>; null if not defined
                 */
                'markupName'
            ];
        });

        /**
         * Instances of the <code>SingleLevel</code> class represent visible single-level menus of the
         * rendered Portal.
         *
         * @class SingleLevelMenu
         * @extends Menu
         */
        $.SingleLevelMenu = $.Menu.extend({

            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>SingleLevelMenu</code> object
             * @param {object} props
             *      The properties of this <code>SingleLevelMenu</code> object
             */
        });

        /**
         * Instances of the <code>MultiLevel</code> class represent visible multi-level menus of the
         * rendered Portal.
         *
         * @class MultiLevelMenu
         * @extends Menu
         */
        $.MultiLevelMenu = $.Menu.extend({

            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>MultiLevelMenu</code> object
             * @param {object} props
             *      The properties of this <code>MultiLevelMenu</code> object
             */
        });

        /**
         * The <code>Application</code> singleton represents global application settings and environmental
         * data for the rendered Portal.
         *
         * @class Application
         * @extends MetaContext
         */
        $.Application = $.MetaContext.extend(function(Application) {

            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>Application</code> object
             */

            Application._PROPS = [

                /**
                 * Returns true if async mode is enabled for the rendered Portal.
                 *
                 * @method getAsyncModeEnabled
                 * @return {boolean}
                 *      True if async mode is enabled
                 */
                'asyncModeEnabled',

                /**
                 * Returns true if customization is enabled for the rendered Portal.
                 *
                 * @method getCustomizationEnabled
                 * @return {boolean}
                 *      True if customization is enabled
                 */
                'customizationEnabled',

                /**
                 * Returns the default locale for the rendered Portal.
                 * <p>
                 * <b>NOTE:</b>The value returned is the string representation of the value returned
                 * from the server-side API
                 * <code>com.bea.netuix.servlets.manager.AppDescriptor.getDefaultLocale()</code>.
                 *
                 * @method getDefaultLocale
                 * @return {string}
                 *      The default locale
                 */
                'defaultLocale',

                /**
                 * Returns the desktop path of the rendered Portal for streaming desktops, null
                 * for "file mode" Portals.
                 *
                 * @method getDesktopPath
                 * @returns {string}
                 *      The desktop path of the rendered Portal if streaming; null otherwise
                 * @see #getDotPortal
                 */
                'desktopPath',

                /**
                 * Returns true for "file mode" Portals, false for streaming desktops.
                 *
                 * @method getDotPortal
                 * @returns {boolean}
                 *      True for "file mode" Portals
                 */
                'dotPortal',

                /**
                 * Returns true if DVT is enabled for the rendered Portal.
                 *
                 * @method getDvtEnabled
                 * @return {boolean}
                 *      True if DVT is enabled
                 */
                'dvtEnabled',

                /**
                 * Returns true if localization is enabled for the rendered Portal.
                 *
                 * @method getLocalizationEnabled
                 * @return {boolean}
                 *      True if localization is enabled
                 */
                'localizationEnabled',

                /**
                 * Returns the portal path of the rendered Portal for streaming desktops, null
                 * for "file mode" Portals.
                 *
                 * @method getPortalPath
                 * @returns {string}
                 *      The portal path of the rendered Portal if streaming; null otherwise
                 * @see #getDotPortal
                 */
                'portalPath',

                /**
                 * Returns an array of preferred locales for the rendered Portal.
                 * <p>
                 * <b>NOTE:</b>The value returned is the string representation of the value returned
                 * from the server-side API
                 * <code>com.bea.netuix.servlets.l10n.L10nResourceManager.getPreferredLocalesAsList()</code>.
                 *
                 * @method getPreferredLocales
                 * @returns {string[]}
                 *      The list of preferred locales
                 */
                'preferredLocales',

                /**
                 * Returns true if production mode is enabled for the rendered Portal.
                 *
                 * @method getProductionModeEnabled
                 * @returns {boolean}
                 *      True if production mode is enabled
                 */
                'productionModeEnabled',

                /**
                 * Returns true when rendering a standalone Portlet.
                 *
                 * @method getStandalonePortlet
                 * @returns {boolean}
                 *      True when rendering a standalone Portlet
                 */
                'standalonePortlet',

                /**
                 * Returns the authenticated username for the rendered Portal, if any.
                 *
                 * @method getUserName
                 * @returns {string}
                 *      Authenticated username; null for anonymous users
                 */
                'userName',

                /**
                 * Returns the webapp name for the rendered Portal.
                 *
                 * @method getWebAppName
                 * @returns {string}
                 *      The webapp name
                 */
                'webAppName'
            ];
        });

        /**
         * The <code>Desktop</code> represents global desktop settings for the rendered Portal.
         *
         * @class Desktop
         * @extends MetaContext
         */
        $.Desktop = $.MetaContext.extend(function(Desktop) {

            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>Desktop</code> object
             */

            /**
             * Returns the label of this <code>Desktop</code>. This is equivalent to calling
             * <code>getDefinitionLabel</code>.
             *
             * @method getLabel
             * @returns {string}
             *      The definition label of this <code>Desktop</code>
             * @see #getDefinitionLabel
             */
            this.getLabel = function() {
                return this.getDefinitionLabel();
            };

            Desktop._PROPS = [

                /**
                 * Returns the title of this <code>Desktop</code>.
                 *
                 * @method getTitle
                 * @returns {string}
                 *      The title of the </code>Desktop</code>
                 */
                'title',

                /**
                 * Returns the definition label of this <code>Desktop</code>.
                 *
                 * @method getDefinitionLabel
                 * @returns {string}
                 *      The definition label of this </code>Desktop</code>
                 */
                'definitionLabel'
            ];
        });

        /**
         * The <code>LookAndFeel</code> represents global look and feel settings for the rendered Portal.
         *
         * @class LookAndFeel
         * @extends MetaContext
         */
        $.LookAndFeel = $.MetaContext.extend(function(LookAndFeel) {

            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>LookAndFeel</code> object
             */

            LookAndFeel._PROPS = [

                /**
                 * Returns the name of the skin component of this <code>LookAndFeel</code>.
                 *
                 * @method getSkin
                 * @returns {string}
                 *      The skin name
                 */
                'skin',

                /**
                 * Returns the name of the skeleton component of this <code>LookAndFeel</code>.
                 *
                 * @method getSkeleton
                 * @returns {string}
                 *      The skeleton name
                 */
                'skeleton',

                /**
                 * Returns the markup name of this <code>LookAndFeel</code> if defined.
                 *
                 * @method getMarkupName
                 * @returns {string}
                 *      The markup name of this <code>LookAndFeel</code>; null if not defined
                 */
                'markupName'
            ];
        });

        /**
         * The <code>Shell</code> represents global shell settings for the rendered Portal.
         *
         * @class Shell
         * @extends MetaContext
         */
        $.Shell = $.MetaContext.extend(function(Shell) {

            /**
             * @private
             * @method initialize
             * @param {object} props
             *      The properties of this <code>Shell</code> object
             */

            Shell._PROPS = [

                /**
                 * Returns the markup name of this <code>Shell</code> if defined.
                 *
                 * @method getMarkupName
                 * @returns {string}
                 *      The markup name of this <code>Shell</code>; null if not defined
                 */
                'markupName'
            ];
        });
    }
});
});
bea.wlp.disc.Module._include("Window", "bea.wlp.disc.context", function() {
// bea.wlp.disc.context[Window]
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
    declare: function($) {

        var $Util = bea.wlp.disc._util;

        /**
         * Instances of the <code>Window</code> class represent visible windows of the rendered Portal.
         * <code>Window</code> is intended to be an abstract base class from which specific window types
         * such as <code>Portlet</code> are derived.
         *
         * @class Window
         * @extends PresentationContext
         */
        $.Window = $.PresentationContext.extend(function(Window) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Window</code> object
             * @param {object} props
             *      The properties of this <code>Window</code> object
             */
            
            /**
             * Returns the DOM <code>Element</code> associated with the content of this <code>Window</code>.
             * Under circumstances where portlet content is not visible (e.g. portlet is minimized) this method
             * will return null.
             * <p/>
             * <b>NOTE</b>: The <code>Element</code> returned by this method is a container provided by Portal.
             * This container should be considered an immutable opaque object, and any particular characteristics
             * of the container (properties, attributes, etc.) are subject to change in future releases. The only
             * supported uses of this container are operations on its children, including the removal, addition
             * and update of children. 
             *
             * @method getContentMarkupElement
             * @returns {Element}
             *      The DOM <code>Element</code> associated with this <code>Window</code>; null if not visible
             */
            this.getContentMarkupElement = function() {
                var element = this.getMarkupElement();
                var contentElement = null;
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        var type = ctx.getType();
                        if (type == $.Titlebar.TYPE) {
                            proceed = false;
                        }
                        else if (type == $._Content.TYPE) {
                            contentElement = el;
                            proceed = false;
                        }
                    }
                    return proceed;
                }, 'div');
                return contentElement;
            };

            /**
             * Returns the label of this <code>Window</code>. This is a convenience method to return the correct
             * kind (instance/definition) of label for each <code>Window</code> type. For <code>Window</code>
             * in general, definition labels are used. Subtypes may override this method to return more
             * appropriate kinds of labels.
             * 
             * @method getLabel
             * @returns {string}
             *      The definition label of this <code>Window</code>
             */
            this.getLabel = function() {
                return this.getDefinitionLabel();
            };

            /**
             * Returns the parent <code>Page</code> of this <code>Window</code>. Under some conditions this
             * method may return null (e.g. if called on the main book).
             * <p/>
             * <b>NOTE</b>: This method is polymorphic - it returns a parent of type <code>Page</code>. This
             * may produce unexpected results when called on a <code>Page</code> object, as the return value
             * will be the parent <code>Book</code>.
             *
             * @method getParentPage
             * @returns {Page}
             *      The parent <code>Page</code> of this <code>Window</code>; null if no parent
             *      <code>Page</code> exists
             */
            this.getParentPage = $._Common.getParentPage;

            /**
             * Returns the parent <code>Theme</code> of this <code>Window</code>. If no theme is applied
             * to this <code>Window</code> object, the method will return null. If the optional <code>deep</code>
             * parameter is true, any <code>Theme</code> applied to a parent <code>Window</code> object will be
             * returned.
             *
             * @method getParentTheme
             * @param {boolean} [deep]
             *      True to return a <code>Theme</code> applied to a parent <code>Window</code>; false (default)
             *      to return only a <code>Theme</code> applied specifically to this <code>Window</code>.
             * @returns {Theme}
             *      The <code>Theme</code> applied to this <code>Window</code>; null if this <code>Window</code>
             *      is not themed.
             */
            this.getParentTheme = function(deep) {
                var element = this.getMarkupElement();
                var theme = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if (!deep && (ctx instanceof $.Window)) {
                            proceed = false;
                        }
                        else if (ctx.getType() == $.Theme.TYPE) {
                            theme = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                });
                return theme;
            };

            /**
             * Returns the <code>Titlebar</code> corresponding to this <code>Window</code>. Under circumstances
             * where the titlebar is not visible (e.g. not defined) this method will return null.
             *
             * @method getTitlebar
             * @returns {Titlebar}
             *      The <code>Titlebar</code> for this <code>Window</code>; null if not visible
             */
            this.getTitlebar = function() {
                var element = this.getMarkupElement();
                var titlebar = null;
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        var type = ctx.getType();
                        if (type == $._Content.TYPE) {
                            proceed = false;
                        }
                        else if (type == $.Titlebar.TYPE) {
                            titlebar = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                }, 'div');
                return titlebar;
            };

            /**
             * Returns the <code>Window</code> object with the specified label. If no window with the specified
             * label is visible on the rendered page null is returned.
             * <p/>
             * <b>NOTE</b>: This method is polymorphic - it uses the calling context to determine the
             * actual type of window object to search for. For example, if a portlet with the label
             * "myPortlet" is visible on the page, the following results can be expected:
             * <pre>
             * Portlet.findByLabel("myPortlet"); // Returns Portlet
             * Window.findByLabel("myPortlet");  // Returns Portlet
             * Book.findByLabel("myPortlet");    // Returns null
             * </pre>
             *
             * @static
             * @method findByLabel
             * @param {string} label
             *      The label
             * @returns {Window}
             *      The instance of the <code>Window</code> with the specified label; null if no such
             *      Window is visible on the page
             */
            Window.findByLabel = function(label) {
                var context = null;
                var all = this.getAll();
                for (var i = 0; i < all.length; i++) {
                    if (all[i].getLabel() == label) {
                        context = all[i];
                        break;
                    }
                }
                return context;
            };

            /**
             * Updates the <code>title</code> of this <code>Window</code>.
             * <p/>
             * <b>NOTE</b>: This method only updates the state of this client-side <code>Window</code> object;
             * it does not update the state of the server-side window object. It is the responsibility of the
             * application code calling this method to ensure that client-side and server-side state are in sync.
             *
             * @private
             * @method setTitle
             * @param {string} title
             *      The new value for the title property
             * @see #getTitle
             */
            this.setTitle = function(title) {
                this._props['title'] = title;
            };

            Window._PROPS = [

                /**
                 * Returns the value of the "presentationClass" attribute for this <code>Window's</code> content,
                 * if defined.
                 *
                 * @method getContentPresentationClass
                 * @returns {string}
                 *      The value of the "presentationClass" attribute for this <code>Window's</code> content;
                 *      null if not defined
                 */
                'contentPresentationClass',

                /**
                 * Returns the value of the "presentationStyle" attribute for this <code>Window's</code> content,
                 * if defined.
                 *
                 * @method getContentPresentationStyle
                 * @returns {string}
                 *      The value of the "presentationStyle" attribute for this <code>Window's</code> content;
                 *      null if not defined
                 */
                'contentPresentationStyle',

                /**
                 * Returns the definition label of this <code>Window</code>.
                 *
                 * @method getDefinitionLabel
                 * @returns {string}
                 *      The definition label of this <code>Window</code>.
                 */
                'definitionLabel',

                /**
                 * Returns the title of this <code>Window</code>.
                 *
                 * @method getTitle
                 * @returns {string}
                 *      The title of this Window
                 */
                'title',

                /**
                 * Returns a string representing the current mode of this <code>Window</code>. Typical values
                 * include "view", "edit" and "help", but values representing custom modes may also be returned.
                 * 
                 * @method getWindowMode
                 * @returns {string}
                 *      The current mode of this Window
                 */
                'windowMode',

                /**
                 * Returns a string representing the current state of this <code>Window</code>. Typical values
                 * include "normal", "minimized" and "float", but values representing custom states may also be
                 * returned. 
                 *
                 * @method getWindowState
                 * @returns {string}
                 *      The current state of this Window
                 */
                'windowState'
            ];
        });

        /**
         * Instances of the <code>Page</code> class represent visible pages of the rendered Portal.
         *
         * @class Page
         * @extends Window
         */
        $.Page = $.Window.extend(function(Page) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Page</code> object
             * @param {object} props
             *      The properties of this <code>Page</code> object
             */

            /**
             * Returns the parent <code>Book</code> of this <code>Page</code>. Under some conditions this
             * method may return null (e.g. if called on the main book).
             *
             * @method getParentBook
             * @returns {Book}
             *      The parent <code>Book</code> of this <code>Page</code>; null if no parent
             *      <code>Book</code> exists
             */
            this.getParentBook = function() {
                var element = this.getMarkupElement();
                var book = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && (ctx.getType() == $.Book.TYPE)) {
                        book = ctx;
                        proceed = false;
                    }
                    return proceed;
                });
                return book;
            };

            /**
             * Returns the <code>Layout</code> contained as an immediate child of this <code>Page</code>. Under some
             * circumstances this method may return null (e.g. if the page contains a maximized portlet).
             *  
             * @method getLayout
             * @returns {Layout}
             *      The <code>Layout</code> contained within this <code>Page</code>; null if the <code>Page</code>
             *      does not contain a <code>Layout</code>
             */
            this.getLayout = function() {
                var element = this.getMarkupElement();
                var layout = null;
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if (ctx instanceof $.Window) {
                            proceed = false;
                        }
                        else if (ctx instanceof $.Layout) {
                            layout = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                }, 'div');
                return layout;
            };

            /**
             * Returns an array of all <code>Books</code> and <code>Portlets</code> contained as immediate children
             * of <code>Placeholders</code> within this <code>Page</code>.
             *
             * @method getPlaceables
             * @returns {Window[]}
             *      An array of <code>Books</code> and <code>Portlets</code> contained within this Page
             */
            this.getPlaceables = $._Common.getPlaceables;

            Page._PROPS = [

                /**
                 * Returns the "activeImage" attribute for this <code>Page</code>, if defined.
                 *
                 * @method getActiveImage
                 * @returns {string}
                 *      The value of the "activeImage" attribute for this <code>Page</code>;
                 *      null if not defined
                 */
                'activeImage',

                /**
                 * Returns the "inactiveImage" attribute for this <code>Page</code>, if defined.
                 *
                 * @method getInactiveImage
                 * @returns {string}
                 *      The value of the "inactiveImage" attribute for this <code>Page</code>;
                 *      null if not defined
                 */
                'inactiveImage',

                /**
                 * Returns the "rolloverImage" attribute for this <code>Page</code>, if defined.
                 *
                 * @method getRolloverImage
                 * @returns {string}
                 *      The value of the "rolloverImage" attribute for this <code>Page</code>;
                 *      null if not defined
                 */
                'rolloverImage',

                /**
                 * When customization has been enabled this method will return true when the user is
                 * entitled to make updates to this page, false otherwise.
                 *
                 * @method getUpdateable
                 * @returns {boolean}
                 *      true when a user is entitled to make updates to this page; false otherwise.
                 * @see Application#getCustomizationEnabled
                 */
                'updateable'
            ];
        });

        /**
         * Instances of the <code>Book</code> class represent visible books of the rendered Portal.
         *
         * @class Book
         * @extends Page
         */
        $.Book = $.Page.extend(function(Book) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Book</code> object
             * @param {object} props
             *      The properties of this <code>Book</code> object
             */

            /**
             * Returns the <code>Menu</code> corresponding to this <code>Book</code>. Under circumstances
             * where the menu is not visible (e.g. not defined) this method will return null.
             *
             * @method getMenu
             * @returns {Menu}
             *      The <code>Menu</code> for this <code>Book</code>; null if not visible
             */
            this.getMenu = function() {
                var element = this.getMarkupElement();
                var menu = null;
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if (ctx instanceof $.Window) {
                            proceed = false;
                        }
                        else if (ctx instanceof $.Menu) {
                            menu = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                }, 'div');
                return menu;
            };

            /**
             * Returns the <code>Page</code> contained as an immediate child of this <code>Book</code>.
             * <p/>
             * <b>NOTE</b>: This method is polymorphic - it returns a child of type <code>Page</code>. This
             * may produce unexpected results when a <code>Book</code> is the immediate child of another
             * <code>Book</code>, as the return value will be the child <code>Book</code>
             *
             * @method getPage
             * @returns {Page}
             *      The <code>Page</code> contained within this <code>Book</code>
             */
            this.getPage = function() {
                var element = this.getMarkupElement();
                var page = null;
                $Util.Dom.eachDescendantLinear(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && (ctx instanceof $.Page)) {
                        page = ctx;
                        proceed = false;
                    }
                    return proceed;
                }, 'div');
                return page;
            };

            /**
             * Updates the <code>placeholderPosition</code> of this <code>Book</code>.
             * <p/>
             * <b>NOTE</b>: This method only updates the state of this client-side <code>Book</code> object;
             * it does not update the state of the server-side book object. It is the responsibility of the
             * application code calling this method to ensure that client-side and server-side state are in sync.             *
             *
             * @private
             * @method setPlaceholderPosition
             * @param {number} placeholderPosition
             *      The new value for the placeholderPosition property
             * @see #getPlaceholderPosition
             */
            this.setPlaceholderPosition = function(placeholderPosition) {
                this._props['placeholderPosition'] = placeholderPosition;
            };

            Book._MIXINS = [

                /**
                 * Returns the parent <code>Placeholder</code> of this <code>Book</code>.
                 * <p/>
                 * There are generally two kinds of <code>books</code>: <i>placeable</i> and <i>navigable</i>.
                 * Placeable books (also known as nested books) are those placed immediately within placeholders.
                 * Navigable books are those placed immediately within other books, including the main book.
                 *
                 * @method getParentPlaceholder
                 * @returns {Placeholder}
                 *      The parent <code>Placeholder</code> of this <code>Book</code> if this is a placeable book;
                 *      null otherwise
                 */
                'Placeable'
            ];

            Book._PROPS = [

                /**
                 * Returns this <code>Book's</code> position within its parent <code>Placeholder</code>.
                 * Positions are ordinal numbers such that <code>Windows</code> with the same
                 * <code>Placeholder</code> parent have the positions 0, 1, 2, 3, 4, <em>...</em>.
                 * <p/>
                 * Iteration over <code>Placeholder</code> children may produce a non-contiguous set of
                 * positions if some children are not visible. Calling this method on a <code>Book</code> without
                 * a parent <code>Placeholder</code> will return null.
                 *
                 * @method getPlaceholderPosition
                 * @returns {number}
                 *      The position of this <code>Book</code> within the parent <code>Placeholder</code>;
                 *      null if this <code>Book</code> is not a child of a <code>Placeholder</code> 
                 */
                'placeholderPosition'
            ];
        });

        /**
         * Instances of the <code>Portlet</code> class represent visible portlets of the rendered Portal.
         *
         * @class Portlet
         * @extends Window
         */
        $.Portlet = $.Window.extend(function(Portlet) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Portlet</code> object
             * @param {object} props
             *      The properties of this <code>Portlet</code> object
             */

            /**
             * Returns the label of this <code>Portlet</code>. This is equivalent to calling
             * <code>getInstanceLabel</code>.
             *
             * @method getLabel
             * @returns {string}
             *      The instance label of this <code>Window</code>
             * @see #getInstanceLabel
             */
            this.getLabel = function() {
                return this.getInstanceLabel();
            };

            /**
             * Updates the <code>placeholderPosition</code> of this <code>Portlet</code>.
             * <p/>
             * <b>NOTE</b>: This method only updates the state of this client-side <code>Portlet</code> object;
             * it does not update the state of the server-side portlet object. It is the responsibility of the
             * application code calling this method to ensure that client-side and server-side state are in sync.
             *
             * @method setPlaceholderPosition
             * @param {number} placeholderPosition
             *      The new value for the placeholderPosition property
             * @see #getPlaceholderPosition
             */
            this.setPlaceholderPosition = function(placeholderPosition) {
                this._props['placeholderPosition'] = placeholderPosition;
            };

            Portlet._MIXINS = [

                /**
                 * Returns the parent <code>Placeholder</code> of this <code>Portlet</code>. Under some
                 * circumstances this method may return null (e.g. if the portlet is maximized).
                 *
                 * @method getParentPlaceholder
                 * @returns {Placeholder}
                 *      The parent <code>Placeholder</code> of this <code>Portlet</code>; null if this
                 *      <code>Portlet</code> does not have a parent <code>Placeholder</code>
                 */
                'Placeable'
            ];

            Portlet._PROPS = [

                /**
                 * Returns the "asyncContent" attribute for this <code>Portlet</code>.
                 *
                 * @method getAsyncContent
                 * @returns {boolean}
                 *      The value of the "asyncContent" attribute for this <code>Portlet</code>
                 */
                'asyncContent',

                /**
                 * Returns the instance label of this <code>Portlet</code>.
                 *
                 * @method getInstanceLabel
                 * @returns {string}
                 *      The instance label of this <code>Portlet</code>.
                 */
                'instanceLabel',

                /**
                 * Returns this <code>Portlet's</code> position within its parent <code>Placeholder</code>.
                 * Positions are ordinal numbers such that <code>Windows</code> with the same
                 * <code>Placeholder</code> parent have the positions 0, 1, 2, 3, 4, <em>...</em>.
                 * <p/>
                 * Iteration over <code>Placeholder</code> children may produce a non-contiguous set of
                 * positions if some children are not visible. Calling this method on a <code>Portlet</code>
                 * without a parent <code>Placeholder</code> will return null.
                 *
                 * @method getPlaceholderPosition
                 * @returns {number}
                 *      The position of this <code>Portlet</code> within the parent <code>Placeholder</code>;
                 *      null if this <code>Portlet</code> is not a child of a <code>Placeholder</code> 
                 */
                'placeholderPosition'
            ];
        });

        $._Content = $.HookedContext.extend();

        /**
         * Instances of the <code>Theme</code> class represent visible themes of the rendered Portal.
         *
         * @class Theme
         * @extends PresentationContext
         */
        $.Theme = $.PresentationContext.extend(function(Theme) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Theme</code> object
             * @param {object} props
             *      The properties of this <code>Theme</code> object
             */

            /**
             * Returns the <code>Window</code> contained as an immediate child of this <code>Theme</code>.
             *
             * @method getWindow
             * @returns {Window}
             *      The immediate <code>Window</code> child of this Theme
             */
            this.getWindow = function() {
                var element = this.getMarkupElement();
                var theme = null;
                $Util.Dom.eachDescendantLinear(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && (ctx instanceof $.Window)) {
                        theme = ctx;
                        proceed = false;
                    }
                    return proceed;
                }, 'div');
                return theme;
            };

            Theme._PROPS = [

                /**
                 * Returns true if this <code>Theme</code> includes skeleton resources.
                 *
                 * @method getUsingAltSkeleton
                 * @returns {boolean}
                 *      True if this <code>Theme</code> includes skeleton resources; false otherwise
                 */
                'usingAltSkeleton',

                /**
                 * Returns true if this <code>Theme</code> includes skin resources.
                 *
                 * @method getUsingAltSkin
                 * @returns {boolean}
                 *      True if this <code>Theme</code> includes skin resources; false otherwise
                 */
                'usingAltSkin',

                /**
                 * Returns the markup name of this <code>Theme</code> if defined.
                 *
                 * @method getMarkupName
                 * @returns {string}
                 *      The markup name of this <code>Theme</code>; null if not defined
                 */
                'markupName',

                /**
                 * Returns the name of this <code>Theme</code>.
                 *
                 * @method getName
                 * @returns {string}
                 *      The name of this <code>Theme</code>.
                 */
                'name'
            ];
        });

        /**
         * Instances of the <code>Titlebar</code> class represent visible titlebars of the rendered Portal.
         *
         * @class Titlebar
         * @extends PresentationContext
         */
        $.Titlebar = $.PresentationContext.extend(function(Titlebar) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Titlebar</code> object
             * @param {object} props
             *      The properties of this <code>Titlebar</code> object
             */

            /**
             * Returns an array of mode and state buttons available via this <code>Titlebar</code>.
             *
             * @method getButtons
             * @return {AbstractButton[]}
             *      Array of available mode & state buttons
             *
             */
            this.getButtons = function() {
                var element = this.getMarkupElement();
                var buttons = [];
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if (ctx.getType() == $._Content.TYPE) {
                            proceed = false;
                        }
                        else if (ctx instanceof $.AbstractButton) {
                            buttons.push(ctx);
                        }
                    }
                    return proceed;
                }, 'div');
                return buttons;
            };

            Titlebar._PROPS = [

                /**
                 * Returns the "icon" attribute for this <code>Titlebar</code>, if defined.
                 *
                 * @method getIcon
                 * @returns {string}
                 *      The value of the "icon" attribute for this <code>Titlebar</code>;
                 *      null if not defined
                 */
                'icon'
            ];
        });

        /**
         * Instances of the <code>AbstractButton</code> class represent visible buttons of the rendered Portal.
         * <code>AbstractButton</code> is intended to be an abstract base class from which specific button types
         * are derived.
         *
         * @class AbstractButton
         * @extends PresentationContext
         */
        $.AbstractButton = $.PresentationContext.extend(function(AbstractButton) {
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>AbstractButton</code> object
             * @param {object} props
             *      The properties of this <code>AbstractButton</code> object
             */

            AbstractButton._PROPS = [

                /**
                 * Returns the "altText" attribute for this <code>AbstractButton</code>, if defined.
                 *
                 * @method getAltText
                 * @returns {string}
                 *      The value of the "altText" attribute for this <code>AbstractButton</code>;
                 *      null if not defined
                 */
                'altText',

                /**
                 * Returns the "image" attribute for this <code>AbstractButton</code>, if defined.
                 *
                 * @method getImage
                 * @returns {string}
                 *      The value of the "image" attribute for this <code>AbstractButton</code>;
                 *      null if not defined
                 */
                'image',

                /**
                 * Returns the name of this <code>AbstractButton</code>.
                 *
                 * @method getName
                 * @returns {string}
                 *      The name of this <code>AbstractButton</code>
                 */
                'name',

                /**
                 * Returns the "rolloverImage" attribute for this <code>AbstractButton</code>, if defined.
                 *
                 * @method getRolloverImage
                 * @returns {string}
                 *      The value of the "rolloverImage" attribute for this <code>AbstractButton</code>;
                 *      null if not defined
                 */
                'rolloverImage'
            ];
        });

        /**
         * Instances of the <code>Button</code> class represent visible buttons of the rendered Portal.
         *
         * @class Button
         * @extends AbstractButton
         */
        $.Button = $.AbstractButton.extend({
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>Button</code> object
             * @param {object} props
             *      The properties of this <code>Button</code> object
             */
        });

        /**
         * Instances of the <code>ToggleButton</code> class represent visible buttons of the rendered Portal.
         * Conceptually <code>ToggleButton</code> objects differ from <code>Button</code> objects due to the
         * the presence of opposing "modes". However, no specific information about these "modes" is exposed
         * by this class; only the distinction of the different buton type is supported by this class.
         *
         * @class ToggleButton
         * @extends AbstractButton
         */
        $.ToggleButton = $.AbstractButton.extend({
            
            /**
             * @private
             * @method initialize
             * @param {number} id
             *      The ID of the HTML container element associated with this <code>ToggleButton</code> object
             * @param {object} props
             *      The properties of this <code>ToggleButton</code> object
             */
        });
    }
});
});
bea.wlp.disc.Module._include("_Mixin", "bea.wlp.disc.context", function() {
// bea.wlp.disc.context[_Mixin]
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
    declare: function($) {

        var $Util = bea.wlp.disc._util;

        $._Common = {

            getParentPage: function() {
                var element = this.getMarkupElement();
                var page = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && (ctx instanceof $.Page)) {
                        page = ctx;
                        proceed = false;
                    }
                    return proceed;
                });
                return page;
            },

            getParentPlaceholder: function() {
                var element = this.getMarkupElement();
                var placeholder = null;
                $Util.Dom.eachAncestor(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx) {
                        if ((ctx instanceof $.Layout) || (ctx instanceof $.Window)) {
                            proceed = false;
                        }
                        else if (ctx.getType() == $.Placeholder.TYPE) {
                            placeholder = ctx;
                            proceed = false;
                        }
                    }
                    return proceed;
                });
                return placeholder;
            },

            getPlaceables: function() {
                var element = this.getMarkupElement();
                var placeables = [];
                $Util.Dom.eachDescendantRecursive(element, function(el) {
                    var ctx = el[$._HookContextProperty];
                    var proceed = true;
                    if (ctx && ((ctx.getType() == $.Book.TYPE) || (ctx.getType() == $.Portlet.TYPE))) {
                        placeables.push(ctx);
                        proceed = false;
                    }
                    return proceed;
                }, 'div');
                return placeables;
            }

        };

        $._Mixin = {

            Placeable: {

            }
        };

        $._Mixin.Placeable.getParentPlaceholder = $._Common.getParentPlaceholder;
    }
});
});
});
