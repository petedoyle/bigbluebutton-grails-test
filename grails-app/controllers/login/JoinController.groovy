package login

class JoinController {
	
	def apiService

	def index = {
		redirect( url: apiService.getSignedApiUrl( "join", [fullName: 'Les', meetingID: '71000', password:8888] ) )
		//render( contentType: "text/plain", text: apiService.getSignedApiUrl( "getMeetings", [random:"dummytext"] ) )
	}
}
