<html>
<head>
  <title>${title}</title>
</head>
<body >
    <div style="width:100%;display:flex;flex-direction:column;text-align:center">
      <h1 >${title}</h1>
      <h2> Date Created : ${dateCreated} </h2>
      <h3> Chart : </h3>
      <br>

      <ol>
        <#list entries as entry>
          <li>${entry}</li>
        </#list>
      </ol>
    </div>
</body>
</html>