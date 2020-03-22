$(document).on('click', ".answer-write input[type='submit']", addAnswer);

function addAnswer(event) {
    event.preventDefault();
    console.log("click me");

    var queryString = $('.answer-write').serialize();
    console.log("queryString : " + queryString);

    var url = $('.answer-write').attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess,
    })
}

function onError() {
    console.log("질문 생성 실패");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(
        data.writer.name,
        data.writer.name,
        data.contents,
        data.queestion.id,
        data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}


String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
