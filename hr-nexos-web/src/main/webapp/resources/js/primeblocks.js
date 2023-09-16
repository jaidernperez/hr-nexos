var PrimeBlocks = {
    styleClass: function(el) {
        var props = PrimeBlocks.utils.getProps(el, 'pbStyleclass');

        return {
            init: function() {
                if (el) {
                    el['styleclass-initialized'] = true;

                    this.bindEvents();
                }

                return this;
            },

            run: function() {
                this.target = this.resolveTarget();
    
                if (props.toggleClass) {
                    if (PrimeBlocks.utils.hasClass(this.target, props.toggleClass))
                        PrimeBlocks.utils.removeClass(this.target, props.toggleClass);
                    else
                        PrimeBlocks.utils.addClass(this.target, props.toggleClass);
                }
                else {
                    if (this.target.offsetParent === null)
                        this.enter();
                    else
                        this.leave();
                }
            },

            enter: function() {
                var $this = this;
    
                if (props.enterActiveClass) {
                    if (!this.animating) {
                        this.animating = true;
    
                        if (props.enterActiveClass === 'slidedown') {
                            this.target.style.height = '0px';
                            PrimeBlocks.utils.removeClass(this.target, 'hidden');
                            this.target.style.maxHeight = this.target.scrollHeight + 'px';
                            PrimeBlocks.utils.addClass(this.target, 'hidden');
                            this.target.style.height = '';
                        }
    
                        PrimeBlocks.utils.addClass(this.target, props.enterActiveClass);
                        if (props.enterClass) {
                            PrimeBlocks.utils.removeClass(this.target, props.enterClass);
                        }
    
                        this.enterListener = function() {
                            PrimeBlocks.utils.removeClass($this.target, props.enterActiveClass);
                            if (props.enterToClass) {
                                PrimeBlocks.utils.addClass($this.target, props.enterToClass);
                            }
                            $this.target.removeEventListener('animationend', $this.enterListener);
    
                            if (props.enterActiveClass === 'slidedown') {
                                $this.target.style.maxHeight = '';
                            }
                            $this.animating = false;
                        };
    
                        this.target.addEventListener('animationend', this.enterListener);
                    }
                }
                else {
                    if (props.enterClass) {
                        PrimeBlocks.utils.removeClass(this.target, props.enterClass);
                    }
    
                    if (props.enterToClass) {
                        PrimeBlocks.utils.addClass(this.target, props.enterToClass);
                    }
                }
    
                if (props.hideOnOutsideClick) {
                    this.bindDocumentListener();
                }
            },
    
            leave: function() {
                var $this = this;
    
                if (props.leaveActiveClass) {
                    if (!this.animating) {
                        this.animating = true;
                        PrimeBlocks.utils.addClass(this.target, props.leaveActiveClass);
                        if (props.leaveClass) {
                            PrimeBlocks.utils.removeClass(this.target, props.leaveClass);
                        }
    
                        this.leaveListener = function() {
                            PrimeBlocks.utils.removeClass($this.target, props.leaveActiveClass);
                            if (props.leaveToClass) {
                                PrimeBlocks.utils.addClass($this.target, props.leaveToClass);
                            }
                            $this.target.removeEventListener('animationend', $this.leaveListener);
                            $this.animating = false;
                        };
    
                        this.target.addEventListener('animationend', this.leaveListener);
                    }
                }
                else {
                    if (props.leaveClass) {
                        PrimeBlocks.utils.removeClass(this.target, props.leaveClass);
                    }
    
                    if (props.leaveToClass) {
                        PrimeBlocks.utils.addClass(this.target, props.leaveToClass);
                    }
                }
    
                if (props.hideOnOutsideClick) {
                    this.unbindDocumentListener();
                }
            },
    
            resolveTarget: function() {
                if (this.target) {
                    return this.target;
                }
    
                switch (props.selector) {
                    case '@next':
                        return el.nextElementSibling;
    
                    case '@prev':
                        return el.previousElementSibling;
    
                    case '@parent':
                        return el.parentElement;
    
                    case '@grandparent':
                        return el.parentElement.parentElement;
    
                    default:
                        return document.querySelector(props.selector);
                }
            },
    
            bindDocumentListener: function() {
                if (!this.documentListener) {
                    var $this = this;
    
                    this.documentListener = function(event) {
                        if (getComputedStyle($this.target).getPropertyValue('position') === 'static') {
                            $this.unbindDocumentListener();
                        }
                        else if (!el.isSameNode(event.target) && !el.contains(event.target) && !$this.target.contains(event.target)) {
                            $this.leave();
                        }
                    };
    
                    el.ownerDocument.addEventListener('click', this.documentListener);
                }
            },
    
            unbindDocumentListener: function() {
                if (this.documentListener) {
                    el.ownerDocument.removeEventListener('click', this.documentListener);
                    this.documentListener = null;
                }
            },
    
            bindEvents: function() {
                var $this = this;
                this.clickListener = function() {
                    $this.run();
                }
    
                el.addEventListener('click', this.clickListener);
            }
        }
    },
    tabList: function(list, tabs, tab) {
        var props = PrimeBlocks.utils.getProps(tab, 'pbTab');

        return {
            init: function() {
                if (tab) {
                    list['tablist-initialized'] = true;

                    this.bindEvents();
                }

                return this;
            },

            run: function() {
                this.selector = this.resolveSelector();
    
                if (!PrimeBlocks.utils.hasClass(tab, props.toggleClass)) {
                    for (var i = 0; i < tabs.length; i++) {
                        var tb = tabs[i];
                        var tb_data = tb.dataset.pbTab.replace(/(['"])?([a-z0-9A-Z_]+)(['"])?:/g, '"$2": ').replaceAll('\'', '"');
                        var tb_props = JSON.parse(tb_data) || {};

                        PrimeBlocks.utils.removeClass(tb, tb_props.toggleClass);
                        PrimeBlocks.utils.addClass(document.querySelector(tb_props.selector), tb_props.selectorToggleClass);
                    }

                    PrimeBlocks.utils.addClass(tab, props.toggleClass);
                    PrimeBlocks.utils.removeClass(this.selector, props.selectorToggleClass);
                }
            },

            bindEvents: function() {
                var $this = this;
                this.clickListener = function() {
                    $this.run();
                }
    
                tab.addEventListener('click', this.clickListener);
            },

            resolveSelector: function() {
                if (this.selector) {
                    return this.selector;
                }
    
                return document.querySelector(props.selector);
            }
        }
    },
    utils: {
        getProps: function(el, dataName) {
            if (el) {
                var data = el.dataset[dataName].replace(/(['"])?([a-z0-9A-Z_]+)(['"])?:/g, '"$2": ').replaceAll('\'', '"');
                var props = JSON.parse(data) || {};
        
                return props;
            }

            return {};
        },

        addClass: function(element, className) {
            if (element && className) {
                if (element.classList)
                    element.classList.add(className);
                else
                    element.className += ' ' + className;
            }
        },

        removeClass: function(element, className) {
            if (element && className) {
                if (element.classList)
                    element.classList.remove(className);
                else
                    element.className = element.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
            }
        },

        hasClass: function(element, className) {
            if (element) {
                if (element.classList)
                    return element.classList.contains(className);
                else
                    return new RegExp('(^| )' + className + '( |$)', 'gi').test(element.className);
            }
        },

        find: function(element, selector) {
            return element ? Array.from(element.querySelectorAll(selector)) : [];
        }
    }
}

$(function() {
    /* StyleClass */
    $(document.body).off('click.primeblock-styleclass', '*[data-pb-styleclass]').on('click.primeblock-styleclass', '*[data-pb-styleclass]', function() {
        if (!this['styleclass-initialized']) {
            PrimeBlocks.styleClass(this).init().run();
        }
    });

    /* TabList */
    $(document.body).off('click.primeblock-tablist', '*[data-pb-tab]').on('click.primeblock-tablist', '*[data-pb-tab]', function() {
        var list = $(this).closest('*[data-pb-tablist]')[0];
        if (!list['tablist-initialized']) {
            var tabs = $(list).find('*[data-pb-tab]');

            for (var i = 0; i < tabs.length; i++) {
                var tab = tabs[i];
                var model = PrimeBlocks.tabList(list, tabs, tab).init();
                if (tab === this) model.run();
            }
        }
    });
}); 