import React from 'react';
import { scriptService, authenticationService } from '@/_services';
import Calendar from 'react-calendar';
import moment from 'moment';

class ScriptPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: authenticationService.currentUserValue,
            scripts: null,
            date: new Date(),
            script: null,
            text: ""
        };
    }

    onChange = date => {
        const script = this.findScriptByDate(date)[0]
        console.log(script)
        this.setState({ script });
        if(script) {
            this.setState({text: script.text})
        } else {
            this.setState({text: ""})
        }
        this.setState({ date });
    }

    onTextChange = event => {
        this.setState({text: event.target.value})
    }

    onClick = () => {
        if(this.state.text == ""){
            console.log("Text shouldnt be empty")
            return
        }
        if(this.state.script == null){
            // let script = await scriptService.create(this.state.date, this.state.text)

            // this.state.scripts.push(script)

            scriptService.create(this.state.date, this.state.text).then( script => {
                console.log(script)
                this.state.scripts.push(script)
                // scriptService.getAll().then(scripts => this.setState({scripts}))
                console.log(this.state.scripts)
            })
        } else {
            // let updatedScript = await scriptService.update(this.state.script.id, this.state.date, this.state.text).then
            // // curScript = this.state.scripts.filter(script => script.id == this.state.script.id)[0]
            // curScriptId = this.state.scripts.findIndex(script, id, array => script.id == this.state.script.id)
            // console.log(curScript)
            // console.log(curScriptId)

            scriptService.update(this.state.script.id, this.state.airDate, this.state.text).then( script => {
                console.log(script)
                // scriptService.getAll().then(scripts => this.setState({scripts}))
                let curScriptId = this.state.scripts.findIndex((script_, id, array) => script_.id == this.state.script.id)
                console.log(curScriptId)
                this.state.scripts[curScriptId] = script
                console.log(this.state.scripts)
        })
    }

    }
    componentDidMount() {
        scriptService.getAll().then(scripts => this.setState({ scripts }));
    }

    findScriptByDate = (date) => {
        if (!this.state.scripts) {
            return null;
        }
        return this.state.scripts.filter(script => {
            return moment.unix(script.airDate/1000).format('YYYY-DD-MM') === moment(date).format('YYYY-DD-MM')
        });
    }


    render() {
        const textareaStyle = {
            height: "400px"
        }
        return (
            <div>
                <Calendar
                    onChange={this.onChange}
                    value={this.state.date}
                    className='col-12 mt-5'
                />
                <span className="badge badge-info ml-3 mt-5">{moment(this.state.date).format('YYYY-DD-MM')}</span>
                <div className="md-form col-12 mt-1">
                    <textarea 
                        className="md-textarea form-control" 
                        style={textareaStyle}
                        rows="3" 
                        onChange={this.onTextChange}
                        value={this.state.text}>
                    </textarea>
                </div>
                <button className="btn btn-primary mt-5 float-right mr-3" onClick={this.onClick}>Save</button>
            </div>
        );
    }
}

export { ScriptPage };