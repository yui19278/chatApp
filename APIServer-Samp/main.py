import uvicorn
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class ChatData(BaseModel):
    id: int
    groupId: int
    senderId: int
    senderName: str
    dateTime: str
    message: str

chatList = []

initData = [
    ChatData(id = 0, groupId = 1, senderId = 1, senderName = "Alice", dateTime = "2024-08-14T10:15:30", message = "Good morning, everyone!"),
    ChatData(id = 1, groupId = 1, senderId = 2, senderName = "Bob", dateTime = "2024-08-14T10:16:00", message = "Morning, Alice!"),
    ChatData(id = 2, groupId = 1, senderId = 3, senderName = "Charlie", dateTime = "2024-08-14T10:16:30", message = "How's it going?"),
    ChatData(id = 3, groupId = 2, senderId = 4, senderName = "David", dateTime = "2024-08-14T10:17:00", message = "Can we have a meeting today?"),
    ChatData(id = 4, groupId = 2, senderId = 5, senderName = "Eva", dateTime = "2024-08-14T10:18:00", message = "Sure, what time?"),
    ChatData(id = 5, groupId = 3, senderId = 6, senderName = "Frank", dateTime = "2024-08-14T10:19:00", message = "Did you complete the report?"),
    ChatData(id = 6, groupId = 3, senderId = 7, senderName = "Grace", dateTime = "2024-08-14T10:20:00", message = "Yes, I sent it yesterday."),
    ChatData(id = 7, groupId = 1, senderId = 1, senderName = "Alice", dateTime = "2024-08-14T10:21:00", message = "Let's catch up later."),
    ChatData(id = 8, groupId = 4, senderId = 8, senderName = "Henry", dateTime = "2024-08-14T10:22:00", message = "I'm on my way."),
    ChatData(id = 9, groupId = 4, senderId = 9, senderName = "Isabella", dateTime = "2024-08-14T10:23:00", message = "See you soon!")
]

@app.get("/")
def read_root():
    return {"Hello": "World"}

#全てのグループのメッセージ履歴を送る
@app.get("/getallchats")
def get_Allchats():
    return chatList

#指定したグループのメッセージ履歴を送る
@app.get("/getchats")
async def get_groupChats(groupId: int):
    chats = [item for item in chatList if item.groupId == groupId]
    return chats

#メッセージの受け取り
@app.post("/post")
def post_message(chat: ChatData):
    chat.id = len(chatList)
    print(chat)
    chatList.append(chat)
    print(chatList)
    return {"res": "OK", "chat": chat}

#これを実行
if __name__ == "__main__":
    chatList.extend(initData)
    # print(chatList)
    #証明書はそれぞれのIPで作ったものに変更する
    uvicorn.run(app, ssl_keyfile="./localhost+2-key.pem", ssl_certfile="./localhost+2.pem", host="0.0.0.0", port=8000)
