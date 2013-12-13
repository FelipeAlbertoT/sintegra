/*
    Requer: jquery.datatable.js
*/

(function ($) {

    var defaults = {
        "MultiRowSelection": true,
        "trClickCallback" : function () { },
        "sortColumn" : 0,
        "sortOrder" : "asc",
        "sAjaxSource" : null,
        "fnServerData" : null,
        "aoColumnDefs" : null,
        "bProcessing": false,
        "bServerSide": false,
        "bFilter": true,
        "bPaginate": true,
        "bInfo": true,
        "iDisplayLength": 10
    };

    var settings, selector, oTable;

    $.fn.kappDataTable = function (options) {

        if (options) {
            settings = $.extend({}, defaults, options);
        }

        selector = this.selector;

        if (settings.MultiRowSelection) {
            $(selector).find('tbody tr').click(function () {
                if ($(this).hasClass('row_selected')){               
                    $(this).removeClass('row_selected');
                }
                else {
                    $(this).addClass('row_selected');
                }                
            });
        }
        else {
            $(selector).find('tbody tr').click(function () {
                if ($(this).hasClass('row_selected'))
                    $(this).removeClass('row_selected');
                else {
                    $(oTable.fnSettings().aoData).each(function () {
                        $(this.nTr).removeClass('row_selected');
                    });
                    $(this).addClass('row_selected');
                }
            });
        }
        
        $(selector).find('tbody tr').click(function () {        	
        	settings.trClickCallback();
        });

        oTable = $(selector).dataTable(
        {
            "bDestroy": true,
            "sAjaxSource" : settings.sAjaxSource,
            "fnServerData" : settings.fnServerData,
            "aoColumnDefs" : settings.aoColumnDefs,
            "bProcessing": settings.bProcessing,
            "bServerSide": settings.bServerSide,
            "bFilter": settings.bFilter,
            "bPaginate": settings.bPaginate,
            "bInfo": settings.bInfo,
            "iDisplayLength" : settings.iDisplayLength
        });
        
        oTable.fnSort( [ [settings.sortColumn, settings.sortOrder] ] );

        return $.fn.kappDataTable;
    };
    
    $.fn.kappDataTable.fnFilter = function (value, index) {
        oTable.fnFilter(value, index);
    };

    $.fn.kappDataTable.UnselectAll = function () {
        $(oTable.fnSettings().aoData).each(function () {
            $(this.nTr).removeClass('row_selected');
        });
    };

    $.fn.kappDataTable.ClearTable = function () {
        oTable.fnClearTable(this);
        $(selector).find('tbody').empty();
    };

    $.fn.kappDataTable.Selected = function () {

        objects = new Array();
        
        $(oTable.fnSettings().aoData).each(function () {
        	
            if($(this.nTr).hasClass('row_selected')){
            	
            	obj = null;

            	$(this.nTr).find('td').each(function () {

                    propertyName = $(this).attr("oName");
                    propertyValue = $(this).attr("oValue");
                    
                    if (propertyName != undefined && propertyName != null && propertyValue != undefined && propertyValue != null) {

                        if (obj == null)
                            obj = new Object();

                        eval("obj." + propertyName + "='" + propertyValue + "'");
                    }
                });

                if (obj != null)
                    objects.push(obj);
            	
            }
        });
        
        return objects;
    };

})(jQuery);