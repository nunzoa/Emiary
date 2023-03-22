const editor = new EditorJS({
  holder: "editorjs",
  tools: {
    header: Header,
    quote: Quote,
    // image: {
    //   class: ImageTool,
    //   config: {
    //     endpoints: {
    //       byFile: "http://localhost:8888/uploadFile", // Your backend file uploader endpoint
    //       byUrl: "http://localhost:8888/fetchUrl", // Your endpoint that provides uploading by Url
    //     },
    //   },
    // },
    list: {
      class: NestedList,
      inlineToolbar: true,
      config: {
        defaultStyle: "unordered",
      },
    },
    checklist: {
      class: Checklist,
      inlineToolbar: true,
    },
    raw: RawTool,
  },
});



$("#diarybtn").click(function() {
  let content = $("#editorjs").text();
  let daystring = $("#dayString").text();
  daystring = daystring
      .replace("년 ", "-")
      .replace("월 ", "-")
      .replace("일", "");

  console.log(content);
  console.log(daystring);

  $.ajax({
    type: "POST",
    url: "write",
    data: { content : content, created_at : daystring },
    dataType : 'text',
    success: function(n) {
      console.log("Data saved successfully! : " + n);
      location.href ='/emiary';
    },
    error: function(e) {
      alert(JSON.stringify(e));
      console.log("Error occurred while saving data!");
      // Handle error response here
    }
  });
});