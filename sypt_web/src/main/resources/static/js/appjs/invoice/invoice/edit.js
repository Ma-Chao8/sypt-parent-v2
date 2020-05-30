$().ready(function() {
	update()
});
function update() {
	function getInvoiceId() {
		var id = [[${invoiceId}]]
		return id;
	}
	console.log("gggg"+getInvoiceId())

	$.ajax({
		url: getContext() + "/invoice/invoice/getGetail/" + getInvoiceId(),
		type: "GET",
		dataType: "json",
		success: function (result) {
			if (result.success) {
				var data = result.data;
				$("#invoice_id").text(data.invoice_id);
				$("#deliver_date").text($.formatDate(new Date(data.deliver_date), "yyyy-MM-dd"));
				$("#agent_name").text(data.agent_name);
				$("#linkman").text(data.linkman);
				$("#tel").text(data.tel);
				$("#deliver_address").text(data.deliver_addres);
				var text = "";
				var products = data.products;
				for (var i in products) {
					var product = products[i];
					text += "<tr>";
					text += "<td>" + product.product_name + "</td>";
					text += "<td>";
					var codes = product.codes;
					for (var j in codes) {
						var code = codes[j];
						text += "<div>"
						text += code.big_code ? (code.big_code + " （大码）") : (code.small_code + " （小码）");
						text += "</div>"
					}
					text += "</td>";
					text += "</tr>"
				}
				$("#table_product").append(text);
			} else {
				layer.msg(result.message)
			}
		},
		error: function () {
			layer.msg("网络错误，请稍后重新操作");
		}
	})

	$.ajax({
		cache : true,
		type : "POST",
		url : "/invoice/invoice/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
