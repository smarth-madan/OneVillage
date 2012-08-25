/**
*    raphael.arrowSet plugin
*    Copyright (c) 2011 @author: top-flight
*    
*    Licensed under the MIT license
*/

(function() {

    Raphael.fn.arrowSet = function (x1, y1, x2, y2, r) {
        var paper = this,
            arrow = paper.set();
        arrow.push(paper.path(triangle(x2, y2 - (r / 2), r)).rotate(((Math.atan2(x1 - x2, y2 - y1) / (2 * Math.PI)) * 360) + 180, x2, y2));
        arrow.push(paper.path(["M", x1, y1, "L", x2, y2]));
        return arrow;
    };
    
    /**
    * Triangle path string
    * Adapted from raphael.primitives.js
    * For more info visit: https://github.com/DmitryBaranovskiy/raphael
    */
    function triangle (cx, cy, r) {
        r *= 1.75;
        return "M".concat(cx, ",", cy, "m0-", r * .58, "l", r * .5, ",", r * .87, "-", r, ",0z");
    }
})();