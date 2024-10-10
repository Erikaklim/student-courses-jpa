    function createCourse() {
        const title = document.querySelector('.titleCreateRest').value;
        fetch('/JavaWebTry/api/courses/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title: title})
        })
            .then(response => {
                console.log(response);
            })
    }

    function getCourse() {
        const id =  document.querySelector('.idGetRest').value;
        console.log(id);
        fetch('/JavaWebTry/api/courses/' + id, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Title', data.title);
                document.getElementById('getResultTitle').textContent = "Title: " + data.title;
            })
            .catch(error => console.error('Error fetching course:', error));
    }

    function updateCourse() {
        const id = document.querySelector('.idUpdateRest').value;
        const title = document.querySelector('.titleUpdateRest').value;
        fetch('/JavaWebTry/api/courses/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ title: title})
        })
            .then(response => {
                console.log(response);
            })
    }