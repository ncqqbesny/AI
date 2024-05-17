export function encode(key, eles) {
    var keyVal = parseInt(key)
    var arr = []
    for (var i = 0; i < eles.length; ++i) {
        var ele = eles[i]
        arr.push(ele.length ^ keyVal)
        for (var j = 0; j < ele.length; ++j) {
            arr.push(ele[j].charCodeAt(0) ^ keyVal)
        }
    }
    arr.push(keyVal)
    var context = "CV16" + arr.join("%")
    return context;
} 

// 变成树形
export function arrToTree(list, rootValue) {
    let treeData = [] // 新建数组 , 用来储存当前对象的子对象
    list.forEach(item => {
        // 遍历寻找子对象
        if (item.parentMenuId === rootValue) {
            // 递归  返回对象的children列表
            const children = arrToTree(list, item.menuId)
            // 如果有children就将数组添加给对象
            if (children.length) {
                item.children = children
            }
            // 将完整的item添加给导出的数组
            treeData.push(item)
        }
    })
    return treeData // 每次递归会返回当前的子列表
}
export function arrToTree2(items) {  
    const idMap = {};  
    const tree = [];  
  
    items.forEach(item => {  
        idMap[item.mid] = { ...item, children: [] };  
    });  
  
    items.forEach(item => {  
        const parent = idMap[item.parentMid];  
        if (parent) {  
            parent.children.push(idMap[item.mid]);  
        } else {  
            tree.push(idMap[item.mid]);  
        }  
    });  
  
    return tree;  
}
export function checkNumberInArray(array,number) {  
    return array.includes(number);  
}