function showMe(show) {
    if(show == "kcal") {
        document.getElementById("morris-area-weight").style.display = "none";
        document.getElementById("morris-area-kcal").style.display = "block";
    } else if (show == "weight") {
        document.getElementById("morris-area-weight").style.display = "block";
        document.getElementById("morris-area-kcal").style.display = "none";
    } else{
        document.getElementById("morris-area-weight").style.display = "block";
        document.getElementById("morris-area-kcal").style.display = "block";
    }
}
showExercise(0);
function showExercise(e) {
    $.getJSON('/zti/charts-exercises', function (myDataDonut) {
        $("#morris-area-chart-exercises").empty();
        Morris.Area({
            element: 'morris-area-chart-exercises',
            data: myDataDonut.myData,
            xkey: 'sessionDate',
            ykeys: [myDataDonut.names[e]],
            labels: [myDataDonut.names[e]],
            pointSize: 2,
            hideHover: 'auto',
            resize: true,
            parseTime: false,
            xLabelAngle: 70

        });
    });
}

$(function() {
    $.getJSON('/zti/charts-user', function (myDataWeight) {
        Morris.Area({
            element: 'morris-area-chart-weight',
            data: myDataWeight,
            xkey: 'date',
            ykeys: ['weight'],
            labels: ['Waga'],
            pointSize: 2,
            hideHover: 'auto',
            resize: true

        });

        Morris.Area({
            element: 'morris-area-chart-kcal',
            data: myDataWeight,
            xkey: 'date',
            ykeys: ['calories'],
            labels: ['Kalorie'],
            lineColors: 'red',
            pointSize: 2,
            hideHover: 'auto',
            resize: true
        });
    });



    $.getJSON('/zti/charts-sessions', function (myDataBar) {
        Morris.Bar({
            element: 'morris-bar-chart',
            data: myDataBar,
            xkey: 'week',
            ykeys: ['count'],
            labels: ['Liczba treningów'],
            hideHover: 'auto',
            resize: true
        });
    });

});

