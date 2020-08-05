/**
 * 
 */
var key = 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D';
var keyb = 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql';
function gwangjufcm(){	
	  $.ajax({        
          type : 'POST',
          url : "https://fcm.googleapis.com/fcm/send",
          headers : {
//              Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
//               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
               Authorization : 'key=' + key
						
               },
          contentType : 'application/json',
          dataType: 'json',
          data: JSON.stringify({"to": "/topics/gwangju", "notification":
          {"title":$('#gwangju_title').val(),"body":$('#gwangju_body').val()}}),
          success : function(response) {
              console.log(response);
              alert('정상적으로 푸시 알림을 보냈습니다.')
              $('#gwangju_body').val('');
          },
          error : function(xhr, status, error) {
              console.log(xhr.error);                   
          }
      });
	
}

function seoulfcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/seoul", "notification": 
		{"title":$('#seoul_title').val(),"body":$('#seoul_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#seoul_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function busanfcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/busan", "notification": 
		{"title":$('#busan_title').val(),"body":$('#busan_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#busan_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function kyungkifcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/kyungki", "notification": 
		{"title":$('#kyungki_title').val(),"body":$('#kyungki_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#kyungki_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function kangwonfcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/kangwon", "notification": 
		{"title":$('#kangwon_title').val(),"body":$('#kangwon_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#kangwon_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function chungchungfcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/chungchung", "notification": 
		{"title":$('#chungchung_title').val(),"body":$('#chungchung_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#chungchung_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function junrafcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/junra", "notification": 
		{"title":$('#junra_title').val(),"body":$('#junra_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#junra_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function kyungsangfcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/kyungsang", "notification": 
		{"title":$('#kyungsang_title').val(),"body":$('#kyungsang_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#kyungsang_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
}
function jejufcm(){	
	$.ajax({        
		type : 'POST',
		url : "https://fcm.googleapis.com/fcm/send",
		headers : {
//			Authorization : 'key=' + 'AAAAKY1g2qo:APA91bFp38U2rJoXynapLxIT7BzkqD1-pWNtpaVCTIJOde7RIkVmxlX750B27qkoI5fzdZ4s5oP0CcGjwv_vqetA6NYTSYDzs_i9F0sCsW6GGYPdhVlmqhQjddBD67t27uck934R6sql'
               Authorization : 'key=' + 'AAAAIPQBAY8:APA91bGsq-K5aiYyEvRYE7CsnPmLj9g5umPP1t0DRICTUL_3Wft6WB8YLFUdyMGPBqkKQXZRBiqWjkEAAFN6qbpxoIX84vvGlzOOjAoYAE9Xvuqo7Z4mpVMt1JCfnyNTIg2tlZNBtg-D'
			
		},
		contentType : 'application/json',
		dataType: 'json',
		data: JSON.stringify({"to": "/topics/jeju", "notification": 
		{"title":$('#jeju_title').val(),"body":$('#jeju_body').val()}}),
		success : function(response) {
			console.log(response);
			alert('정상적으로 푸시 알림을 보냈습니다.')
			$('#jeju_body').val('');
		},
		error : function(xhr, status, error) {
			console.log(xhr.error);                   
		}
	});
	
	
}