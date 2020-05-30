function pageToDate(ele, tagele) {
    var $postDate = {
        segments: [],
        mould: ""
    };
    $postDate.mould = $(ele).attr("name");
    $(ele).find(tagele).each(function () {
        var data = {
            segmentItems: []
        };

        data.title = $(this).find('dt span').text();
        data.styletype = $(this).attr("name");
        if ($(this).find('.fc-sub2').length > 0) {
            var objpara = {
                ele: this,
                box: ".contentBox2",
                tag: ".fc-sub2",
                childele: ".settledspan",
                subkey: ".subkey",
                subvalue: ".subvalue",

            };
            if (!isDomObject(objpara.ele)) {
                return alert("模板不规范,请重新选择")
            }
            data.segmentItems = htmlTodate(objpara)
        }
        $postDate.segments.push(data)
    });
    return $postDate;
}

function htmlTodate(obj) {
    var ele = obj.ele;
    var box = obj.box || ".contentBox2";
    var tag = obj.tag || ".fc-sub2";
    var childele = obj.childele || ".settledspan";
    var subkey = obj.subkey || ".subkey";
    var subvalue = obj.subvalue || ".subvalue";
    var boxstr = box.slice(0, -1);
    var num = Number(box.slice(-1));
    var data = [];

    $(ele).find(box).find(tag).each(function () {
        var $info = {
            segmentItemImgs: []
        };
        $info.key = $(this).children(childele).children(subkey).text();
        $info.val = $(this).children(childele).children(subvalue).text();
        $(this).children('img').each(function () {
            $info.segmentItemImgs.push({imgId: $(this).attr("name")});
        });
        if ($(this).find(boxstr + (num + 1)).length > 0) {
            $info.children = htmlTodate({
                'ele': this,
                'box': boxstr + (num + 1),
                "tag": tag.slice(0, -1) + (num + 1)
            });
        }
        data.push($info)
    });
    return data
}


function isDomObject(obj) {
    var isDOM = (typeof HTMLElement === 'object') ?
        function (obj) {
            return obj instanceof HTMLElement;
        } :
        function (obj) {
            return obj && typeof obj === 'object' && obj.nodeType === 1 && typeof obj.nodeName === 'string';
        };
    return isDOM;

}