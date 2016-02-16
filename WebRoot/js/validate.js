
function validateForm() {
	
		var validator = $("#myForm").validate({
			onsubmit:true,// 是否在提交是验证
			submitHandler:function(form) {
	    		ajaxSubmit();
	    	},
	    rules: {
	    	
	    	userName: {
	    		required:true
	    		},
	    	sex: {
	    		required:true
	    		},
	    	phoneNum: {
	    		required:true,
	    		digits:true
	      },
	      	city: {
	      		required:true
	      	},
	      	cameraType: {
	      		required:true
	      	},
	      	shootType: {
	      		required:true
	      	}
	    },
	      messages: {
	    	 userName: {
	    		 required:"请输入您的姓名"
		    		},
		     sex: {
		    	 required:"请选择您的性别"
		    		},
		     phoneNum: {
		    	 required:"请输入您的电话号码",
		    	 digits:"请输入正确的电话号码"
		      },
		     city: {
		    	 required:"请输入您所在的城市"
		      	},
		     cameraType: {
		    	 required:"请输入您的相机型号"
		      	},
		      shootType: {
		    	 required:"请输入您的拍摄风格"
		      }
	    },
	    errorPlacement: function(error, element) { //指定错误信息位置 
	    	if (element.is(":radio") ) { //如果是radio 
	    		
	    	error.appendTo(element.parent().next()); //将错误信息添加当前元素的父结点后面 
	    	} else if(element.is(":checkbox")){//或checkbox
	    		
		    	error.appendTo($("#shotType")); //将错误信息添加当前元素的父结点后面 
	    	}else if(element.is(":text")){
	    		error.appendTo(element.prev());
	    	}else{ 
	    	error.insertAfter(element); 
	    	} 
	    	} 
	    	//debug: true, //如果修改为true则表单不会提交
	    	//通过之后回调
	    	
	  }); 
		
		

}// end document.ready

