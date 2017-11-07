(function() {
	cola(function(model) {
		$(".ui.accordion").accordion({
			exclusive : false
		});
		model.dataType({
			name : "CapCompanyInfo",
			properties : {
				capcompanyinfos : {
					dataType : "CapCompanyInfo",
					provider : {
						url : "./service/frame/company/tree/?parentId={{@id}}"
					}
				}
			}
		});
		model.describe("capcompanyinfos", {
			dataType : "CapCompanyInfo",
			provider : {
				url : "./service/frame/company/tree/",
			}
		});
		model.get("capcompanyinfos");
		model.dataType({
			name : "CapUser",
			properties : {
				username : {
					validators : [ "required" ]
				},
				enableFlag : {
					defaultValue : 1
				},
				isAdministrator : {
					dataType : "boolean"
				},
				cname : {
					validators : [ "required" ]
				},
				ename : {
					validators : [ "required" ]
				},
				/*
				 * idcard : { validators : [ "required",{ $type:"regExp", regExp :
				 * "^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$", message:"身份证格式不正确！" } ] },
				 */
				mobile : {
					validators : [ {
						$type : "regExp",
						regExp : "^([0-9-]+)$",
						message : "手机号格式不正确！"
					} ]
				},
				email : {
					dataType : "string",
					validators : [ {
						$type : "email",
						message : "邮箱格式不正确！"
					} ]
				},
				password : {
					validators : [ "required", {
						$type : "length",
						min : 8,
						message : "密码长度过短,必须大于8位数！"
					} ]
				}
			}
		});
		model
				.describe("capusers",
						{
							dataType : "CapUser",
							provider : {
								url : "./service/frame/user/pageview/",
								pageSize : 10,
								beforeSend : function(self, arg) {
									if (model.get("companyId") == App
											.getLoginUser().companyName) {
										arg.options.data.companyId = App
												.getLoginUser().companyId;
									} else {
										arg.options.data.companyId = arg.model
												.get("companyId");
									}
								}
							}
						});
		model.describe("entityCapUser", "CapUser");
		model.set("entityCapUser", {});

		model
				.dataType({
					name : "Node",
					properties : {
						nodes : {
							dataType : "Node",
							provider : {
								url : "./service/frame/dept/company/parent/?parentId={{@id}}"
							}
						}
					}
				});
		model.set("companyId", App.getLoginUser().companyId);
		model
				.describe("nodes",
						{
							dataType : "Node",
							provider : {
								url : "./service/frame/dept/company/parent/",
								beforeSend : function(self, arg) {
									if (model.get("companyId") == App
											.getLoginUser().companyName) {
										arg.options.data.companyId = App
												.getLoginUser().companyId;
									} else {
										arg.options.data.companyId = arg.model
												.get("companyId");
									}
								}
							}
						});

		model
				.describe("cappositions",
						{
							provider : {
								url : "./service/frame/user/position/",
								beforeSend : function(self, arg) {
									if (model.get("companyId") == App
											.getLoginUser().companyName) {
										arg.options.data.companyId = App
												.getLoginUser().companyId;
									} else {
										arg.options.data.companyId = arg.model
												.get("companyId");
									}
								}
							}
						});
		model.get("cappositions");

		model
				.describe("capciteams",
						{
							provider : {
								url : "./service/capCiTeam/",
								beforeSend : function(self, arg) {
									if (model.get("companyId") == App
											.getLoginUser().companyName) {
										arg.options.data.compId = App
												.getLoginUser().companyId;
									} else {
										arg.options.data.compId = arg.model
												.get("companyId");
									}
								}
							}
						});

		var filter = {}
		model.describe("condition", {});
		model.set("condition", {});
		model.describe("mecInfo", {
			provider : {
				pageSize : 15,
				method : "POST",
				url : App.getContextPath()
						+ "/service/hospitalController/getMecInfo",
				beforeSend : function(self, arg) {
					if (model.get("condition") != null) {
						arg.options.data = {
							"filter" : filter
						};
					}
				},
			}
		});

		model
				.action({
					falg : function(value) {
						if (value == 1) {
							return "可用"
						} else if (value == 0) {
							return "不可用"
						}
					},
					formatNbr : function(nbr) {
						if (nbr) {
							if (nbr.length == 18) {
								return nbr
										.replace(
												/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/,
												"$1********$5$6");
							} else if (nbr.length == 15) {
								return nbr.replace(
										/^(\d{6})(\d{4})(\d{2})(\d{3})$/,
										"$1******$4");
							}
						} else {
							return "";
						}
					},
					getColor : function(status) {
						if (status === "完成") {
							return "positive-text";
						} else {
							return "negative-text";
						}
					},
					search : function() {
						return model.flush("capusers");
					},
					addHospital : function() {
						var entity = model.get("capusers").current
						if (entity) {
							cola.widget("hospitalDialog").show();
						}
					},
					closeDialog : function() {
						cola.widget("hospitalDialog").hide();
					},
					saveMecInfo : function() {
						var mecInfo = model.get("mecInfo");
						var serviceItemString = "";
						var servItemNoChoiced = new Array();
						var entity = model.get("capusers").current;
						mecInfo.each(function(mec) {
							if (mec.get("selected") == true) {
								serviceItemString += mec.get("mecName") + ",";
								var mecNo = mec.get("mecNo");
								servItemNoChoiced.push(mecNo);
							}
						});
						model.set("list", {
							"depts" : servItemNoChoiced,
							"username" : entity.get("username")
						})
						$.ajax("./service/frame/user/addHospital", {
							data : JSON.stringify(model.get("list").toJSON()),
							type : "POST",
							contentType : "application/json",
							success : function() {
								cola.widget("hospitalDialog").hide();
								model.flush("capusers");
								cola.NotifyTipManager.success({
									message : "添加成功!",
									description : "后台任务执行成功！",
									showDuration : 3000
								});
							},
							error : function() {
								cola.NotifyTipManager.success({
									message : "添加失败!",
									showDuration : 3000
								});
							}
						});

					},
					searchHospotial : function() {
						filter = {};
						var conditionEntity = model.get('condition');
						if (conditionEntity.get('mecName')) {
							filter['mecName'] = conditionEntity.get('mecName');
						}
						model.flush("mecInfo");
					},
					add : function() {
						var bankCode = App.getLoginUser().bankCode;
						var instCode = App.getLoginUser().instCode;
						if (bankCode == "null" && instCode != "null") {
							model.set("entityCapUser", {
								bankCode : "",
								instCode : instCode
							});
						} else if (instCode == "null" && bankCode != "null") {
							model.set("entityCapUser", {
								bankCode : bankCode,
								instCode : ""
							});
						} else if (bankCode == "null" && instCode == "null") {
							model.set("entityCapUser", {
								bankCode : "",
								instCode : ""
							});
						} else {
							model.set("entityCapUser", {
								bankCode : bankCode,
								instCode : instCode
							});
						}
						model.set("entityCapUser", {});
						model.get("entityCapUser").setState("new");
						cola.widget("passwordInput").set("value", "");
						cola.widget("usernameInput").set("readOnly", false);
						return cola.widget("editLayer").show();
					},
					edit : function(item) {
						model.set("entityCapUser", item.toJSON());
						model.get("entityCapUser").setState("modified");
						cola.widget("usernameInput").set("readOnly", true);
						cola.widget("editLayer").show();
					},
					cancel : function() {
						return cola.widget("editLayer").hide();
					},
					cancel2 : function() {
						return cola.widget("addAreaSidebar").hide();
					},
					cancel3 : function() {
						return cola.widget("addCiTeamSidebar").hide();
					},
					ok : function() {
						var data, entityCapUser, validate, result, message;
						entityCapUser = model.get("entityCapUser");
						entityCapUser.set("enableFlag", cola.widget("flag")
								.get("checked") ? 1 : 0);
						result = entityCapUser.validate();
						try {
							message = entityCapUser._messageHolder.keyMessage;
						} catch (e) {

						}
						if (result) {
							if (entityCapUser.state == "new") {
								entityCapUser.set("createUser", App
										.getLoginUser().cname);
							} else if (entityCapUser.state == "modified") {
								entityCapUser.set("updateUser", App
										.getLoginUser().cname);
							}
							model.set("entityCapUser.capCompanyInfo", {
								"id" : model.get("companyId") == App
										.getLoginUser().companyName ? App
										.getLoginUser().companyId : model
										.get("companyId")
							});
							data = entityCapUser.toJSON();
							delete data.deptName;
							delete data.teamName;
							NProgress.start();
							$
									.ajax(
											"./service/frame/user/",
											{
												data : JSON.stringify(data),
												type : entityCapUser.state != "new" ? "PUT"
														: "POST",
												contentType : "application/json",
												success : function(self, arg) {
													if (self.data == "success") {
														if (entityCapUser.state == "new") {
															cola
																	.widget(
																			"editLayer")
																	.hide();
															model
																	.flush("capusers");
															entityCapUser.state = "none";
															NProgress.done();
															cola.NotifyTipManager
																	.success({
																		message : "添加成功!",
																		description : "后台任务执行成功！",
																		showDuration : 3000
																	});
														} else if (entityCapUser.state == "modified") {
															cola
																	.widget(
																			"editLayer")
																	.hide();
															model
																	.flush("capusers");
															entityCapUser.state = "none";
															cola.NotifyTipManager
																	.success({
																		message : "修改成功!",
																		description : "后台任务执行成功！",
																		showDuration : 3000
																	});
														}
													} else {
														cola
																.alert(
																		"用户名已存在!!",
																		{
																			level : cola.MessageBox.level.ERROR
																		});
													}
												}
											});
						} else if (message.idcard) {
							if (message.idcard.text) {
								cola.NotifyTipManager.error({
									message : "保存失败",
									description : "身份证格式不正确！",
									showDuration : 3000
								});
							}
						} else if (message.password) {
							if (message.password.text) {
								cola.NotifyTipManager.error({
									message : "保存失败",
									description : "密码长度过短,必须大于8位数！",
									showDuration : 3000
								});
							}
						} else if (message.mobile) {
							if (message.mobile.text) {
								cola.NotifyTipManager.error({
									message : "保存失败",
									description : "手机号格式不正确！",
									showDuration : 3000
								});
							}
						} else if (message.email) {
							if (message.email.text) {
								cola.NotifyTipManager.error({
									message : "保存失败",
									description : "邮箱格式不正确！",
									showDuration : 3000
								});
							}
						}
					},
					del : function(item) {
						var temp1 = model.get("companyId");
						var temp2 = item.get("username");
						cola.confirm("确定要删除吗？", {
							onApprove : function() {
								$.ajax("./service/frame/user/?companyId="
										+ temp1 + "&username=" + temp2, {
									type : "DELETE",
									complete : function() {
										model.flush("capusers");
										cola.NotifyTipManager.success({
											message : "删除成功!",
											description : "后台任务执行成功！",
											showDuration : 3000
										});
									}
								});
							}
						});
					},
					unlock : function(item) {
						var temp1 = model.get("companyId");
						var temp2 = item.get("username");
						console.log(temp1 + temp2);
						cola.confirm("确定要解锁吗？", {
							onApprove : function() {
								$.ajax(
										"./service/frame/user/unlock/?companyId="
												+ temp1 + "&username=" + temp2,
										{
											type : "DELETE",
											complete : function() {
												model.flush("capusers");
												cola.NotifyTipManager.success({
													message : "解锁成功!",
													description : "后台任务执行成功！",
													showDuration : 3000
												});
											}
										});
							}
						});
					},
					mappingIsAdmin : function(key) {
						var result;
						result = key;
						if (result == true) {
							return "是";
						} else {
							return "不是";
						}
					},
					addDept : function() {
						return cola.widget("addAreaSidebar").show();
					},
					addCiTeam : function() {
						model.flush("capciteams");
						return cola.widget("addCiTeamSidebar").show();
					},
					citySave : function() {
						var companyId;
						var list = new Array();
						model.get("nodes").each(function(node) {
							model.action.iteratAreaTree(list, node);
						});

						if (model.get("companyId") == App.getLoginUser().companyName) {
							companyId = App.getLoginUser().companyId;
						} else {
							companyId = arg.model.get("companyId");
						}

						model.set("user", {
							"companyId" : companyId,
							"username" : model.get("capusers.username"),
							"depts" : list
						});
						$.ajax("./service/frame/user/adddepts", {
							data : JSON.stringify(model.get("user").toJSON()),
							type : "POST",
							contentType : "application/json",
							complete : function() {
								cola.widget("addAreaSidebar").hide();
								model.flush("capusers");
								cola.NotifyTipManager.success({
									message : "添加成功!",
									description : "后台任务执行成功！",
									showDuration : 3000
								});
							}
						});
					},
					iteratAreaTree : function(list, node) {
						// 遍历树判断节点是否被选中
						if (node.get("checked")) {
							var dept = node.get("id");
							list.push(dept);
						}
						// 判断树节点不加载,去遍历子节点
						var nodes = node.get("nodes", "never");
						if (nodes) {
							nodes.each(function(node) {
								model.action.iteratAreaTree(list, node);
							});
						}
					},
					teamSave : function() {
						var temp1 = model.get("capciteams.id");
						var temp2 = model.get("capusers.username");
						$.ajax("./service/frame/user/addteam?teamCode=" + temp1
								+ "&userName=" + temp2, {
							complete : function() {
								cola.widget("addCiTeamSidebar").hide();
								model.flush("capusers");
								cola.NotifyTipManager.success({
									message : "添加成功!",
									description : "后台任务执行成功！",
									showDuration : 3000
								});
							}
						});
					}
				});

		return model
				.widgetConfig({
					SystemDropDown : {
						$type : "customDropdown",
						openMode : "drop",
						bind : "companyId",
						valueProperty : "id",
						textProperty : "name",
						content : {
							$type : "tree",
							bind : {
								hasChildProperty : "isDir",
								expression : "capcompanyinfo in capcompanyinfos",
								valueProperty : "id",
								textProperty : "name",
								child : {
									recursive : true,
									hasChildProperty : "isDir",
									textProperty : "name",
									valueProperty : "id",
									expression : "capcompanyinfo in capcompanyinfo.capcompanyinfos"
								}
							},
							itemClick : function(self, arg) {
								var companyId = arg.item._data._data.id;
								model.set("companyId", companyId);
								model.set("parentId", "-1");
								var dropdown = cola.findDropDown(self);
								if (dropdown)
									dropdown.close(arg.item.get("data"));
							}
						}
					},
					dropdownPosition : {
						$type : "dropdown",
						openMode : "drop",
						items : "{{type in cappositions}}",
						valueProperty : "name",
						textProperty : "name",
						bind : "entityCapUser.position",
						beforeOpen : function(self, arg) {
							self.set("value", "");
						}
					},
					addAreaSidebar : {
						$type : "Sidebar",
						size : "350",
						direction : "right"
					},
					addCiTeamSidebar : {
						$type : "Sidebar",
						size : "350",
						direction : "right"
					},
					areaTree : {
						$type : "tree",
						highlightCurrentItem : true,
						autoExpand : true,
						autoCollapse : true,
						autoCheckChildren : true,
						bind : {
							expression : "node in nodes",
							checkedProperty : "checked",
							textProperty : "name",
							child : {
								expandedProperty : "expanded",
								recursive : true,
								expression : "node in node.nodes",
								checkedProperty : "checked",
								textProperty : "name"
							}
						}
					},
					editLayer : {
						$type : "Sidebar",
						size : "500",
						direction : "right",
						show : function() {
							model.get("entityCapUser").validate();
						}
					},
					capuserTable : {
						$type : "table",
						showHeader : true,
						bind : "item in capusers",
						highlightCurrentItem : true,
						currentPageOnly : true,
						changeCurrentItem : true,
						columns : [ {
							bind : ".username",
							align : "center",
							caption : "账号"

						}, {
							bind : ".cname",
							align : "center",
							caption : "中文名"
						}, {
							bind : ".ename",
							align : "center",
							caption : "员工编号"
						},
						/*
						 * { bind : "formatNbr(item.idcard)", align : "center",
						 * caption : "身份证", width : 100 },
						 */

						{
							bind : ".email",
							caption : "电子邮件",
							align : "center",
						}, {
							bind : ".mobile",
							align : "center",
							caption : "手机号"
						}, {
							bind : ".mecName",
							caption : "医院",
						}, {
							bind : "falg(item.enableFlag)",
							caption : "是否可用",
							align : "center",
						}, {
							bind : "mappingIsAdmin(item.isAdministrator)",
							align : "center",
							caption : "是否管理员"
						}, {
							caption : "操作",
							align : "center",
							template : "operations"
						} ]
					},
					hospitalTable : {
						$type : "table",
						bind : "mec in mecInfo",
						showHeader : true,
						highlightCurrentItem : true,
						columns : [ {
							$type : "select"
						}, {
							property : "mecNo",
							caption : "体检分部编码",
							align : "center",
							width : "150px"
						}, {
							property : "mecName",
							caption : "医院名称",
							align : "center",
							width : "150px"
						} ]
					}

				});

		/* 解决页面刚渲染时页面结构错乱 */
		$("[tag='contentContainer']").attr("tag", "");
	});
	cola.on("ready",function(){
		console.log( App.getLoginUser())       
		var administrator = App.getLoginUser().administrator;
		if (administrator=="true") {
			cola.widget("btnMecInfo").set("display",true);
		} else {
			cola.widget("btnMecInfo").set("display",false);
		}
	})
}).call(this);
