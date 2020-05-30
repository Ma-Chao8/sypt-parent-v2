function getContext() {
    var pathName=window.document.location.pathname;
    console.log("pathName:"+pathName)
    //截取，得到项目名称
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    console.log("projectName:"+projectName)
    getRootPath()
    return projectName;
}


function getRootPath() {
    var pathName = window.location.pathname.substring(1);
    console.log("rootPath:"+pathName)
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    console.log("webName:"+webName)
    var url = window.location.protocol + '//' + window.location.host + '/' + webName + '/';
    console.log("url："+url)
    return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
}