function getProduct(productId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/product/all",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            console.log(data)
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.productId == productId){
                    htmlText += "<option selected = 'selected' value='"+item.productId+"'> "+item.productName+" </option>"
                }else{
                    htmlText += "<option value='"+item.productId+"' box='"+item.boxSize+"'> "+item.productName+" </option>"
                }

            }

            $("#productId").append(htmlText);
        }
    });
}

function getProcedures(procedureId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/procedures/procedures/all",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            console.log(data)
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.procedureId == procedureId){
                    htmlText += "<option selected = 'selected' value='"+item.procedureId+"'> "+item.name+" </option>"
                }else{
                    htmlText += "<option value='"+item.procedureId+"'> "+item.name+" </option>"
                }

            }

            $("#proceduresId").append(htmlText);
        }
    });
}

function getSupplierTypeByUpdate(typeId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/supplier/supplierType/getSupplierList",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            console.log(data)
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.supplierTypeId == typeId){
                    htmlText += "<option selected = 'selected' value='"+item.supplierTypeId+"'> "+item.typeName+" </option>"
                }else{
                    htmlText += "<option value='"+item.supplierTypeId+"'> "+item.typeName+" </option>"
                }

            }

            $("#supplierTypeId").append(htmlText);
        }
    });
}

function getSupplierByUpdate(supplierId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/supplier/supplier/getSupplierList",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.supplierId == supplierId){
                    htmlText += "<option selected='selected' value='"+item.supplierId+"'> "+item.supplierName+" </option>"
                }else{
                    htmlText += "<option value='"+item.supplierId+"'> "+item.supplierName+" </option>"
                }
            }
            $("#supplierId").append(htmlText);
        }
    });
}

function getMaterialByUpdate(materialId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"material/material/getMaterialList",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.materialId == materialId){
                    htmlText += "<option selected='selected' value='"+item.materialId+"'> "+item.materialName+" </option>"
                }else{
                    htmlText += "<option value='"+item.materialId+"'> "+item.materialName+" </option>"
                }
            }
            $("#materialId").append(htmlText);
        }
    });
}

function getCheckMechanismByUpdate(checkMechanismId) {
    $('#checkMechanismId').empty();
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/check/checkMechanism/all",
        data : {
            "checkMechanismName" : $('#checkMechanismName').val()
        },
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.checkMechanismId == checkMechanismId){
                    htmlText += "<option selected='selected' value='"+item.checkMechanismId+"'> "+item.checkMechanismName+" </option>"
                }else{
                    htmlText += "<option value='"+item.checkMechanismId+"'> "+item.checkMechanismName+" </option>"
                }
            }
            $("#checkMechanismId").append(htmlText);
        }
    });
}

function getCheckTypeByUpdate(checkTypeId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/check/checkType/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.checkTypeId == checkTypeId){
                    htmlText += "<option selected='selected' value='"+item.checkTypeId+"'> "+item.checkTypeName+" </option>"
                }else{
                    htmlText += "<option value='"+item.checkTypeId+"'> "+item.checkTypeName+" </option>"
                }
            }
            $("#checkTypeId").append(htmlText);
        }
    });
}


function getIndustryByUpdate(industryId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/industry/industry/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.industryId == industryId){
                    htmlText += "<option selected='selected' value='"+item.industryId+"'> "+item.industryName+" </option>"
                }else{
                    htmlText += "<option value='"+item.industryId+"'> "+item.industryName+" </option>"
                }
            }
            $("#industryId").append(htmlText);
        }
    });
}


function getReportByUpdate(reportId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/report/report/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.reportId == reportId){
                    htmlText += "<option selected='selected' value='"+item.reportId+"'> "+item.reportName+" </option>"
                }else{
                    htmlText += "<option value='"+item.reportId+"'> "+item.reportName+" </option>"
                }
            }
            $("#reportId").append(htmlText);
        }
    });
}

// function getReportByUpdate(reportId) {
//     $.ajax({
//         cache : true,
//         type : "GET",
//         url : getContext()+"/report/report/all",
//         // data : $('#signupForm').serialize(),// 你的formid
//         async : false,
//         error : function(request) {
//             parent.layer.alert("Connection error");
//         },
//         success : function(data) {
//             var itemList = data.data;
//             var htmlText ="";
//             for (var i in itemList){
//                 var item = itemList[i];
//                 if (item.reportId == reportId){
//                     htmlText += "<option selected='selected' value='"+item.reportId+"'> "+item.reportName+" </option>"
//                 }else{
//                     htmlText += "<option value='"+item.reportId+"'> "+item.reportName+" </option>"
//                 }
//             }
//             $("#reportId").append(htmlText);
//         }
//     });
// }

function getAttr(attrId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : ctx+"/attr/attr/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.attrId == attrId){
                    htmlText += "<option selected='selected' value='"+item.attrId+"'> "+item.attrName+" </option>"
                }else{
                    htmlText += "<option value='"+item.attrId+"'> "+item.attrName+" </option>"
                }
            }
            $("#attrId").append(htmlText);
        }
    });
}

function getPurchaseByUpdate(purchaseId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/material/purchase/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.purchaseId == purchaseId){
                    htmlText += "<option selected='selected' value='"+item.purchaseId+"'> "+item.purchaseNo+" </option>"
                }else{
                    htmlText += "<option value='"+item.purchaseId+"'> "+item.purchaseNo+" </option>"
                }
            }
            $("#purchaseId").append(htmlText);
        }
    });
}

function getAgentLevelByUpdate(levelId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/agent/agentLevel/agentLevelDOList",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.levelId == levelId){
                    htmlText += "<option selected='selected' value='"+item.levelId+"'> "+item.levelName+" </option>"
                }else{
                    htmlText += "<option value='"+item.levelId+"'> "+item.levelName+" </option>"
                }
            }
            $("#levelId").append(htmlText);
        }
    });
}

function getAgentByUpdate(agentId) {
    $.ajax({
        cache : true,
        type : "GET",
        url : getContext()+"/agent/agent/all",
        // data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            var itemList = data.data;
            var htmlText ="";
            for (var i in itemList){
                var item = itemList[i];
                if (item.agentId == agentId){
                    htmlText += "<option selected='selected' value='"+item.agentId+"'> "+item.agentName+" </option>"
                }else{
                    htmlText += "<option value='"+item.agentId+"'> "+item.agentName+" </option>"
                }
            }
            $("#agentId").append(htmlText);
        }
    });
}

function  getWarehouseNameByUpdate(warehouseId){
    $.ajax({
        cache : true,
        url: getContext() + "/warehouse/warehouse/all",
        type: "GET",
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success: function (result) {
            var list = result.data;
            var htmlText = "";
            for (var index in  list) {
                var item = list[index];
                if (item.warehouseId == warehouseId) {
                    htmlText += "<option selected='selected' value='" + item.warehouseId + "'> " + item.warehouseName + " </option>"
                } else {
                    htmlText += "<option value='" + item.warehouseId + "'> " + item.warehouseName + " </option>"
                }
            }
            $("#warehouseId").append(htmlText);
        }
    });
}
function selectAgentAll() {
    $.ajax({
        url: getContext() + "/agent/agent/all",
        type: "GET",
        success: function (result) {
            if (result.success) {
                var list = result.data;
                for (var index in list) {
                    var item = list[index];
                    $("#agentId").append("<option value='" + item.agentId + "'>" + item.agentName + "</option>")
                }
            } else {
                layer.msg(result.message)
            }
        },
        error: function (result) {
            layer.msg("网络错误，请稍后重新操作")
        }
    });
}