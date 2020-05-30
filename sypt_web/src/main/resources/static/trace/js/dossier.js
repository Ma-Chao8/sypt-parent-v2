function bindDrag(ele, type) {
    var $type = type || "template";
    $(ele).each(function () {
        //console.log(this);
        var $src = $(this).parents("li").find('img').attr("src") || "";
        var $enclosure_id = $(this).parents("li").find('img').attr("name") || "";// 这里加上了附件id
        //console.log(this);
        this.addEventListener("dragstart", function (event) {
            //event.preventDefault();
            console.log("dragstart", this);
            var $typeID = Number($(this).attr("name"));
            var $parentId = $(this).attr("parentId");
            var $data = {
                'type': $type,
                'typeID': $typeID,
                'parentId': $parentId,
                'src': $src,
                'enclosure_id': $enclosure_id
            };
            console.log("send data ",$data);
            event.dataTransfer.setData("Text", JSON.stringify($data));
        }, false)
    })
}

function bindDrop(ele, obj) {
    $(ele).get(0).addEventListener("drop", function (event) {
        event.stopPropagation();
        event.preventDefault();
        var $data = JSON.parse(event.dataTransfer.getData("Text"));
        var self = event.target;
        var typeID = Number($data.typeID);
        var parentId = $data.parentId;
        var type = $data.type;
        //console.log("drop", self, this, obj, $data);
        var newtplobj = $.grep(obj, function (item, index) {
            if (item.style == parentId) {
                return item;
            }
        })[0];
        switch (type) {
            case "template": {
                console.log(typeID, typeID == "1", typeID == "2");
                if (typeID == "1") {
                    if ($(self).parents(".area").length > 0) {
                        if (newtplobj["sub2"])
                            $(self).parents(".area").after(newtplobj["sub"])
                    } else {
                        if (newtplobj["sub2"])
                            $("ele").append(newtplobj["sub"]);
                        checkele("#fc-content-body", ".area")
                    }
                }

                if (typeID == "2") {
                    if (!($(self).parents('.area').attr('name') == parentId)) {
                        return alert("请将标题拖到对应的样式块中")
                    }

                    if ($(self).parent().parent().is(".fc-sub2")) {
                        if (newtplobj["sub2"])
                            $(self).parents(".fc-sub2").after(newtplobj["sub2"])
                    } else if ($(self).parents(".settledspan").is(".fc-sub1")) {
                        if (newtplobj["sub2"])
                            $(self).parents("dl").find(".contentBox2").append(newtplobj["sub2"])
                    } else {
                        return;
                    }
                }

                if (typeID == "3") {
                    if (!($(self).parents('.area').attr('name') == parentId)) {
                        return alert("请将标题拖到对应的样式块中")
                    }
                    if ($(self).parent().parent().is(".fc-sub3")) {
                        if (newtplobj["sub2"])
                            $(self).parents(".fc-sub3").after(newtplobj["sub3"])
                    } else if ($(self).parents("ul").is(".contentBox3")) {
                        if (newtplobj["sub2"])
                            $(self).parents("ul").append(newtplobj["sub2"])
                    } else {
                        return;
                    }

                }
                break;
            }


            case "images": {
                console.log(typeID, typeID == "1", typeID == "2");
                //新增图片
                if (typeID == "1") {
                    if ($(self).parent().is("a")) {
                        $(self).parent('a').after("<a href='#'><img src=" + $data.src + " class='mt-10'></a>")
                    } else if ($(self).is("img")) {
                        $(self).after("<img name='" + $data.enclosure_id + "' src=" + $data.src + " class='mt-10'>")
                    } else if ($(self).parent().parent().is(".fc-sub2,.fc-sub3,.fc-sub4")) {
                        $(self).parent().after("<img src=" + $data.src + " class='mt-10'  name='" + $data.enclosure_id + "'> ")
                    }
                }
                //替换换图片
                else if (typeID == "2") {
                    if ($(self).is("img")) {
                        $(self).attr("src", $data.src);
                        $(self).attr("name", $data.enclosure_id);
                    }
                }
                break;
            }
        }
    }, false)
}

function checkele(parEle, goalEle) {
    //console.log($(parEle).find(goalEle).length)
    if ($(parEle).find(goalEle).length < 1) {
        $(parEle).addClass("borderDiv")
    } else {
        $(parEle).removeClass("borderDiv")
    }
}
