$(function() {
    var shopId = getQueryString("shopId");
    var productName = '';
    $("a").each(function (i,e) {
        var url = $(e).attr("href");
        $(e).attr("href",url+"?shopId="+shopId);
    })
    function getList() {
        var listUrl = '/shop/listuserproductmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId + '&cxtj=' + productName;
        $.getJSON(listUrl, function (data) {
            if(data.success){

                var userProductMapList = data.userProductMaps;
                var tempHtml = '';
                userProductMapList.map(function (item, index) {
                    var expireTime = new Date(item.createTime).Format("yyyy-MM-dd");
                    tempHtml += ''
                        +      '<div class="row row-productbuycheck">'
                        +          '<div class="col-33">'+ item.productName +'</div>'
                        +          '<div class="col-33 productbuycheck-time">'+ expireTime +'</div>'
                        +          '<div class="col-33">'+ item.userName +'</div>'
                        +      '</div>';
                });
                $('.productbuycheck-wrap').html(tempHtml);

                f(data);
            }
        });
    }

    $('#search').on('input', function (e) {
        productName = e.target.value;
        $('.productbuycheck-wrap').empty();
        getList();
    });

    getList();

    function f(dataa) {
        var myChart = echarts.init(document.getElementById('chart'));

        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:dataa.legend
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : dataa.xAxis
                },
                {
                    type:'category',
                    data : dataa.createTime,
                    axisLabel : {//坐标轴刻度标签的相关设置。
                        clickable:true,//并给图表添加单击事件  根据返回值判断点击的是哪里
                        interval : 0,
                        formatter : function(params,index){
                            if (index % 2 != 0) {
                                return '\n\n' + params;
                            }
                            else {
                                return params;
                            }
                        }

                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : /*[
                {
                    name:'奶粉',
                    type:'bar',
                    data:[120, 132, 101, 134, 290, 230, 220]
                },
                {
                    name:'牛奶',
                    type:'bar',
                    data:[60, 72, 71, 74, 190, 130, 110]
                }/!*,
                {
                    name:'香蕉',
                    type:'bar',
                    data:[62, 82, 91, 84, 109, 110, 120]
                }*!/
            ]*/
            dataa.series
        };

        myChart.setOption(option);
    }











});