PrimeFaces.widget.AjaxStatus=PrimeFaces.widget.BaseWidget.extend({init:function(a){this._super(a);this.bind()},bind:function(){var b=$(document),a=this;b.on("pfAjaxStart",function(){a.trigger("start",arguments)}).on("pfAjaxError",function(){a.trigger("error",arguments)}).on("pfAjaxSuccess",function(){a.trigger("success",arguments)}).on("pfAjaxComplete",function(){a.trigger("complete",arguments)});this.bindToStandard()},trigger:function(b,a){var c=this.cfg[b];if(c){c.apply(document,a)}this.jq.children().hide().filter(this.jqId+"_"+b).show()},bindToStandard:function(){if(window.jsf&&window.jsf.ajax){var a=$(document);jsf.ajax.addOnEvent(function(b){if(b.status==="begin"){a.trigger("pfAjaxStart",arguments)}else{if(b.status==="complete"){a.trigger("pfAjaxSuccess",arguments)}else{if(b.status==="success"){a.trigger("pfAjaxComplete",arguments)}}}});jsf.ajax.addOnError(function(b){a.trigger("pfAjaxError",arguments)})}}});