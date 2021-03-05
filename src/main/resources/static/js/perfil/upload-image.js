var fileInputFile = document.getElementById("file_picture_user");
fileInputFile.addEventListener(
	"change",
	function(event) {
		const endpoint = `/api/profile/avatar`;
		var formData = new FormData();
		formData.append("file", event.target.files[0]);
		var img1 = document.querySelector("#img_loader");
		img1.src = "/images/loader.gif";
		fetch(endpoint, {
			method: "POST",
			body: formData,
		})
			.then((response) => response.json())
			.catch((error) => console.error("Error:", error))
			.then((response) => {
				var imgProfile = document.querySelectorAll(".img_profile");
				imgProfile.forEach((elem) => {
					elem.src = response.url;
				});
			});
	},
	false
);