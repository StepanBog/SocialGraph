
window.onload = function() {
    var main = document.getElementById("main")
    var list = [[${mylist}]];
    alert(1)
    for (i = 0; i < list.length ; i++) {
        var t = document.createTextNode(list[i].name)
        var p = document.createElement("p")
        p.appendChild(t)
        main.append(p);
    }
}