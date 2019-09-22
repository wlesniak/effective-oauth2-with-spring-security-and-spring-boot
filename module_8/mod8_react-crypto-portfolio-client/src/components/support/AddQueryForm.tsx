import * as React from 'react';
import {connect} from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { History } from 'history';
import { AddQueryAction } from '../../actions/types';
import { addQueryCreator } from '../../actions/supportActions';
import { Redirect } from 'react-router';

interface AddQueryFormProps {
    addQuery?: Function;
}

class AddQueryForm extends React.Component<AddQueryFormProps, any> {

    constructor(props:AddQueryFormProps) {
        super(props);
        this.state = {
            subject: "",
            content: "",
            navToMainSupportPage: false
            
        }
        this.onChange=this.onChange.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
    }

    handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const query = {
            subject: this.state.subject,
            content: this.state.content        };
        if(this.props.addQuery !== undefined) {
            this.setState({
                subject: "",
                content: "",
                navToMainSupportPage: true
            });
            this.props.addQuery(query);
        }
    }

    onChange(e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        this.setState({ [e.target.name]: e.target.value});
    }

  public render() {
      if(this.state.navToMainSupportPage) {
         return <Redirect to='/support'/>
      }
    return (
        <div className="form-group">
            <form className="form-horizontal" onSubmit={this.handleSubmit}>
                <fieldset>
                <div className="form-group">
                    <label className="col-sm-2 control-label">Subject</label>
                    <input type ="string" name = "subject" required={true} value={this.state.subject} onChange={this.onChange}/>
                </div>
                <div className="form-group"> 
                    <label className="col-sm-2 control-label">Query</label>
                    <textarea name = "content" required={true} value={this.state.content} onChange={this.onChange}/>
				</div>  
                    <button name="addQueryBtn" type="submit">Add Query</button>               
                </fieldset>
            </form>
	    </div>
    );
  }
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{},{},any>) => ({
    addQuery: (query: AddQueryAction, history: History) => dispatch(addQueryCreator(query, history))
  });

export default connect(null,mapDispatchToProps)(AddQueryForm);