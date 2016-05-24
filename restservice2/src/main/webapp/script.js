$(function () {
	var getAllPersons = function() {
		$.ajax({
			  method: "GET",
			  url: "http://localhost:8080/restservicedemo/api/person",
			  success: function(data) {
					$('#owner').html('');
					$('#personTable tbody').html('');
					$.each(data, function(key, value) {
					    $('#owner').append($("<option/>", {
					        value: value.id,
					        text: value.firstName
					    }));
					    $('#personTable tbody').append("<tr>" + 
					    		"<td>" + value.id + "</td>" +
					    		"<td>" + value.firstName + "</td>" +
					    		"<td>" + value.yob + "</td>" +
					    		"</tr>")
					});
				}
		});
	};
	
	var getAllCars = function() {
		console.log("test");
		$.ajax({
			  method: "GET",
			  url: "http://localhost:8080/restservicedemo/api/car",
			  success: function(data) {		
				  console.log("tet2");
					$('#carTable tbody').html('');
					$.each(data, function(key, value) {
					    $('#carTable tbody').append("<tr>" + 
					    		"<td>" + value.id + "</td>" +
					    		"<td>" + value.make + "</td>" +
					    		"<td>" + value.model + "</td>" +
					    		"<td>" + value.yop + "</td>" +
					    		"<td>" + value.owner.id + "</td>" +
					    		"</tr>")
					});
				}
		});
	};
	
	getAllPersons();
	getAllCars();


	
	$("#addPerson").click(function() {
		$.ajax({
			  method: "POST",
			  url: "http://localhost:8080/restservicedemo/api/person",
			  data: JSON.stringify({ id: $("#idPerson").val(), firstName: $("#name").val(), yob: $("#yob").val() }),
			  contentType: "application/json; charset=utf-8",
			  dataType: "json"
			});
		getAllPersons();
	});
	
	$("#addCar").click(function() {
		$.ajax({
			  method: "POST",
			  url: "http://localhost:8080/restservicedemo/api/car",
			  data: JSON.stringify({ id: $("#idCar").val(), make: $("#make").val(),
				  model: $("#model").val(), yop: $("#yop").val(), owner: { id: $("#owner").val()} }),
			  contentType: "application/json; charset=utf-8",
			  dataType: "json"
			});
		getAllCars();
	});
});