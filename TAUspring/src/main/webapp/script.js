$(function () {
	var getAllPersons = function() {
		$.ajax({
			  method: "GET",
			  url: "/api/persons",
			  success: function(data) {
				  if (data._embedded == undefined) {
					  return;
				  }
				  if (data._embedded.persons.length === 0) {
					  return;
				  }
				  console.log(data);
					$('#owner').html('');
					$('#personTable tbody').html('');
					$.each(data._embedded.persons, function(key, value) {
					    $('#owner').append($("<option/>", {
					        value: value.id,
					        text: value.firstName
					    }));
					    $('#personTable tbody').append("<tr>" + 
					    		"<td>" + value.p_id + "</td>" +
					    		"<td>" + value.firstName + "</td>" +
					    		"<td>" + value.yob + "</td>" +
					    		"</tr>")
					});
				}
		});
	};

	
	var getAllCars = function() {
		$.ajax({
			  method: "GET",
			  url: "/api/cars",
			  success: function(data) {
				  if (data._embedded == undefined) {
					  return;
				  }
				  if (data._embedded.cars.length === 0) {
					  return;
				  }
				  console.log("SAMOCHODY:");
				  console.log(data);
					$('#carTable tbody').html('');
					$.each(data._embedded.cars, function(key, value) {
					    $('#carTable tbody').append("<tr>" + 
					    		"<td>" + value.c_id + "</td>" +
					    		"<td>" + value.make + "</td>" +
					    		"<td>" + value.model + "</td>" +
					    		"<td>" + value.yop + "</td>" +
					    		"<td>" + value.owner_id + "</td>" +
					    		"</tr>")
					});
				}
		});
	};
		
	
	getAllPersons();
	getAllCars();
	
	$('#refresh').click(function() {
		getAllPersons();
		getAllCars();
	});
	
	$("#addPerson").click(function() {
		$.ajax({
			  method: "POST",
			  url: "/api/persons",
			  data: JSON.stringify({firstName: $("#name").val(), yob: $("#yob").val() }),
			  contentType: "application/json; charset=utf-8",
			  dataType: "json"
			}).done(function() {
				getAllPersons();
			});

	});
	
	$("#addCar").click(function() {
		$.ajax({
			  method: "POST",
			  url: "/api/cars",
			  data: JSON.stringify({make: $("#make").val(),
				  model: $("#model").val(), yop: $("#yop").val(), owner: { id: $("#owner").val()} }),
			  contentType: "application/json; charset=utf-8",
			  dataType: "json"
			}).done(function() {
				getAllCars();
			});

	});
});