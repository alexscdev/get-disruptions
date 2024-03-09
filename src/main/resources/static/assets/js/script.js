//Read the Formbutton docs at formspree.io/formbutton/docs. See more examples at codepen.io/formspree 
  

    /* paste this line in verbatim */
    window.formbutton = window.formbutton || function () { (formbutton.q = formbutton.q || []).push(arguments) };
    /* customize formbutton below*/
    formbutton("create", {
      action: "https://formspree.io/f/mrgnpvky",
      title: "Any suggestions?",
      fields: [
        {
          type: "email",
          label: "Email:",
          name: "email",
          required: false,
          placeholder: "your@email.com"
        },
        {
          type: "text",
          label: "Name:",
          name: "name",
          required: true,
          placeholder: "Alex"
        },
        {
          type: "textarea",
          label: "Message:",
          name: "message",
          placeholder: "What's on your mind?",
        },
        { type: "submit" }
      ],
      styles: {
        title: {
          backgroundColor: "gray"
        },
        button: {
          backgroundColor: "gray"
        }
      }
    });

//--------------------------------------------------------------------



function getCookie(cookieName) {
      var cookies = document.cookie.split(';');
      for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.indexOf(cookieName + '=') === 0) {
          return cookie.substring(cookieName.length + 1, cookie.length);
        }
      }
      return null;
    }



    


